/*
 * Copyright Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.socotech.fcm.http;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.common.io.Closeables;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;

import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Helper class to send messages to the GCM service using an API Key.
 */
public class Sender {

    private final Gson gson;
    private final String url;
    private final Random random;
    private final String accessToken;

    /**
     * Default constructor.
     *
     * @param projectId project ID
     */
    public Sender(String projectId) throws IOException {
        this.url = String.format("https://fcm.googleapis.com/v1/projects/%s/messages:send", projectId);
        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Message.class, new InstanceCreator<Message>() {
            @Override
            public Message createInstance(Type type) {
                return new Message.Builder().build();
            }
        });
        this.gson = gsonBuilder.create();
        this.random = new Random();
        String scope = "https://www.googleapis.com/auth/firebase.messaging";
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("service-account.json");
        GoogleCredential credential = GoogleCredential.fromStream(stream).createScoped(Collections.singletonList(scope));
        credential.refreshToken();
        this.accessToken = credential.getAccessToken();
    }

    /**
     * Sends a message without retrying in case of service unavailability. See
     * {@link #send(Request, int)} for more info.
     *
     * @return result of the post, or {@literal null} if the GCM service was
     * unavailable or any network exception caused the request to fail,
     * or if the response contains more than one result.
     * @throws InvalidRequestException  if GCM didn't returned a 200 status.
     * @throws IllegalArgumentException if to is {@literal null}.
     */
    public Response send(Request request) throws IOException {
        String responseBody = this.makeFcmHttpRequest(request);
        if (responseBody == null) {
            return null;
        } else {
            return gson.fromJson(responseBody, Response.class);
        }
    }

    /**
     * Sends a message to one device, retrying in case of unavailability.
     * <p/>
     * <p/>
     * <strong>Note: </strong> this method uses exponential back-off to retry in
     * case of service unavailability and hence could block the calling thread
     * for many seconds.
     *
     * @param request message to be sent, including the device's registration id.
     * @param retries number of retries in case of service unavailability errors.
     * @return result of the request (see its javadoc for more details).
     * @throws IllegalArgumentException if to is {@literal null}.
     * @throws InvalidRequestException  if GCM didn't returned a 200 or 5xx status.
     * @throws IOException              if message could not be sent.
     */
    public Response send(Request request, int retries) throws IOException {
        int attempt = 0;
        int backoff = BACKOFF_INITIAL_DELAY;
        boolean tryAgain;
        Response response;
        do {
            attempt++;
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Attempt #" + attempt + " to send message " + request + " to regIds " + request.getMessage().getToken());
            }
            response = this.send(request);
            tryAgain = response == null && attempt <= retries;
            if (tryAgain) {
                int sleepTime = backoff / 2 + random.nextInt(backoff);
                sleep(sleepTime);
                if (2 * backoff < MAX_BACKOFF_DELAY) {
                    backoff *= 2;
                }
            }
        } while (tryAgain);
        if (response == null) {
            throw new IOException("Could not send message after " + attempt + " attempts");
        }
        return response;
    }

    private String makeFcmHttpRequest(Request request) throws InvalidRequestException {
        int status;
        HttpURLConnection conn;
        try {
            String body = gson.toJson(request);
            logger.log(Level.FINE, body);
            conn = this.post(url, "application/json", body);
            status = conn.getResponseCode();
        } catch (IOException e) {
            logger.log(Level.FINE, "IOException posting to FCM", e);
            return null;
        }
        String responseBody;
        if (status != 200) {
            try {
                responseBody = getAndClose(conn.getErrorStream());
                logger.finest("JSON error response: " + responseBody);
            } catch (IOException e) {
                // ignore the exception since it will thrown an InvalidRequestException
                // anyways
                responseBody = "N/A";
                logger.log(Level.FINE, "Exception reading response: ", e);
            }
            throw new InvalidRequestException(status, responseBody);
        }
        try {
            responseBody = getAndClose(conn.getInputStream());
        } catch (IOException e) {
            logger.log(Level.WARNING, "IOException reading response", e);
            return null;
        }
        logger.finest("JSON response: " + responseBody);
        return responseBody;
    }

    /**
     * Makes an HTTP POST request to a given endpoint.
     * <p/>
     * <p/>
     * <strong>Note: </strong> the returned connected should not be disconnected,
     * otherwise it would kill persistent connections made using Keep-Alive.
     *
     * @param url         endpoint to post the request.
     * @param contentType type of request.
     * @param body        body of the request.
     * @return the underlying connection.
     * @throws IOException propagated from underlying methods.
     */
    private HttpURLConnection post(String url, String contentType, String body) throws IOException {
        if (url == null || contentType == null || body == null) {
            throw new IllegalArgumentException("arguments cannot be null");
        }
        if (!url.startsWith("https://")) {
            logger.warning("URL does not use https: " + url);
        }
        logger.fine("Sending POST to " + url);
        logger.finest("POST body: " + body);
        byte[] bytes = body.getBytes(UTF8);
        HttpURLConnection conn = getConnection(url);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", contentType);
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
        conn.setFixedLengthStreamingMode(bytes.length);
        OutputStream out = conn.getOutputStream();
        try {
            out.write(bytes);
        } finally {
            Closeables.close(out, true);
        }
        return conn;
    }

    /**
     * Gets an {@link HttpURLConnection} given an URL.
     */
    private HttpURLConnection getConnection(String url) throws IOException {
        return (HttpURLConnection) new URL(url).openConnection();
    }

    /**
     * Convenience method to convert an InputStream to a String.
     * <p/>
     * If the stream ends in a newline character, it will be stripped.
     * <p/>
     * If the stream is {@literal null}, returns an empty string.
     */
    private static String getString(InputStream stream) throws IOException {
        if (stream == null) {
            return "";
        }
        String newLine;
        StringBuilder content = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        do {
            newLine = reader.readLine();
            if (newLine != null) {
                content.append(newLine).append('\n');
            }
        } while (newLine != null);
        if (content.length() > 0) {
            content.setLength(content.length() - 1); // strip last newline
        }
        return content.toString();
    }

    private static String getAndClose(InputStream stream) throws IOException {
        try {
            return getString(stream);
        } finally {
            if (stream != null) {
                Closeables.close(stream, true);
            }
        }
    }

    void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static final String UTF8 = "UTF-8";
    /**
     * Maximum delay before a retry.
     */
    private static final int MAX_BACKOFF_DELAY = 1024000;
    /**
     * Initial delay before first retry, without jitter.
     */
    private static final int BACKOFF_INITIAL_DELAY = 1000;
    /**
     * Logger
     */
    private static final Logger logger = Logger.getLogger(Sender.class.getName());
}

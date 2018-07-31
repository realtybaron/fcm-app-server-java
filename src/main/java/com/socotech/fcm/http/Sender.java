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

import java.io.*;
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

    private Gson gson;
    private String url;
    private Random random;
    private String accessToken;
    private GoogleCredential credential;

    /**
     * Default constructor.
     *
     * @param projectId project ID
     */
    public Sender(String projectId) throws IOException {
        this(projectId, Thread.currentThread().getContextClassLoader());
    }

    /**
     * Default constructor.
     *
     * @param projectId   project ID
     * @param classLoader class loader used to find "service-account.json"
     * @throws IOException
     */
    public Sender(String projectId, ClassLoader classLoader) throws IOException {
        this.url = String.format("https://fcm.googleapis.com/v1/projects/%s/messages:send", projectId);
        this.gson = new Gson();
        this.random = new Random();
        String scope = "https://www.googleapis.com/auth/firebase.messaging";
        InputStream stream = classLoader.getResourceAsStream("service-account.json");
        this.credential = GoogleCredential.fromStream(stream).createScoped(Collections.singletonList(scope));
        this.credential.refreshToken();
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
        return send(request, 0);
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
        Response response = null;
        do {
            attempt++;
            String responseBody = this.makeFcmHttpRequest(request);
            if (responseBody != null) {
                response = gson.fromJson(responseBody, Response.class);
            }
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
        String responseBody;
        HttpURLConnection conn = null;
        try {
            String body = gson.toJson(request);
            conn = this.post(url, "application/json", body);
            status = conn.getResponseCode();
            switch (status) {
                case 200:
                    responseBody = getAndClose(conn.getInputStream());
                    LOGGER.log(Level.INFO, "JSON response: " + responseBody);
                    return responseBody;
                case 401:
                    if (this.credential.refreshToken()) {
                        this.accessToken = credential.getAccessToken();
                        return null; // force retry
                    } else {
                        LOGGER.log(Level.WARNING, "Refreshing access token failed");
                    }
                default:
                    responseBody = getAndClose(conn.getErrorStream());
                    LOGGER.log(Level.WARNING, "JSON error response: " + responseBody);
                    throw new InvalidRequestException(status, responseBody);
            }
        } catch (InvalidRequestException e) {
            throw e;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "IOException posting to FCM", e);
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
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
            LOGGER.log(Level.WARNING, "URL does not use https: " + url);
        }
        LOGGER.log(Level.INFO, "Send to " + url);
        LOGGER.log(Level.INFO, "POST body: " + body);
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
    private static final Logger LOGGER = Logger.getLogger(Sender.class.getName());
}

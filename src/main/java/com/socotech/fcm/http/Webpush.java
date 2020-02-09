
package com.socotech.fcm.http;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Webpush {

    @Expose
    @SerializedName("data")
    private Map<String, String> data;
    @Expose
    @SerializedName("headers")
    private Map<String, String> headers;
    @Expose
    @SerializedName("fcm_options")
    private WebpushFcmOptions options;
    @Expose
    @SerializedName("notification")
    private WebpushNotification notification;

    /**
     * Default constructor
     *
     * @param builder a builder
     */
    private Webpush(Builder builder) {
        options = builder.options;
        notification = builder.notification;
        if (!builder.data.isEmpty()) {
            data = Collections.unmodifiableMap(builder.data);
        }
        if (!builder.headers.isEmpty()) {
            headers = Collections.unmodifiableMap(builder.headers);
        }
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public WebpushFcmOptions getOptions() {
        return options;
    }

    public void setOptions(WebpushFcmOptions options) {
        this.options = options;
    }

    public WebpushNotification getNotification() {
        return notification;
    }

    public void setNotification(WebpushNotification notification) {
        this.notification = notification;
    }

    public static final class Builder {

        private WebpushFcmOptions options;
        private final Map<String, String> data;
        private final Map<String, String> headers;
        private WebpushNotification notification;

        public Builder() {
            this.data = new LinkedHashMap<>();
            this.headers = new LinkedHashMap<>();
        }

        /**
         * Adds a key/value pair to the payload data.
         */
        public Builder data(String key, String value) {
            data.put(key, value);
            return this;
        }

        /**
         * Adds a key/value pair to the payload data.
         */
        public Builder header(String key, String value) {
            headers.put(key, value);
            return this;
        }

        /**
         * Sets the options property
         */
        public Builder options(WebpushFcmOptions value) {
            options = value;
            return this;
        }

        /**
         * Sets the notification property.
         */
        public Builder notification(WebpushNotification value) {
            notification = value;
            return this;
        }

        public Webpush build() {
            return new Webpush(this);
        }
    }

}

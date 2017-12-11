
package com.socotech.fcm.http;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Apns {

    @Expose
    @SerializedName("payload")
    private Payload payload;

    @Expose
    @SerializedName("headers")
    private Map<String, String> headers;

    private Apns(Builder builder) {
        payload = builder.payload;
        if (!builder.headers.isEmpty()) {
            headers = Collections.unmodifiableMap(builder.headers);
        }
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public static final class Builder {

        private Payload payload;
        private Map<String, String> headers;

        public Builder() {
            this.headers = new LinkedHashMap<>();
        }

        /**
         * Adds a key/value pair to the payload data.
         */
        public Builder addHeader(String key, String value) {
            headers.put(key, value);
            return this;
        }

        /**
         * Sets the notification property.
         */
        public Builder payload(Payload value) {
            payload = value;
            return this;
        }

        public Apns build() {
            return new Apns(this);
        }
    }

}

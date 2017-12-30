
package com.socotech.fcm.http;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Message {

    @Expose
    @SerializedName("apns")
    private Apns apns;
    @Expose
    @SerializedName("token")
    private String token;
    @Expose
    @SerializedName("topic")
    private String topic;
    @Expose
    @SerializedName("condition")
    private String condition;
    @Expose
    @SerializedName("android")
    private Android android;
    @Expose
    @SerializedName("webpush")
    private Webpush webpush;
    @Expose
    @SerializedName("notification")
    private Notification notification;
    @Expose
    @SerializedName("data")
    private Map<String, String> data;

    private Message(Builder builder) {
        this.apns = builder.apns;
        this.token = builder.token;
        this.topic = builder.topic;
        this.android = builder.android;
        this.webpush = builder.webpush;
        this.condition = builder.condition;
        this.notification = builder.notification;
        if (!builder.data.isEmpty()) {
            data = Collections.unmodifiableMap(builder.data);
        }
    }

    public Apns getApns() {
        return apns;
    }

    public void setApns(Apns apns) {
        this.apns = apns;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Android getAndroid() {
        return android;
    }

    public void setAndroid(Android android) {
        this.android = android;
    }

    public Webpush getWebpush() {
        return webpush;
    }

    public void setWebpush(Webpush webpush) {
        this.webpush = webpush;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public static final class Builder {
        private Apns apns;
        private String token;
        private String topic;
        private String condition;
        private Android android;
        private Webpush webpush;
        private Notification notification;
        private Map<String, String> data;

        public Builder() {
            this.data = new LinkedHashMap<>();
        }

        public Builder apns(Apns apns) {
            this.apns = apns;
            return this;
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder topic(String topic) {
            this.topic = topic;
            return this;
        }

        public Builder condition(String condition) {
            this.condition = condition;
            return this;
        }

        public Builder android(Android android) {
            this.android = android;
            return this;
        }

        public Builder webpush(Webpush webpush) {
            this.webpush = webpush;
            return this;
        }

        public Builder notification(Notification notification) {
            this.notification = notification;
            return this;
        }

        public Builder data(String key, String value) {
            data.put(key, value);
            return this;
        }

        public Message build() {
            return new Message(this);
        }
    }

    /**
     * Value used to set message priority to high.
     */
    public static final String MESSAGE_PRIORITY_HIGH = "high";
    /**
     * Value used to set message priority to normal.
     */
    public static final String MESSAGE_PRIORITY_NORMAL = "normal";
}


package com.socotech.fcm.http;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @Expose
    @SerializedName("apns")
    private Apns apns;
    @Expose
    @SerializedName("name")
    private String name;
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

    private Message(Builder builder) {
        this.apns = builder.apns;
        this.name = builder.name;
        this.token = builder.token;
        this.android = builder.android;
        this.webpush = builder.webpush;
        this.condition = builder.condition;
        this.notification = builder.notification;
    }

    public Apns getApns() {
        return apns;
    }

    public void setApns(Apns apns) {
        this.apns = apns;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public static final class Builder {
        private Apns apns;
        private String name;
        private String token;
        private String topic;
        private String condition;
        private Android android;
        private Webpush webpush;
        private Notification notification;

        public Builder apns(Apns apns) {
            this.apns = apns;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder setTopic(String topic) {
            this.topic = topic;
            return this;
        }

        public Builder setCondition(String condition) {
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

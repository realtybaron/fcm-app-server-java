
package com.socotech.fcm.http;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Aps {

    @Expose
    @SerializedName("alert")
    private Alert alert;
    @Expose
    @SerializedName("sound")
    private String sound;
    @Expose
    @SerializedName("category")
    private String category;
    @Expose
    @SerializedName("thread-id")
    private String threadId;
    @Expose
    @SerializedName("badge")
    private Integer badge;
    @Expose
    @SerializedName("content-available")
    private Integer contentAvailable;

    private Aps(Builder builder) {
        alert = builder.alert;
        sound = builder.sound;
        badge = builder.badge;
        category = builder.category;
        threadId = builder.threadId;
        contentAvailable = builder.contentAvailable;
    }

    public Alert getAlert() {
        return alert;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
    }

    public Integer getBadge() {
        return badge;
    }

    public void setBadge(Integer badge) {
        this.badge = badge;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public Integer getContentAvailable() {
        return contentAvailable;
    }

    public void setContentAvailable(Integer contentAvailable) {
        this.contentAvailable = contentAvailable;
    }

    public static final class Builder {
        private Alert alert;
        private String sound;
        private String category;
        private String threadId;
        private Integer badge;
        private Integer contentAvailable;

        public Builder alert(Alert alert) {
            this.alert = alert;
            return this;
        }

        public Builder setSound(String sound) {
            this.sound = sound;
            return this;
        }

        public Builder setCategory(String category) {
            this.category = category;
            return this;
        }

        public Builder setThreadId(String threadId) {
            this.threadId = threadId;
            return this;
        }

        public Builder setBadge(Integer badge) {
            this.badge = badge;
            return this;
        }

        public Builder setContentAvailable(Integer contentAvailable) {
            this.contentAvailable = contentAvailable;
            return this;
        }

        public Aps build() {
            return new Aps(this);
        }
    }

}

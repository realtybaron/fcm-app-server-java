
package com.socotech.fcm.http;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notification {

    @Expose
    @SerializedName("body")
    private String body;

    @Expose
    @SerializedName("title")
    private String title;

    private Notification(Builder builder) {
        body = builder.body;
        title = builder.title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static class Builder {
        String body;
        String title;

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Notification build() {
            return new Notification(this);
        }
    }
}

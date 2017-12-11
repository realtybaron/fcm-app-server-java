
package com.socotech.fcm.http;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WebpushNotification {

    @Expose
    @SerializedName("body")
    private String body;
    @Expose
    @SerializedName("icon")
    private String icon;
    @Expose
    @SerializedName("title")
    private String title;

    private WebpushNotification(Builder builder) {
        body = builder.body;
        icon = builder.icon;
        title = builder.title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static class Builder {
        String body;
        String icon;
        String title;

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Builder icon(String icon) {
            this.icon = icon;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public WebpushNotification build() {
            return new WebpushNotification(this);
        }
    }

}

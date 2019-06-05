package com.socotech.fcm.http;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created with IntelliJ IDEA.
 * User: marc
 * Date: 2019-06-05
 * Time: 10:14
 */
public class WebpushFcmOptions {

    @Expose
    @SerializedName("link")
    private String link;

    public WebpushFcmOptions(Builder builder) {
        this.link = builder.link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public static class Builder {
        String link;

        public WebpushFcmOptions.Builder link(String link) {
            this.link = link;
            return this;
        }

        public WebpushFcmOptions build() {
            return new WebpushFcmOptions(this);
        }
    }
}

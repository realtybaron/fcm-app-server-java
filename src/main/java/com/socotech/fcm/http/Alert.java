
package com.socotech.fcm.http;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

public class Alert {

    @Expose
    @SerializedName("body")
    private String body;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("loc-key")
    private String locKey;
    @Expose
    @SerializedName("launch-image")
    private String launchImage;
    @Expose
    @SerializedName("title-loc-key")
    private String titleLocKey;
    @Expose
    @SerializedName("action-loc-key")
    private String actionLocKey;
    @Expose
    @SerializedName("loc-args")
    private List<String> locArgs;
    @Expose
    @SerializedName("title-loc-args")
    private List<String> titleLocArgs;

    private Alert(Builder builder) {
        body = builder.body;
        title = builder.title;
        locKey = builder.locKey;
        launchImage = builder.launchImage;
        titleLocKey = builder.titleLocKey;
        actionLocKey = builder.actionLocKey;
        if (!builder.locArgs.isEmpty()) {
            locArgs = Collections.unmodifiableList(builder.locArgs);
        }
        if (!builder.titleLocArgs.isEmpty()) {
            titleLocArgs = Collections.unmodifiableList(builder.titleLocArgs);
        }
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

    public String getLocKey() {
        return locKey;
    }

    public void setLocKey(String locKey) {
        this.locKey = locKey;
    }

    public String getLaunchImage() {
        return launchImage;
    }

    public void setLaunchImage(String launchImage) {
        this.launchImage = launchImage;
    }

    public String getTitleLocKey() {
        return titleLocKey;
    }

    public void setTitleLocKey(String titleLocKey) {
        this.titleLocKey = titleLocKey;
    }

    public String getActionLocKey() {
        return actionLocKey;
    }

    public void setActionLocKey(String actionLocKey) {
        this.actionLocKey = actionLocKey;
    }

    public List<String> getLocArgs() {
        return locArgs;
    }

    public void setLocArgs(List<String> locArgs) {
        this.locArgs = locArgs;
    }

    public List<String> getTitleLocArgs() {
        return titleLocArgs;
    }

    public void setTitleLocArgs(List<String> titleLocArgs) {
        this.titleLocArgs = titleLocArgs;
    }

    public static final class Builder {
        private String body;
        private String title;
        private String locKey;
        private String launchImage;
        private String titleLocKey;
        private String actionLocKey;
        private List<String> locArgs;
        private List<String> titleLocArgs;

        public Builder() {
            this.locArgs = Lists.newArrayList();
            this.titleLocArgs = Lists.newArrayList();
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder setLocKey(String locKey) {
            this.locKey = locKey;
            return this;
        }

        public Builder setLaunchImage(String launchImage) {
            this.launchImage = launchImage;
            return this;
        }

        public Builder setTitleLocKey(String titleLocKey) {
            this.titleLocKey = titleLocKey;
            return this;
        }

        public Builder setActionLocKey(String actionLocKey) {
            this.actionLocKey = actionLocKey;
            return this;
        }

        public Builder addLocArg(String titleLocArgs) {
            this.locArgs.add(titleLocArgs);
            return this;
        }

        public Builder addTitleLocKey(String actionLocKey) {
            this.titleLocArgs.add(actionLocKey);
            return this;
        }

        public Alert build() {
            return new Alert(this);
        }
    }
}
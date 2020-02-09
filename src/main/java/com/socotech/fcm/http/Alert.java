
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
    private String bodyLocKey;
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
    private List<String> bodyLocArgs;
    @Expose
    @SerializedName("title-loc-args")
    private List<String> titleLocArgs;

    private Alert(Builder builder) {
        body = builder.body;
        title = builder.title;
        bodyLocKey = builder.bodyLocKey;
        launchImage = builder.launchImage;
        titleLocKey = builder.titleLocKey;
        actionLocKey = builder.actionLocKey;
        if (!builder.bodyLocArgs.isEmpty()) {
            bodyLocArgs = Collections.unmodifiableList(builder.bodyLocArgs);
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

    public String getBodyLocKey() {
        return bodyLocKey;
    }

    public void setBodyLocKey(String bodyLocKey) {
        this.bodyLocKey = bodyLocKey;
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

    public List<String> getBodyLocArgs() {
        return bodyLocArgs;
    }

    public void setBodyLocArgs(List<String> bodyLocArgs) {
        this.bodyLocArgs = bodyLocArgs;
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
        private String bodyLocKey;
        private String launchImage;
        private String titleLocKey;
        private String actionLocKey;
        private final List<String> bodyLocArgs;
        private final List<String> titleLocArgs;

        public Builder() {
            this.bodyLocArgs = Lists.newArrayList();
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

        public Builder bodyLocKey(String locKey) {
            this.bodyLocKey = locKey;
            return this;
        }

        public Builder launchImage(String launchImage) {
            this.launchImage = launchImage;
            return this;
        }

        public Builder titleLocKey(String titleLocKey) {
            this.titleLocKey = titleLocKey;
            return this;
        }

        public Builder actionLocKey(String actionLocKey) {
            this.actionLocKey = actionLocKey;
            return this;
        }

        public Builder bodyLocArg(String locArg) {
            this.bodyLocArgs.add(locArg);
            return this;
        }

        public Builder titleLocArg(String titleLocArg) {
            this.titleLocArgs.add(titleLocArg);
            return this;
        }

        public Alert build() {
            return new Alert(this);
        }
    }
}
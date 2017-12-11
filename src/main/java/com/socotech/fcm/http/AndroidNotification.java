
package com.socotech.fcm.http;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

public class AndroidNotification {

    @Expose
    @SerializedName("tag")
    private String tag;
    @Expose
    @SerializedName("body")
    private String body;
    @Expose
    @SerializedName("icon")
    private String icon;
    @Expose
    @SerializedName("color")
    private String color;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("sound")
    private String sound;
    @Expose
    @SerializedName("click_action")
    private String clickAction;
    @Expose
    @SerializedName("body_loc_key")
    private String bodyLocKey;
    @Expose
    @SerializedName("title_loc_key")
    private String titleLocKey;
    @Expose
    @SerializedName("body_loc_args")
    private List<String> bodyLocArgs;
    @Expose
    @SerializedName("title_loc_args")
    private List<String> titleLocArgs;

    private AndroidNotification(Builder builder) {
        tag = builder.tag;
        body = builder.body;
        icon = builder.icon;
        title = builder.title;
        color = builder.color;
        sound = builder.sound;
        bodyLocKey = builder.bodyLocKey;
        clickAction = builder.clickAction;
        titleLocKey = builder.titleLocKey;
        if (!builder.bodyLocArgs.isEmpty()) {
            bodyLocArgs = Collections.unmodifiableList(builder.bodyLocArgs);
        }
        if (!builder.titleLocArgs.isEmpty()) {
            titleLocArgs = Collections.unmodifiableList(builder.titleLocArgs);
        }
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getClickAction() {
        return clickAction;
    }

    public void setClickAction(String clickAction) {
        this.clickAction = clickAction;
    }

    public String getBodyLocKey() {
        return bodyLocKey;
    }

    public void setBodyLocKey(String bodyLocKey) {
        this.bodyLocKey = bodyLocKey;
    }

    public List<String> getBodyLocArgs() {
        return bodyLocArgs;
    }

    public void setBodyLocArgs(List<String> bodyLocArgs) {
        this.bodyLocArgs = bodyLocArgs;
    }

    public String getTitleLocKey() {
        return titleLocKey;
    }

    public void setTitleLocKey(String titleLocKey) {
        this.titleLocKey = titleLocKey;
    }

    public List<String> getTitleLocArgs() {
        return titleLocArgs;
    }

    public void setTitleLocArgs(List<String> titleLocArgs) {
        this.titleLocArgs = titleLocArgs;
    }

    public static final class Builder {

        private String tag;
        private String icon;
        private String body;
        private String title;
        private String sound;
        private String color;
        private String bodyLocKey;
        private String clickAction;
        private String titleLocKey;
        private List<String> bodyLocArgs;
        private List<String> titleLocArgs;

        public Builder() {
            this.sound = "default"; // the only currently supported value
            this.bodyLocArgs = Lists.newArrayList();
            this.titleLocArgs = Lists.newArrayList();
        }

        public Builder tag(String value) {
            tag = value;
            return this;
        }

        public Builder icon(String icon) {
            this.icon = icon;
            return this;
        }

        public Builder body(String value) {
            body = value;
            return this;
        }

        public Builder title(String value) {
            title = value;
            return this;
        }

        public Builder sound(String value) {
            sound = value;
            return this;
        }

        public Builder color(String value) {
            color = value;
            return this;
        }

        public Builder bodyLocKey(String value) {
            bodyLocKey = value;
            return this;
        }

        public Builder clickAction(String value) {
            clickAction = value;
            return this;
        }

        public Builder titleLocKey(String value) {
            titleLocKey = value;
            return this;
        }

        public Builder bodyLocArg(String value) {
            bodyLocArgs.add(value);
            return this;
        }

        public Builder titleLocArg(String value) {
            titleLocArgs.add(value);
            return this;
        }

        public AndroidNotification build() {
            return new AndroidNotification(this);
        }

    }

}

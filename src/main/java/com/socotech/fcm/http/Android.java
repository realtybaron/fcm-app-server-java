
package com.socotech.fcm.http;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Android {

    @Expose
    @SerializedName("ttl")
    private String ttl;
    @Expose
    @SerializedName("priority")
    private String priority;
    @Expose
    @SerializedName("collapse_key")
    private String collapseKey;
    @Expose
    @SerializedName("restricted_package_name")
    private String restrictedPackageName;
    @Expose
    @SerializedName("data")
    private Map<String, String> data;
    @Expose
    @SerializedName("notification")
    private AndroidNotification notification;

    private Android(Android.Builder builder) {
        priority = builder.priority;
        collapseKey = builder.collapseKey;
        notification = builder.notification;
        restrictedPackageName = builder.restrictedPackageName;
        if (builder.ttl != null) {
            ttl = builder.ttl + "s";
        }
        if (!builder.data.isEmpty()) {
            data = Collections.unmodifiableMap(builder.data);
        }
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCollapseKey() {
        return collapseKey;
    }

    public void setCollapseKey(String collapseKey) {
        this.collapseKey = collapseKey;
    }

    public AndroidNotification getNotification() {
        return notification;
    }

    public void setNotification(AndroidNotification notification) {
        this.notification = notification;
    }

    public String getRestrictedPackageName() {
        return restrictedPackageName;
    }

    public void setRestrictedPackageName(String restrictedPackageName) {
        this.restrictedPackageName = restrictedPackageName;
    }

    public enum Priority {
        NORMAL, HIGH
    }

    public static final class Builder {
        private Float ttl;
        private String priority;
        private String collapseKey;
        private String restrictedPackageName;
        private Map<String, String> data;
        private AndroidNotification notification;

        public Builder() {
            this.data = new LinkedHashMap<>();
        }

        public Builder collapseKey(String value) {
            collapseKey = value;
            return this;
        }

        public Builder ttl(float value) {
            ttl = value;
            return this;
        }

        public Builder data(String key, String value) {
            data.put(key, value);
            return this;
        }

        public Builder restrictedPackageName(String value) {
            restrictedPackageName = value;
            return this;
        }

        public Builder priority(Priority value) {
            switch (value) {
                case HIGH:
                    priority = Message.MESSAGE_PRIORITY_HIGH;
                    break;
                case NORMAL:
                    priority = Message.MESSAGE_PRIORITY_NORMAL;
                    break;

            }
            return this;
        }

        public Builder notification(AndroidNotification value) {
            notification = value;
            return this;
        }

        public Android build() {
            return new Android(this);
        }
    }
}


package com.socotech.fcm.http;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Payload {

    @Expose
    @SerializedName("aps")
    private Aps aps;

    private Payload(Builder builder) {
        aps = builder.aps;
    }

    public Aps getAps() {
        return aps;
    }

    public void setAps(Aps aps) {
        this.aps = aps;
    }

    public static class Builder {
        Aps aps;

        public Builder aps(Aps aps) {
            this.aps = aps;
            return this;
        }

        public Payload build() {
            return new Payload(this);
        }
    }
}

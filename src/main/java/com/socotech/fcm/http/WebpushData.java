
package com.socotech.fcm.http;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WebpushData {

    @SerializedName("foo")
    @Expose
    private String foo;

    public String getFoo() {
        return foo;
    }

    public void setFoo(String foo) {
        this.foo = foo;
    }

}

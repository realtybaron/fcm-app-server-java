package com.socotech.fcm.http;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created with IntelliJ IDEA.
 * User: marc
 * Date: 12/10/17
 * Time: 5:19 PM
 */
public class Request {
    @Expose
    @SerializedName("message")
    private Message message;

    @Expose
    @SerializedName("validate_only")
    private boolean validateOnly;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public boolean isValidateOnly() {
        return validateOnly;
    }

    public void setValidateOnly(boolean validateOnly) {
        this.validateOnly = validateOnly;
    }
}

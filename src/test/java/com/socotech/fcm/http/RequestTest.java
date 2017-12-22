package com.socotech.fcm.http;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: marc
 * Date: 12/10/17
 * Time: 8:50 PM
 */
@RunWith(JUnit4.class)
public class RequestTest {
    private Gson gson;

    @Before
    public void onSetup() throws IOException {
        this.gson = new Gson();
    }

    @Test
    public void testIOS() throws IOException {
        Request request = new Request();
        Alert alert = new Alert.Builder().title("Testing").body("Testing an iOS push notification").build();
        Aps aps = new Aps.Builder().alert(alert).build();
        Payload payload = new Payload.Builder().aps(aps).build();
        Apns apns = new Apns.Builder().payload(payload).build();
        Message message = new Message.Builder().data("name1", "value1").data("name2", "value2").token(TOKEN).apns(apns).build();
        request.setMessage(message);
        request.setValidateOnly(true);
        Assert.assertEquals(gson.toJson(request), "");
    }

    @Test
    public void testAndroid() throws IOException {
        Notification notification = new Notification.Builder().title("Testing").body("Testing an Android push notification").build();
        Message message = new Message.Builder().data("name1", "value1").data("name2", "value2").token(TOKEN).notification(notification).build();
        Request request = new Request();
        request.setMessage(message);
        request.setValidateOnly(true);
        Assert.assertEquals(gson.toJson(request), "");
    }

    @Test
    public void testWebpush() throws IOException {
        Request request = new Request();
        WebpushNotification notification = new WebpushNotification.Builder().title("Testing").body("Testing an Web push notification").build();
        Webpush webpush = new Webpush.Builder().notification(notification).build();
        Message message = new Message.Builder().data("name1", "value1").data("name2", "value2").token(TOKEN).webpush(webpush).build();
        request.setMessage(message);
        request.setValidateOnly(true);
        Assert.assertEquals(gson.toJson(request), "");
    }

    private static final String TOKEN = "abc123xyz";
}

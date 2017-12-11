package com.socotech.fcm.http;

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
public class SenderTest {
    private Sender sender;

    @Before
    public void onSetup() throws IOException {
        this.sender = new Sender("you-worth");
    }

    @Test
    public void testIOS() throws IOException {
        Request request = new Request();
        Alert alert = new Alert.Builder().title("Testing").body("Testing an iOS push notification").build();
        Aps aps = new Aps.Builder().alert(alert).build();
        Payload payload = new Payload.Builder().aps(aps).build();
        Apns apns = new Apns.Builder().payload(payload).build();
        Message message = new Message.Builder().token(TOKEN).apns(apns).build();
        request.setMessage(message);
        request.setValidateOnly(true);
        this.sender.send(request);
    }

    @Test
    public void testAndroid() throws IOException {
        Notification notification = new Notification.Builder().title("Testing").body("Testing an Android push notification").build();
        Message message = new Message.Builder().token(TOKEN).notification(notification).build();
        Request request = new Request();
        request.setMessage(message);
        request.setValidateOnly(true);
        this.sender.send(request);
    }

    @Test
    public void testWebpush() throws IOException {
        Request request = new Request();
        WebpushNotification notification = new WebpushNotification.Builder().title("Testing").body("Testing an Web push notification").build();
        Webpush webpush = new Webpush.Builder().notification(notification).build();
        Message message = new Message.Builder().token(TOKEN).webpush(webpush).build();
        request.setMessage(message);
        request.setValidateOnly(true);
        this.sender.send(request);
    }

    private static final String TOKEN = "cJjwcGdfQJg:APA91bG06xkuJvwGyo-9K0l2aY58gn5Dnng_C14za2fxs9M89nEEpyYoKCnYCDLzHhQMJ1Bg4kigzdlFHq7D8f9a1ZThSNKHMdjyw5FAmSNbMtzYv_CewyRAWpkFX7B8DVXcI4nLLA2S";
}

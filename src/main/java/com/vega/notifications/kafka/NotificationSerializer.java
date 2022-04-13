package com.vega.notifications.kafka;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.vega.notifications.model.Notification;
import org.apache.kafka.common.serialization.Serializer;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class NotificationSerializer implements Serializer<Notification> {
    private String encoding;

    public NotificationSerializer() {
        this.encoding = StandardCharsets.UTF_8.name();
    }

    @Override
    public byte[] serialize(String s, Notification notification) {
        try {
            return notification == null ? null : new Gson().toJson(notification).getBytes(encoding);
        } catch (UnsupportedEncodingException | JsonSyntaxException e) {
            System.err.println("Error when deserializing byte[] to string due to unsupported encoding " + encoding);
            return null;
        }
    }
}

package com.vega.notifications.model;

import com.vega.entities.Event;

import java.time.ZonedDateTime;

public class Notification {
    private String userId;
    private String name;
    private String description;
    private String startTime;

    public Notification(String userId, String name, String description, String startTime) {
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.startTime = startTime;
    }

    public Notification(Event event) {
        this.userId = event.getUserId();
        this.name = event.getName();
        this.description = event.getNotes();
        ZonedDateTime time = event.getBeginDate();
        int minutes = time.getMinute();
        this.startTime = time.getHour() + ":" + (minutes > 9 ? minutes : "0" + minutes);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startTime='" + startTime + '\'' +
                '}';
    }
}

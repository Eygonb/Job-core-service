package com.vega.entities;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;

@Entity
@Table(name = "statuses")
public class Status {

    @EmbeddedId
    private StatusKey key;

    @Column(name = "created_at")
    @CreationTimestamp
    private ZonedDateTime createdAt;

    @Column(name = "modified_at", updatable = false)
    @UpdateTimestamp
    private ZonedDateTime modifiedAt;

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(ZonedDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public StatusKey getKey() {
        return key;
    }

    public void setKey(StatusKey key) {
        this.key = key;
    }

    public void setUserId(String userId) {
        key.setUserId(userId);
    }

    public void setName(String name) {
        key.setNameStatus(name);
    }

    @EqualsAndHashCode
    @ToString
    @Embeddable
    public static class StatusKey implements Serializable {
        public StatusKey() {
        }

        public StatusKey(String nameStatus, String userId) {
            this.nameStatus = nameStatus;
            this.userId = userId;
        }

        static final long serialVersionUID = 1L;

        @Column(name = "name_status")
        private String nameStatus;

        @Column(name = "user_id")
        private String userId;

        public String getNameStatus() {
            return nameStatus;
        }

        public void setNameStatus(String nameStatus) {
            this.nameStatus = nameStatus;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}

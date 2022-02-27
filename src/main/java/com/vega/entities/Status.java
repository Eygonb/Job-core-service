package com.vega.entities;


import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;

@Entity
@Table(name = "statuses")
public class Status {

    @EmbeddedId
    private StatusKey key;

    @Column(name="created_at")
    private ZonedDateTime createdAt;

    @Column(name="modified_at")
    private ZonedDateTime modifiedAt;

    public ZonedDateTime getCreatedAt(){
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt){
        this.createdAt = createdAt;
    }

    public ZonedDateTime getModifiedAt(){
        return modifiedAt;
    }

    public void setModifiedAt(ZonedDateTime modifiedAt){
        this.modifiedAt = modifiedAt;
    }

    public StatusKey getKey(){
        return key;
    }

    public void setKey(StatusKey key){
        this.key = key;
    }

    @EqualsAndHashCode
    @ToString
    @Embeddable
    public class StatusKey implements Serializable {

        static final long serialVersionUID = 1L;

        @Column(name="name_status")
        private String nameStatus;

        @Column(name="user_id")
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

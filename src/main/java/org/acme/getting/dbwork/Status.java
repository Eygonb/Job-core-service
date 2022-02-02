package org.acme.getting.dbwork;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
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

    @EqualsAndHashCode
    @ToString
    @Embeddable
    public class StatusKey implements Serializable {

        static final long serialVersionUID = 1L;

        @Getter
        @Setter
        @Column(name="name_status")
        private String nameStatus;

        @Getter
        @Setter
        @Column(name="user_id")
        private String userId;

    }
}

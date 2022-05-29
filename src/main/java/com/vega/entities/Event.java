package com.vega.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.UUID;
import java.time.ZonedDateTime;
import javax.persistence.*;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "vacancy_id")
    private UUID vacancyId;

    @Column
    private String name;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private ZonedDateTime createdAt;

    @Column(name = "modified_at")
    @UpdateTimestamp
    private ZonedDateTime modifiedAt;

    @Column(name = "begin_date")
    private ZonedDateTime beginDate;

    @Column(name = "end_date")
    private ZonedDateTime endDate;

    @Column(name = "notify_for")
    private Integer notifyFor;

    @Column
    private String notes;

    @Column(name = "is_completed")
    private Boolean isCompleted;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getVacancyId() {
        return vacancyId;
    }

    public void setVacancyId(UUID vacancyId) {
        this.vacancyId = vacancyId;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

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

    public ZonedDateTime getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(ZonedDateTime beginDate) {
        this.beginDate = beginDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Integer getNotifyFor() {
        return notifyFor;
    }

    public void setNotifyFor(Integer notifyFor) {
        this.notifyFor = notifyFor;
    }
}

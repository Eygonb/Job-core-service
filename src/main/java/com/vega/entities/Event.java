package com.vega.entities;


import java.util.UUID;
import java.time.ZonedDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name = "events")
public class Event {

    @Column
    @Id
    private UUID id;

    @Column(name="user_id")
    private String userId;

    @Column(name="vacancy_id")
    private UUID vacancyId;

    @Column
    private String name;

    @Column(name="created_at")
    private ZonedDateTime createdAt;

    @Column(name="modified_at")
    private ZonedDateTime modifiedAt;

    @Column(name="begin_date")
    private ZonedDateTime beginDate;

    @Column(name="end_date")
    private ZonedDateTime endDate;

    @Column
    private String notes;

    @Column(name="is_completed")
    private Boolean isCompleted;

    public UUID getId(){
        return id;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public UUID getVacancyId(){
        return vacancyId;
    }

    public void setVacancyId(UUID vacancyId){
        this.vacancyId = vacancyId;
    }

    public String getUserId(){
        return userId;
    }
    
    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }

    public String getNotes(){
        return notes;
    }
    
    public void setNotes(String notes){
        this.notes = notes;
    }
    
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
   
    public ZonedDateTime getBeginDate(){
        return beginDate;
    }
    
    public void setBeginDate(ZonedDateTime beginDate){
        this.beginDate = beginDate;
    }
       
    public ZonedDateTime getEndDate(){
        return endDate;
    }
    
    public void setEndDate(ZonedDateTime endDate){
        this.endDate = endDate;
    }
           
    public Boolean getIsCompleted(){
        return isCompleted;
    }
    
    public void setIsCompleted(Boolean isCompleted){
        this.isCompleted = isCompleted;
    }
}

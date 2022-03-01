package com.vega.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.time.ZonedDateTime;
import javax.persistence.*;

@Entity
@Table(name = "vacancies")
public class Vacancy {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name="user_id")
    private String userId;

    @Column(name="name_vacancy")
    private String nameVacancy;

    @Column(name="status_name")
    private String statusName;

    @Column(name="created_at")
    @CreationTimestamp
    private ZonedDateTime createdAt;

    @Column(name="modified_at", updatable=false)
    @UpdateTimestamp
    private ZonedDateTime modifiedAt;

    @Column(name="location_latitude")
    private Float locationLatitude;

    @Column(name="location_longitude")
    private Float locationLongitude;

    @Column
    private String company;

    @Column
    private Integer salary;

    @Column
    private String notes;

    @OneToMany
    @JoinColumn(name="vacancy_id")
    private Collection<Event> events;

    @OneToMany
    @JoinColumn(name="vacancy_id")
    private List<Contact> contacts;

    public Vacancy(){}

    public UUID getId(){
        return id;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public String getUserId(){
        return userId;
    }
    
    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getNameVacancy(){
        return nameVacancy;
    }
    
    public void setNameVacancy(String nameVacancy){
        this.nameVacancy = nameVacancy;
    }

    public String getStatusName(){
        return statusName;
    }

    public void setStatusName(String statusName){
        this.statusName = statusName;
    }
    
    public String getCompany(){
        return company;
    }
    
    public void setCompany(String company){
        this.company = company;
    }

    public String getNotes(){
        return notes;
    }
    
    public void setNotes(String notes){
        this.notes = notes;
    }

    public Integer getSalary(){
        return salary;
    }
    
    public void setSalary(Integer salary){
        this.salary = salary;
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
        
    public Float getLocationLatitude(){
        return locationLatitude;
    }
    
    public void setLocationLatitude(Float locationLatitude){
        this.locationLatitude = locationLatitude;
    }
            
    public Float getLocationLongitude(){
        return locationLongitude;
    }
    
    public void setLocationLongitude(Float locationLongitude){
        this.locationLongitude = locationLongitude;
    }
}

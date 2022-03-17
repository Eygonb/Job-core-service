package com.vega.entities;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.UUID;

import javax.persistence.*;

@Entity
@Table(name = "contacts")
public class Contact {


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

    @Column(name="vacancy_id")
    private UUID vacancyId;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="created_at")
    @CreationTimestamp
    private ZonedDateTime createdAt;

    @Column(name="modified_at")
    @UpdateTimestamp
    private ZonedDateTime modifiedAt;

    @Column
    private String city;

    @Column
    private String company;

    @Column
    private String position;

    @Column
    private String mail;

    @Column
    private String skype;

    @Column(name="vk_id")
    private String vkId;

    @Column(name="telegram_id")
    private String telegramId;

    @Column
    private String telephone;

    @Column
    private String notes;

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

    public String getFirstName(){
        return firstName;
    }
    
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    
    public String getLastName(){
        return lastName;
    }
    
    public void setLastName(String lastName){
        this.lastName = lastName;
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
    
    public String getCity(){
        return city;
    }
    
    public void setCity(String city){
        this.city = city;
    }
    
    public String getTelephone(){
        return telephone;
    }
    
    public void setTelephone(String telephone){
        this.telephone = telephone;
    }
    
    public String getSkype(){
        return skype;
    }
    
    public void setSkype(String skype){
        this.skype = skype;
    }

    public String getVk(){
        return vkId;
    }
    
    public void setVk(String vkId){
        this.vkId = vkId;
    }
    
    public String getTelegram(){
        return telegramId;
    }
    
    public void setTelegram(String telegramId){
        this.telegramId = telegramId;
    }
    
    public String getPosition(){
        return position;
    }
    
    public void setPosition(String position){
        this.position = position;
    }

    public String getMail(){
        return mail;
    }
    
    public void setMail(String mail){
        this.mail = mail;
    }
}

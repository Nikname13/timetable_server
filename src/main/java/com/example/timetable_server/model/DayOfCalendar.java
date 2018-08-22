/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timetable_server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 *
 * @author a.zolotarev
 */
@Entity
@Table(name="day_of_calendar")
@EntityListeners(AuditingEntityListener.class)
public class DayOfCalendar implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date dayOfYear;
    
    @NotNull
    private String status;
    
    @NotNull
    @Temporal(TemporalType.TIME)
    private Date hours;
    
    @ManyToOne(optional=false)
    @JoinColumn(name="calendar_id", nullable=false)
    @OnDelete(action=OnDeleteAction.CASCADE)
    @JsonIgnore
    private Calendar calendar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDayOfYear() {
        return dayOfYear;
    }

    public void setDayOfYear(Date dayOfYear) {
        this.dayOfYear = dayOfYear;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getHours() {
        return hours;
    }

    public void setHours(Date hours) {
        this.hours = hours;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }  
}

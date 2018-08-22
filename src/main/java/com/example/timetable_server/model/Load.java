/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timetable_server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name="load")
@EntityListeners(AuditingEntityListener.class)
public class Load {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    @NotNull
    private String description;
    
    @NotNull
    @Temporal(TemporalType.TIME)
    private Date hours;
    
    @ManyToOne(optional=false)
    @JoinColumn(name="worker_id", nullable=false)
    @OnDelete(action=OnDeleteAction.CASCADE)
    @JsonIgnore
    private Worker worker;
    
    @ManyToOne(optional=false)
    @JoinColumn(name="day_of_calendar_id", nullable=false)
    @OnDelete(action=OnDeleteAction.CASCADE)
    @JsonIgnore
    private DayOfCalendar dayOfCalendar;
    
    @ManyToOne(optional=false)
    @JoinColumn(name="status_id", nullable=false)
    @OnDelete(action=OnDeleteAction.CASCADE)
    @JsonIgnore
    private Status status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getHours() {
        return hours;
    }

    public void setHours(Date hours) {
        this.hours = hours;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public DayOfCalendar getDayOfCalendar() {
        return dayOfCalendar;
    }

    public void setDayOfCalendar(DayOfCalendar dayOfCalendar) {
        this.dayOfCalendar = dayOfCalendar;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
}

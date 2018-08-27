/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timetable_server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 *
 * @author a.zolotarev
 */
@Entity
@Table(name="worker")
@EntityListeners(AuditingEntityListener.class)
public class Worker implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    private String name;
    
    @ManyToOne
    @JoinColumn(name="post_id", nullable=false)
   // @JsonIgnore
    private Post post;
    
    @ManyToOne
    @JoinColumn(name="department_id", nullable=false)
    @OnDelete(action=OnDeleteAction.CASCADE)
   // @JsonIgnore
    private Department department;
    
    @ManyToOne
    @JoinColumn(name="calendar_id", nullable=false)
   // @JsonIgnore
    private Calendar calendar;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }
    
}

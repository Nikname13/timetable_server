/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timetable_server.controller;

import com.example.timetable_server.exception.ResourceNotFoundException;
import com.example.timetable_server.model.Calendar;
import com.example.timetable_server.model.Department;
import com.example.timetable_server.repository.CalendarRepository;
import com.example.timetable_server.repository.DepartmentRepository;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author a.zolotarev
 */
@RestController
@RequestMapping("/api")
public class CalendarController {
    @Autowired
    CalendarRepository calendarRepository;
    
    @GetMapping("/calendar")
    public List<Calendar> getAllCalendars(){
        return calendarRepository.findAll();
    }
    
    @GetMapping("/calendar/{id}")
    public Calendar getCalendarById(@PathVariable(value="id") Long calendarId){
        return findById(calendarId);
    }
    
    @PostMapping("/calendar")
    public Calendar createCalendar(@Valid @RequestBody Calendar calendar){
        return calendarRepository.save(calendar);
    }
    
    @PutMapping("/calendar/{id}")
    public Calendar updateCalendar(@PathVariable(value="id") Long calendarId, @Valid @RequestBody Calendar calendarDetail){
        Calendar calendar=findById(calendarId);
        calendar.setName(calendarDetail.getName());
        return calendarRepository.save(calendar);
    }
    
    @DeleteMapping("/calendar/{id}")
    public ResponseEntity<?> deleteCalendar(@PathVariable(value="id") Long calendarId){
        calendarRepository.delete(findById(calendarId));
        return ResponseEntity.ok().build();
    }
    
    private Calendar findById(Long calendarId){
        return calendarRepository.findById(calendarId).orElseThrow(()->new ResourceNotFoundException("Календарь", "id", calendarId));
    }
}

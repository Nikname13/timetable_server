/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timetable_server.controller;

import com.example.timetable_server.exception.ResourceNotFoundException;
import com.example.timetable_server.model.Calendar;
import com.example.timetable_server.model.DayOfCalendar;
import com.example.timetable_server.repository.CalendarRepository;
import com.example.timetable_server.repository.DayOfCalendarRepository;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public class DayOfCalendarController {

    @Autowired
    DayOfCalendarRepository dayOfCalendarRepository;

    @Autowired
    CalendarRepository calendarRepository;

    @GetMapping("/calendar/{calendarId}/days_of_calendar")
    public Page<DayOfCalendar> getAllDaysByCalendar(@PathVariable(value = "calendarId") Long calendarId,
            Pageable pageable) {
        return dayOfCalendarRepository.findByCalendarId(calendarId, pageable);
    }

    @PostMapping("/calendar/{calendarId}/day_of_calendar")
    public void createDay(@PathVariable(value = "calendarId") Long calendarId,
            @Valid @RequestBody List<DayOfCalendar> days) {
        Calendar calendar = calendarRepository.findById(calendarId).orElseThrow(() -> new ResourceNotFoundException("Календарь", "id", calendarId));
        for (DayOfCalendar day : days) {
            day.setCalendar(calendar);
            dayOfCalendarRepository.save(day);
        }
    }

    @PutMapping("/calendar/{calendarId}/day_of_calendar/{dayId}")
    public DayOfCalendar updateDay(@PathVariable(value = "calendarId") Long calendarId,
            @PathVariable(value = "dayId") Long dayId,
            @Valid @RequestBody DayOfCalendar dayDetail) {
        if(!calendarRepository.existsById(calendarId)){
            throw new ResourceNotFoundException("Календарь", "id", calendarId);
        }
        return dayOfCalendarRepository.findById(dayId).map(day -> {
            day.setHours(dayDetail.getHours());
            day.setStatus(dayDetail.getStatus());
           return dayOfCalendarRepository.save(day);
        }).orElseThrow(() -> new ResourceNotFoundException("День", "id", dayId));
    }
}

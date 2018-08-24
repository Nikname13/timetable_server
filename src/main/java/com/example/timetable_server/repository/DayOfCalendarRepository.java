/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timetable_server.repository;

import com.example.timetable_server.model.DayOfCalendar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author a.zolotarev
 */
@Repository
public interface DayOfCalendarRepository extends JpaRepository<DayOfCalendar,Long>{
    Page<DayOfCalendar> findByCalendarId(Long calendarId, Pageable pageable);
}

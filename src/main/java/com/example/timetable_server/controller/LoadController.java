/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timetable_server.controller;

import com.example.timetable_server.exception.ResourceNotFoundException;
import com.example.timetable_server.model.Load;
import com.example.timetable_server.model.Worker;
import com.example.timetable_server.repository.LoadRepository;
import com.example.timetable_server.repository.WorkerRepository;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author a.zolotarev
 */
@RestController
@RequestMapping("/api")
public class LoadController {
    
    @Autowired
    LoadRepository loadRepository;
    
    @Autowired
    WorkerRepository workerRepository;
    
    @GetMapping("/worker/{workerId}/load")
    public Page<Load> getAllLoadByWorker(@PathVariable (value="workerId") Long workerId,
            @Valid @RequestParam (value="startDate") @DateTimeFormat(iso = ISO.DATE) Date startDate,
            @Valid @RequestParam (value="endDate") @DateTimeFormat(iso = ISO.DATE) Date endDate,
            Pageable pageable){
        return loadRepository.findByDayOfYearBetween(startDate, endDate, pageable);
    }
    
    @PostMapping("/worker/{workerId}/load")
    public void createLoad(@PathVariable (value="workerId") Long workerId, @Valid @RequestBody List<Load> loads){
        Worker worker=workerRepository.findById(workerId).orElseThrow(() -> new ResourceNotFoundException("Сотрудник", "id", workerId));
        for(Load load:loads){
            load.setWorker(worker);
            loadRepository.save(load);
        }
    }
    
    @PutMapping("/worker/{workerId}/load/{loadId}")
    public Load updateLoad(@PathVariable (value="workerId") Long workerId, @PathVariable (value="loadId") Long loadId, @RequestBody Load loadDetail){
        if(!workerRepository.existsById(workerId)){
            throw new ResourceNotFoundException("Сотрудник", "id", workerId);
        }
        return loadRepository.findById(loadId).map(load->{
            load.setDescription(loadDetail.getDescription());
            load.setDayOfYear(loadDetail.getDayOfYear());
            load.setStatus(loadDetail.getStatus());
            load.setHours(loadDetail.getHours());
            return loadRepository.save(load);
        }).orElseThrow(() -> new ResourceNotFoundException("Нагрузка", "id", loadId));
    }
    
    @DeleteMapping("/worker/{workerId}/load/{loadId}")
    public ResponseEntity<?> deleteLoad(@PathVariable(value="loadId") Long loadId){
        loadRepository.delete(loadRepository.findById(loadId).orElseThrow(() -> new ResourceNotFoundException("Нагрузка", "id", loadId)));
        return ResponseEntity.ok().build();
    }
}

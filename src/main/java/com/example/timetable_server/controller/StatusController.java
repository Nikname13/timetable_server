/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timetable_server.controller;

import com.example.timetable_server.exception.ResourceNotFoundException;
import com.example.timetable_server.model.Status;
import com.example.timetable_server.repository.StatusRepository;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author a.zolotarev
 */
@RestController
@RequestMapping("/api")
public class StatusController {
    
    @Autowired
    StatusRepository statusRepository;
    
    @GetMapping("/status")
    public List<Status> getAllStatus(){
        return statusRepository.findAll();
    }
    
    @GetMapping("/status/{statusId}")
    public Status getStatusById(@PathVariable (value="statusId") Long statusId){
        return findById(statusId);
    }
    
    @PostMapping("/status")
    public Status createStatus(@Valid @RequestBody Status status){
        return statusRepository.save(status);
    }
    
    @DeleteMapping("/status/{statusId}")
     public ResponseEntity<?> deleteWorker(@PathVariable(value = "statusId") Long statusId) {
        statusRepository.delete(findById(statusId));
        return ResponseEntity.ok().build();
    }
    
    private Status findById(Long statusId){
        return statusRepository.findById(statusId).orElseThrow(()->new ResourceNotFoundException("Статус","id",statusId));
    }
}

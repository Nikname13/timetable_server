/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timetable_server.controller;

import com.example.timetable_server.exception.ResourceNotFoundException;
import com.example.timetable_server.model.User;
import com.example.timetable_server.model.Worker;
import com.example.timetable_server.repository.WorkerRepository;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author a.zolotarev
 */
@RestController
@RequestMapping("/api")
public class WorkerController {

    @Autowired
    WorkerRepository workerRepository;

    @GetMapping("/worker")
    public List<Worker> getAllWorkers(@RequestParam(value = "department", required = false) Long departmentId) {
        System.out.println(departmentId);
        if (departmentId != null) {
            return workerRepository.findByDepartmentId(departmentId);
        }
        return workerRepository.findAll();
    }

    @GetMapping("/worker/{id}")
    public Worker getWorkerById(@PathVariable(value = "id") Long workerId) {
        return findById(workerId);
    }

    @PostMapping("/worker")
    public Worker createWorker(@Valid @RequestBody Worker worker) {
        return workerRepository.save(worker);
    }

    @PutMapping("/worker/{id}")
    public Worker updateWorker(@PathVariable(value = "id") Long workerId, @Valid @RequestBody Worker workerDetail) {
        Worker worker = findById(workerId);
        worker.setName(workerDetail.getName());
        worker.setPost(workerDetail.getPost());
        worker.setCalendar(workerDetail.getCalendar());
        worker.setDepartment(workerDetail.getDepartment());
        return workerRepository.save(worker);
    }

    @DeleteMapping("/worker/{id}")
    public ResponseEntity<?> deleteWorker(@PathVariable(value = "id") Long workerId) {
        workerRepository.delete(findById(workerId));
        return ResponseEntity.ok().build();
    }

    private Worker findById(Long workerId) {
        return workerRepository.findById(workerId).orElseThrow(() -> new ResourceNotFoundException("Сотрудник", "id", workerId));
    }
}

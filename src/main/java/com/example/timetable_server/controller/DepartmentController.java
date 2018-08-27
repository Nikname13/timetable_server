/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timetable_server.controller;

import com.example.timetable_server.exception.ResourceNotFoundException;
import com.example.timetable_server.model.Department;
import com.example.timetable_server.repository.DepartmentRepository;
import com.example.timetable_server.repository.UserRepository;
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
public class DepartmentController {
        
    @Autowired
    DepartmentRepository departmentRepository;
    
    @GetMapping("/department")
    public List<Department> getAllDepartments(){
        return departmentRepository.findAll();
    }
    
    @GetMapping("/department/{id}")
    public Department getDepartmentById(@PathVariable(value="id") Long departmentId){
      Department department=  findById(departmentId);
        return department;
    }
    
    @PostMapping("/department")
    public Department createDepartment(@Valid @RequestBody Department department){
        System.out.println(department.getName());
        return departmentRepository.save(department);
    }
    
    @PutMapping("/department/{id}")
    public Department updateDepartment(@PathVariable(value="id") Long departmentId, @Valid @RequestBody Department departmentDetail){
        Department department=findById(departmentId);
        department.setName(departmentDetail.getName());
        return departmentRepository.save(department);
    }
    
    @DeleteMapping("/department/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable(value="id") Long departmentId){
        departmentRepository.delete(findById(departmentId));
        return ResponseEntity.ok().build();
    }
    
    private Department findById(Long departmentId){
        return departmentRepository.findById(departmentId).orElseThrow(()->new ResourceNotFoundException("Отдел", "id", departmentId));
    }
}

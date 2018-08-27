/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timetable_server.controller;

import com.example.timetable_server.exception.ResourceNotFoundException;
import com.example.timetable_server.model.User;
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
public class UserController {
    
    @Autowired
    UserRepository userRepository;
    
    @GetMapping("/user")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    
    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable(value="id") Long userId){
        return findById(userId);
    }
    
    @PostMapping("/user")
    public User createUser(@Valid @RequestBody User user){
        return userRepository.save(user);
    }
    
    @PutMapping("/user/{id}")
    public User updateUser(@PathVariable(value="id") Long userId, @Valid @RequestBody User userDetail){
        User user=findById(userId);
        user.setLogin(userDetail.getLogin());
        user.setSecret(userDetail.getSecret());
        user.setIsAdmin(userDetail.isIsAdmin());
        user.setSessionId(userDetail.getSessionId());
        user.setDepartment(userDetail.getDepartment());
        user.setSession_time(userDetail.getSession_time());
        return userRepository.save(user);
    }
    
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value="id") Long userId){
        userRepository.delete(findById(userId));
        return ResponseEntity.ok().build();
    }
    
    private User findById(Long userId){
        return userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("Пользователь", "id", userId));
    }
}

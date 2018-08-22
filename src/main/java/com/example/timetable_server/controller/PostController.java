/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timetable_server.controller;

import com.example.timetable_server.exception.ResourceNotFoundException;
import com.example.timetable_server.model.Post;
import com.example.timetable_server.repository.PostRepository;
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
public class PostController {
    
    @Autowired
    PostRepository postRepository;
    
    @GetMapping("/post")
    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }
    
    @GetMapping("/post/{id}")
    public Post getPostById(@PathVariable(value="id") Long postId){
        return findById(postId);
    }
    
    @PostMapping("/post")
    public Post createPost(@Valid @RequestBody Post post){
        return postRepository.save(post);
    }
    
    @PutMapping("/post/{id}")
    public Post updatePost(@PathVariable(value="id") Long postId, @Valid @RequestBody Post postDetail){
        Post post=findById(postId);
        post.setName(postDetail.getName());
        return postRepository.save(post);
    }
    
    @DeleteMapping("/post/{id}")
    private ResponseEntity<?> deletePost(@PathVariable(value="id") Long postId){
        Post post=findById(postId);
        postRepository.delete(post);
        return ResponseEntity.ok().build();
    }
    
    private Post findById(Long postId){
        return postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Должность", "id", postId));
    }
    
}

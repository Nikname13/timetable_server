/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timetable_server.controller;

import com.example.timetable_server.exception.ResourceNotFoundException;
import com.example.timetable_server.model.Worker;
import com.example.timetable_server.repository.FileRepository;
import com.example.timetable_server.repository.WorkerRepository;
import java.io.File;
import com.example.timetable_server.model.WorkersFile;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
public class FileController {
    
    private static String sDIR="StaticUpLoadDir";

    @Autowired
    FileRepository fileRepository;

    @Autowired
    WorkerRepository workerRepository;

    @GetMapping("/worker/{workerId}/file")
    public List<WorkersFile> getAllFileByWorker(@PathVariable(value = "workerId") Long workerId) {
        return fileRepository.findFileByWorkerId(workerId);
    }

    @GetMapping("/worker/{workerId}/file/{fileId}")
    public File getFileById(@PathVariable(value = "workerId") Long workerId, @PathVariable(value = "fileId") Long fileId) {
        WorkersFile fileEntry = fileRepository.findById(fileId).orElseThrow(() -> new ResourceNotFoundException("Файл", "id", fileId));
        Worker worker = findById(workerId);
        String filePath = "C:" + File.separator + getBaseFilePath(worker) + File.separator + fileEntry.getPath();
        File fileResponse = new File(filePath);
        return fileResponse;
    }
    
    @PostMapping("/worker/{workerId}/file")
    public void createFile(@PathVariable(value = "workerId") Long workerId,
            @Valid @RequestBody List<File> files){
        Worker worker =findById(workerId);
        String savePath= getBaseFilePath(worker);
        File fileSaveDir=new File("C:\\"+savePath);
        if(!fileSaveDir.exists()){
        fileSaveDir.mkdirs();
        }
        for(File file:files){
            WorkersFile fileResponse=new WorkersFile();
            fileResponse.setName(file.getName());
            fileResponse.setWorker(worker);
            fileResponse.setPath(UUID.randomUUID().toString().concat(fileResponse.getName()));
            fileRepository.save(fileResponse);
        }
    }
    
   private Worker findById(Long workerId) {
        return workerRepository.findById(workerId).orElseThrow(() -> new ResourceNotFoundException("Сотрудник", "id", workerId));
    }
   
   private String getBaseFilePath(Worker worker){
       return sDIR + File.separator + worker.getDepartment().getName() + File.separator + worker.getId() + worker.getName();
   }
}

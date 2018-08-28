/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.timetable_server.repository;

import com.example.timetable_server.model.WorkersFile;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author a.zolotarev
 */
@Repository
public interface FileRepository extends JpaRepository<WorkersFile,Long> {
    List<WorkersFile> findFileByWorkerId(Long id);
}

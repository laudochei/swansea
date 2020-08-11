/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reg.service;

import com.reg.data.StudentDao;
import com.reg.model.Student;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Me
 */


public interface StudentService {
    Student findNo(Integer id);
    List<Student> findAll();
    void save(Student student);
    void update(Student student);
    void delete(Integer id);
}

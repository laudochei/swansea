/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reg.service;

import com.reg.data.StudentDao;
import com.reg.model.Student;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service("studentService")
public class StudentServiceImpl implements StudentService {
    
    StudentDao studentDao;

    @Autowired
    public void setStudentDao(StudentDao studentDao) {
	this.studentDao = studentDao;
    }

    @Override
    public Student findNo(Integer id) {
        System.out.println("Check this id2: " + id);
        return studentDao.findNo(id);
    }
      
    
    @Override
    public List<Student> findAll() {
        return studentDao.findAll();
    }
    
    
    
    @Override 
    public void save(Student student){
        studentDao.save(student);
    }
    
    
    
    @Override 
    public void update(Student student){
        studentDao.update(student);
    }
    
    
    @Override 
    public void delete(Integer id){
        studentDao.delete(id);
    }
        
    
    
    
}




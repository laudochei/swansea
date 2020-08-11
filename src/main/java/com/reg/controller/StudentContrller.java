/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reg.controller;

import com.reg.model.Student;
import com.reg.service.StudentService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author Me
 */




@RestController
@RequestMapping(value = "/student")
public class StudentContrller {
    
        private StudentService studentService;
	@Autowired
	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	    
        // list page
        @RequestMapping(value = "/studentlist", method=GET)
        public List<Student> listStudent(Model model) {
            return studentService.findAll();
        }
        
        
        //display a single record
        @RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> getStudent(@PathVariable("id") Integer id) {
                System.out.println("Check this id1: " + id);
		Student student = studentService.findNo(id);
		if (student == null) {
			return new ResponseEntity("No record found for student with ID: " + id, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(student , HttpStatus.OK);
        }
        
        
        
        //add a single record
        @RequestMapping(value = "/add", method = RequestMethod.POST, headers = "Accept=application/json")
        public ResponseEntity<Void> adddProduct(@RequestBody Student student, UriComponentsBuilder ucb) {   

            studentService.save(student);
            HttpHeaders headers = new HttpHeaders();
            URI companyUri = ucb.path("/student/").path(String.valueOf(student.getId())).build().toUri();
            headers.setLocation(companyUri);
            headers.add("StudentID", String.valueOf(student.getId()));
            ResponseEntity<Void> responseEntity = new ResponseEntity<Void> (headers, HttpStatus.CREATED);
            return responseEntity;
        }
        
        
        //update a single record
        @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
        public ResponseEntity<Void> updateStudent(@PathVariable("id") Integer id, @RequestBody Student student)  {
            Student stud = studentService.findNo(id);
            if (stud == null) {
                return new ResponseEntity("No record to delete with ID: " + id, HttpStatus.NOT_FOUND);
            } 
            studentService.update(student);
            String Msg ="Record updated for Student ID: " + student.getId();
            HttpHeaders headers = new HttpHeaders();
            headers.add("SuccessMsg", Msg);
            ResponseEntity<Void> responseEntity = new ResponseEntity<Void> (headers, HttpStatus.CREATED);
            
            return new ResponseEntity("Record updated for student with ID: " + id,HttpStatus.OK);
        }
        
        
        //delete a single record
        @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
        public ResponseEntity<Student>  deleteStudent(@PathVariable("id") Integer id) {
            Student student  = studentService.findNo(id);
            if (student == null) {
                return new ResponseEntity("No record to delete with ID: " + id, HttpStatus.NOT_FOUND);
            }  
           
            studentService.delete(id);
            return new ResponseEntity("Record deleted for student with ID: " + id,HttpStatus.OK);
            
        } 
        
        
        
        

}
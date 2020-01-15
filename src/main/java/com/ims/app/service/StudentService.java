package com.ims.app.service;

import java.util.Arrays;
import java.util.HashSet;

import javax.validation.Valid;
                                                              
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ims.app.entity.Role;
import com.ims.app.entity.Student;
import com.ims.app.repository.RoleRespository;
import com.ims.app.repository.StudentRepository;

@Service
public class StudentService {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private RoleRespository roleRespository;
	@Autowired
	private StudentRepository studentRepository;
	
	public void saveUser(@Valid Student student) {
		student.setPassword(bCryptPasswordEncoder.encode(student.getPassword()));
		student.setActive(1);
		  Role userRole = roleRespository.findByRole("ADMIN");
		  student.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		  studentRepository.save(student);
		
	}

	public Student findByusername(String name) {
		// TODO Auto-generated method stub
		return studentRepository.findByusername(name);
	}

	
	
	
	
}

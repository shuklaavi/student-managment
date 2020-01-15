package com.ims.app.repository;


import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.ims.app.entity.Student;




@Repository
public interface StudentRepository extends CrudRepository <Student, Long> { 
    List<Student> findByName(String name);

	

	Student findByusername(String name);

   
	
}
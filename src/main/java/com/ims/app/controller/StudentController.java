package com.ims.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ims.app.entity.Student;
import com.ims.app.repository.StudentRepository;
import com.ims.app.service.StudentService;


@Controller
@RequestMapping("/students/")
public class StudentController {

    private final StudentRepository studentRepository;
    
    @Autowired
    private StudentService studentService;

    @Autowired
    public StudentController(StudentRepository studentRepository) {
    	this.studentRepository = studentRepository;
    }

    @GetMapping("signup")
    public String showSignUpForm(Student student) {
        return "add-student";
    }

    @GetMapping("list")
    public String showUpdateForm(Model model) {
        model.addAttribute("students", studentRepository.findAll());
        return "show";
    }

    @PostMapping("add")
    public String addStudent(@Valid Student student, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-student";
        }

        studentService.saveUser(student);
        return "redirect:list";
    }

    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Student student = studentRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid student Id:" + id));
        	//	studentRepository.findById(id).orElseThrow(() - > new IllegalArgumentException("Invalid student Id:" + id));
        model.addAttribute("student", student);
        return "update-student";
    }

    @PostMapping("update/{id}")
    public String updateStudent(@PathVariable("id") long id, @Valid Student student, BindingResult result,
        Model model) {
        if (result.hasErrors()) {
            student.setId(id);
            return "update-student";
        }

        studentRepository.save(student);
        model.addAttribute("students", studentRepository.findAll());
        return "show";
    }

    @GetMapping("delete/{id}")
    public String deleteStudent(@PathVariable("id") long id, Model model) {
        Student student = studentRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid student Id:" + id));
        studentRepository.delete(student);
        model.addAttribute("students", studentRepository.findAll());
        return "show";
    }
    
    @RequestMapping(value= {"home/home"}, method=RequestMethod.GET)
    public ModelAndView home() {
     ModelAndView model = new ModelAndView();
     Authentication auth = SecurityContextHolder.getContext().getAuthentication();
     Student student = studentService.findByusername(auth.getName());
     
     model.addObject("username", student.getUsername());
     
     model.setViewName("home/home");
     return model;
    }
    
    
}
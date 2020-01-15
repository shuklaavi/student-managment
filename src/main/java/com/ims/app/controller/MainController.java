package com.ims.app.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller

public class MainController {

	@RequestMapping("/")
    public String index() {
        return "index";
    }
	
	@RequestMapping(value= {"/login"}, method=RequestMethod.GET)
	 public ModelAndView login() {
	      ModelAndView model = new ModelAndView();
	      model.setViewName("user/login");
	      return model;
	 }
	
	
	
}

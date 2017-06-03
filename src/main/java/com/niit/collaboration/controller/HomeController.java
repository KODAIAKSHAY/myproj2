package com.niit.collaboration.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class HomeController {
	@CrossOrigin(origins="http://localhost:8081")
	@RequestMapping(value="/",method = RequestMethod.GET)
	public String getIndexPage() 
	{
		
		return "home";
	}
}

package com.example.Backend2020;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller //Viktigt att komma ihåg
@SpringBootApplication
public class DemoApplication {
	@RequestMapping("/hello")
	@ResponseBody
	String Home() {
		
		return "Hello World!";
	}
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

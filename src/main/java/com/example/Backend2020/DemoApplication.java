package com.example.Backend2020;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
@SpringBootApplication
public class DemoApplication {
	Connectin contin = Connectin.getInstance();
	@RequestMapping(value = "/addUser",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String addUser(@ModelAttribute User user) {
		contin.ConnectTo("JDBC_DATABASE_URL");
		contin.addUser(user.getName(), user.getProffesion());
		return "Tried adding user";
	}
	@RequestMapping("/")
	String get(){
		return "try going to /userlist";
	}
	@RequestMapping(value = "/userlist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	//index
	public @ResponseBody
	List<User> userlist() { //return list of objects with jackson lib
		contin.ConnectTo("JDBC_DATABASE_URL");
		return contin.getAllUsers();
	}
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	private String getPassword() {
		try {
			byte[] password = Files.readAllBytes(Paths.get("C:/Users/AnimeDasho/eclipseee-workspace/mysqlpassword.txt"));
			return new String(password, "UTF-8");
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	} 

}
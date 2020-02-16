package com.example.Backend2020;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class MyController {
	Connectin contin = Connectin.getInstance();
	
	@RequestMapping(value = "/addUser",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	String addUser(@RequestBody User user) {
		try {
		System.out.println(user.getName() + user.getProffesion());
		contin.ConnectTo("JDBC_DATABASE_URL");
		contin.addUser(user.getName(), user.getProffesion());
		return "Added User Succesfully";
		}catch(SQLException e){
			return "Error:" + e.getMessage();
		}
	}	
	
	@RequestMapping(value = "")
	public @ResponseBody
		String home(){
			return "Try going to /userlist or /clearUsers";
		}
	@RequestMapping(value = "/clearUsers", method = RequestMethod.GET)
	public @ResponseBody
		String clearUsers() {
			try {
				contin.ConnectTo("JDBC_DATABASE_URL");
				contin.clearUsers();
				return "table cleared";
			}catch(SQLException e){
				return "Error:" + e.getMessage();
			}
	}
	@RequestMapping(value = "/userList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<User> userList() { //return list of objects with jackson lib
		try {
			contin.ConnectTo("JDBC_DATABASE_URL");
			return contin.getAllUsers();
		} catch (SQLException e) {
			return null;
		}
		
	}
	@RequestMapping(value = "/userList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	String postCollection(@ModelAttribute User user) {
		try {
			contin.ConnectTo("JDBC_DATABASE_URL");
			contin.addUser(user.getName(), user.getProffesion());
			return "Added User Succesfully";
		}catch(SQLException e){
			return "Error:" + e.getMessage();
		}
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

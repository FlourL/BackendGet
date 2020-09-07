package com.example.Backend2020;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class MyController {
	Connectin contin = Connectin.getInstance();
	
	@RequestMapping(value = "/addTagDoc",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	String addTagDoc(@RequestBody TagDocDB tagDoc) {
		//System.out.println(tagDoc.getName() + " " + tagDoc.getFilepath() + " " +  tagDoc.getStringOfTags());
		try {
			contin.ConnectTo("JDBC_DATABASE_URL");
			contin.addTagDoc(tagDoc);
			return "--ADDED TAGDOC";
		} catch (SQLException e) {
			e.printStackTrace();
			return "--FAILED ADDING TAGDOC";
		}
		
	}	
	
	@RequestMapping(value = "")
	public @ResponseBody
		String home(){
			return "Try going to /userlist";
		}
	@RequestMapping(value = "/clearTagDocs", method = RequestMethod.GET)
	public @ResponseBody
		String clearTagDocs(){
			try {
				contin.ConnectTo("JDBC_DATABASE_URL");
				//contin.clearUsers();
				return "--TABLE CLEARED";
			} catch (SQLException e) {
				e.printStackTrace();
				return "--FAILED CLEARING TABLE";
			}
	}
	@RequestMapping(value = "/tagDocList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<TagDocDB> tagDocList() { //return list of objects with jackson lib
		try {
			contin.ConnectTo("JDBC_DATABASE_URL");
			return contin.getAllTagDocs();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
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

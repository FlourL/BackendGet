package com.example.Backend2020;

	import java.sql.Connection;
	import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
	
	public class Connectin {
		private static final Connectin contin = new Connectin();
		private Connection conn;
		private Connectin() { 
			super();
		}
		public synchronized void ConnectTo(String ip ,String username, String password ){
			try {
				if(conn != null) {
					conn.close();
				}
				conn = DriverManager.getConnection(
						"jdbc:mysql://" + username + ":" + password + "@" + ip + "/"
						);
				System.out.println("Connected to database");
				conn.prepareStatement("CREATE DATABASE IF NOT EXISTS user_db;").executeUpdate(); //must write Schema apparently
				conn.setCatalog("user_db");
				System.out.println("USERDB REACHED");
				conn.prepareStatement( //this whole statement would have to be remade for modification purposes, but yeah
						"CREATE TABLE IF NOT EXISTS Users (" + 
						"id Integer NOT NULL AUTO_INCREMENT," + 
						"name VARCHAR(255)," + 
						"proffesion VARCHAR(255)," + 
						"CONSTRAINT user_id PRIMARY KEY (id)" + 
						");").executeUpdate();
				System.out.println("CREATED TABLE");
			}catch(SQLException e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
		public synchronized void ConnectTo(String systemEnvUrl) {
			try {
				if(conn != null) {
					conn.close();
				}
				conn = DriverManager.getConnection(System.getenv(systemEnvUrl));
				System.out.println("Connected to database");
				//initDBAndTable();
				conn.prepareStatement("CREATE SCHEMA IF NOT EXISTS user_db;").executeUpdate(); //must write Schema apparently
				conn.setCatalog("user_db");
				System.out.println("USERDB REACHED");
				conn.prepareStatement( //this whole statement would have to be remade for modification purposes, but yeah
						"CREATE TABLE IF NOT EXISTS Users (" + 
						"id SERIAL NOT NULL PRIMARY KEY," + 
						"name VARCHAR(255)," + 
						"proffesion VARCHAR(255)" +
						");").executeUpdate();
				System.out.println("CREATED TABLE");
			}catch(SQLException e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
		public static Connectin getInstance() {
			return contin;
		}
		public synchronized List<User> getAllUsers(){
			try {
				List<User> userlist = new ArrayList<>();
				String query = ("SElECT * FROM Users ");
				PreparedStatement stmt = conn.prepareStatement(query);
				ResultSet rs = stmt.executeQuery();
				while(rs.next()) {
					User temp = new User();
					temp.setId(rs.getInt("id"));
					temp.setName(rs.getString("name"));
					temp.setProffesion(rs.getString("proffesion"));
					userlist.add(temp);
					
				}
				return userlist;
				
				
			}catch(Exception e) {
				return null;
			}
		}
		public synchronized void addUser(String fullname, String proffesion) {
			try {
				//When surrounded by ' and ' one can write "DROP TABLE Users" and all that jazz with no worries
				conn.prepareStatement("INSERT INTO Users (name,proffesion) Values ('" + fullname + "','" + proffesion + "');").executeQuery();
				System.out.println("user added");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		public synchronized void clearUsers() {
			try {
				conn.prepareStatement("TRUNCATE TABLE Users;");
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}


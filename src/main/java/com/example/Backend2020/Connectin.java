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
		private String tablename = "Users";
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
				initDBTable(conn, "user_db", "Users");
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
				initDBTable(conn, "user_db", "Users");
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
				String query = ("SElECT * FROM " + this.tablename);
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
			
			
		}private synchronized void initDBTable(Connection conn, String dbName, String tableName) {
			try {
			conn.prepareStatement("CREATE DATABASE IF NOT EXISTS " + dbName + " ;");
			conn.setCatalog("user_db");
			conn.prepareStatement(
					"CREATE TABLE IF NOT EXISTS "+ tableName + " (" + 
					"id Integer NOT NULL AUTO_INCREMENT," + 
					"name VARCHAR(255)," + 
					"proffesion VARCHAR(255)," + 
					"CONSTRAINT user_id PRIMARY KEY (id)" + 
					");").executeUpdate();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}


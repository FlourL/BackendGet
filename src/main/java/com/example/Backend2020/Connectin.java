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
		String db_name = "tagDoc_db";
		String table_name = "TagDocs";
		private Connectin() { 
			super();
		}
		public synchronized void ConnectTo(String systemEnvUrl)throws SQLException {
				if(conn != null) {
					conn.close();
				}
				conn = DriverManager.getConnection(System.getenv(systemEnvUrl));
				System.out.println("Connected to database");
				//initDBAndTable();
				conn.prepareStatement("CREATE SCHEMA IF NOT EXISTS "+ db_name +";").executeUpdate(); //must write Schema apparently
				conn.setCatalog(db_name);
				System.out.println("TagDoc DB Reached");
				conn.prepareStatement( //this whole statement would have to be remade for modification purposes, but yeah
						"CREATE TABLE IF NOT EXISTS TagDocs (" +  
						"id SERIAL NOT NULL PRIMARY KEY," +
						"name VARCHAR(255)," + 
						"tags LONGTEXT," +
						"filepath MEDIUMTEXT" +
						");").executeUpdate();
				System.out.println("CREATED TABLE");
		}
		public static Connectin getInstance() {
			return contin;
		}
		public synchronized List<TagDocDB> getAllTagDocs()throws SQLException{
				List<TagDocDB> tdlist = new ArrayList<>();
				String query = ("SElECT * FROM " + table_name + ";");
				PreparedStatement stmt = conn.prepareStatement(query);
				ResultSet rs = stmt.executeQuery();
				while(rs.next()) {
					TagDocDB temp = new TagDocDB(rs.getInt("id"),rs.getString("filepath"));
					temp.addTagsByStringOfTags(rs.getString("tags"));
					tdlist.add(temp);
					
				}
				return tdlist;
		}
		public synchronized void addTagDoc(TagDocDB tagDoc)throws SQLException {
			try {
				//When surrounded by ' and ' one can write "DROP TABLE TagDocs" and all that jazz with no worries
				conn.prepareStatement("INSERT INTO TagDocs(name,tags,filepath) Values ('" + tagDoc.getName() + "','" + tagDoc.getStringOfTags() + "," + tagDoc.getFilepath() + "');").executeQuery();
				System.out.println("TagDoc added");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


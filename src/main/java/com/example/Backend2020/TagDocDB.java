package com.example.Backend2020;

import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TagDocDB {
	protected int id;
	protected String name;
	protected String tags; //String of Tags
	protected String filePath;
	protected JSONArray jsonTags; //JSONArray of Tags
	
	public TagDocDB(int id, String filePath) {
		this.id = id;
		this.filePath = filePath;
		this.name = Paths.get(filePath).getFileName().toString();
		addTagsByStringOfTags(tags);
	}
	public void TagDocDb(TagDocWrapper tdWrapper) {
		this.name = tdWrapper.getName();
		jsonTags = new JSONArray(tdWrapper.getTags());
		this.filePath = tdWrapper.getFilePath();
	}
	//Getter/Setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFilepath() {
		return this.filePath;
	}
	public String getStringOfTags() {
		return tags;
	}
	//Tag Handling (SQL TRANSFER)
	public void addTagsByStringOfTags(String stringTags) {
		String[] tagTempArray = stringTags.split(Pattern.quote("|"));
		try {
			tags = stringTags;
			jsonTags = new JSONArray(tagTempArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	//JSON TO STRING
	public String toJsonString(boolean includeId) {
		String jsonString = "";
		try {
		JSONObject jsonO = new JSONObject();
		if(includeId)
			jsonO.put("id", this.id);
		jsonO.put("name", this.name);
		jsonO.put("filepath", this.filePath);
		jsonO.put("tags", jsonTags);
		}catch(JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}

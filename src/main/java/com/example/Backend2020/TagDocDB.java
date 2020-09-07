package com.example.Backend2020;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class TagDocDB {
	protected int id;
	protected String name;
	protected ArrayList<String> tags;
	protected Path filepath;
	
	public TagDocDB(String filepath) {
		this.filepath = Paths.get(filepath);
		this.name = this.filepath.getFileName().toString();
		tags = new ArrayList<String>();
	}
	public TagDocDB(int id, String filepath) {
		this.id = id;
		this.filepath = Paths.get(filepath);
		this.name = this.filepath.getFileName().toString();
		tags = new ArrayList<String>();
	}
	//Tag Handling Methods
	public boolean containsTag(String tag) {
		return tags.contains(tag);
	}
	public void addTagsByStringOfTags(String stringTags) {
		String[] tagTempArray = stringTags.split(Pattern.quote("|"));
		tags = new ArrayList<String>(Arrays.asList(tagTempArray));
	}
	public void addTag(String tag) {
		tags.add(tag);
	}
	public void removeTag(String tag) {
		tags.remove(tag);
	}
	public boolean hasTag(String tag){
		return tags.contains(tag);
	}
	public String getStringOfTags(){
		String stringTags = "";
		for(int i = 0; i < tags.size(); i++) {
			stringTags += tags.get(i);
			if(i != (tags.size() - 1)) { //Ugly solution
				stringTags += "|";
			}
		}
		return stringTags;
	}
	//Getter/Setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setFilePath(String filepath) {
		this.filepath = Paths.get(filepath);
	}
	public String getFilepath() {
		return this.filepath.toString();
	}
}

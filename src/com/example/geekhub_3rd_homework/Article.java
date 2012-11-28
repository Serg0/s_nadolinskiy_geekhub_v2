package com.example.geekhub_3rd_homework;
import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;




@DatabaseTable
public class Article {
	@DatabaseField
	private String title;
	@DatabaseField
	private String content;
	@DatabaseField
	private String published;
	@DatabaseField
	private String updated;
	@DatabaseField
	private String id;	
	@DatabaseField
	private Boolean isLiked;
	
	public Article(String OuterTitle, String OuterContent, String OuterPublished, String updated, String id) {
		// TODO Auto-generated constructor stub
		title=OuterTitle;
		content=OuterContent;
		
		// 2012-10-18T12:05:16.918-07:00
		published = OuterPublished.substring(0, 10)+" "+OuterPublished.substring(11, 16);
		
		isLiked = false;
		
	}
	
	public Article(){}
	
	
	
	
	public String getTitle() {
		return title;
	}
	
	public String getContent() {
		return content;
	}
	
	public String getPublished() {
		return published; 
	}

	public Boolean getIsLiked() {
		return isLiked;
	}

	public void changeIsLiked(Boolean isLiked) {
		this.isLiked = !isLiked;
	}


}
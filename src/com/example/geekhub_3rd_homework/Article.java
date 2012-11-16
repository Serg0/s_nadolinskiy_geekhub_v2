package com.example.geekhub_3rd_homework;





public class Article {
	public Article(String OuterTitle, String OuterContent, String OuterPublished) {
		// TODO Auto-generated constructor stub
		title=OuterTitle;
		content=OuterContent;
		
		// 2012-10-18T12:05:16.918-07:00
		published = OuterPublished.substring(0, 10)+" "+OuterPublished.substring(11, 16);
		
	}
	
	String title;
	String content;
	String published;
	
	
	public String getTitle() {
		return title;
	}
	
	public String getContent() {
		return content;
	}
	
	public String getPublished() {
		return published; 
	}


}
package com.example.geekhub_3rd_homework;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;




@DatabaseTable(daoClass = ArticleDAO.class)
public class Article {
	@DatabaseField(columnName = "title")
	private String title;
	@DatabaseField(columnName = "content")
	private String content;
	@DatabaseField(columnName = "published")
	private String published;
	@DatabaseField(columnName = "updated")
	private String updated;
	@DatabaseField(columnName = "id", unique = true)
	private String id;	
	@DatabaseField(columnName = "isLiked")
	private Boolean isLiked;
		
	public String getId() {
		return id;
	}

	public Article(String OuterTitle, String OuterContent, String OuterPublished, String OuterUpdated, String OuterId) {
		title=OuterTitle;
		content=OuterContent;
		published = OuterPublished.substring(0, 10)+" "+OuterPublished.substring(11, 16);
		id = OuterId;
		updated = OuterUpdated;
		isLiked = false;
		
	}
	
	public String getUpdated() {
		return updated;
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
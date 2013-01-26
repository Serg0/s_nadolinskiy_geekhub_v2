package com.nadolinskyi.serhii.geekhub_3rd_homework.models;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.nadolinskyi.serhii.geekhub_3rd_homework.db.ArticleDAO;




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
	@DatabaseField(columnName = "link")
	private String link;
		
	public String getId() {
		return id;
	}

	public Article(String OuterTitle, String OuterContent, String OuterPublished, String OuterUpdated, String OuterId, String OuterLink) {
		title=OuterTitle;
		content=OuterContent;
		published = OuterPublished.substring(0, 10)+" "+OuterPublished.substring(11, 16);
		id = OuterId;
		updated = OuterUpdated;
		isLiked = false;
		link = OuterLink;
	}
	
	public String getLink() {
		return link;
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
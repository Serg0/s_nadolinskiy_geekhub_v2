package com.nadolinskyi.serhii.geekhub_3rd_homework.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable 
public class MyDBContent {
	@DatabaseField(generatedId = true)
    private long id;
	@DatabaseField
    private String date;
	@DatabaseField
    private String title;
	@DatabaseField
    private String content;
    
    public MyDBContent (int contentPos) {
//     this.date = DataProvider.getFeed().get(contentPos).getPublished();
//     this.title = DataProvider.getFeed().get(contentPos).getTitle();
//     this.content = DataProvider.getFeed().get(contentPos).getContent();
    }
    public MyDBContent () {
//        this.date = DataProvider.getFeed().get(contentPos).getPublished();
//        this.title = DataProvider.getFeed().get(contentPos).getTitle();
//        this.content = DataProvider.getFeed().get(contentPos).getContent();
       }
    
    public long getID () {return id;}
    public String getDate () {return date;}
    public String getTitle () {return title;}
    public String getContent () {return content;}
   }
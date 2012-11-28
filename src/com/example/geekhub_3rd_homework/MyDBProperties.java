package com.example.geekhub_3rd_homework;

import java.io.Serializable;
import com.j256.*;
import com.j256.ormlite.field.*;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable 
public class MyDBProperties {
	@DatabaseField
    private String eTag;
	@DatabaseField
    private String last_modified;
	@DatabaseField
    private String host;
//	@DatabaseField
//    private String content;
    
    public MyDBProperties (String OuterETAG, String LastModified, String Host) {
     this.eTag = OuterETAG;
     this.last_modified = LastModified;
     this.host = Host;

    }
    public MyDBProperties () {
//        this.date = DataProvider.getFeed().get(contentPos).getPublished();
//        this.title = DataProvider.getFeed().get(contentPos).getTitle();
//        this.content = DataProvider.getFeed().get(contentPos).getContent();
       }
    
    public String getETag () {return eTag;}
    public String getLastModified () {return last_modified;}
    public String getSourceHost() {return host;}
   
   }
package com.example.geekhub_3rd_homework;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable 
public class MyDBProperties {
	@DatabaseField
    private String eTag;
	@DatabaseField
    private String last_modified;
	@DatabaseField
    private String host;
    public MyDBProperties (String OuterETAG, String LastModified, String Host) {
     this.eTag = OuterETAG;
     this.last_modified = LastModified;
     this.host = Host;

    }
    public MyDBProperties () {
       }
    public String getETag () {return eTag;}
    public String getLastModified () {return last_modified;}
    public String getSourceHost() {return host;}
   
   }
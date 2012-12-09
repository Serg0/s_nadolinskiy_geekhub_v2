package com.example.geekhub_3rd_homework;

import java.util.List;

import android.database.SQLException;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class MyDBcontentDAO extends BaseDaoImpl<MyDBContent, Integer>{

	   protected MyDBcontentDAO(ConnectionSource connectionSource,
	           Class<MyDBContent> dataClass) throws SQLException, java.sql.SQLException{
	       super(connectionSource, dataClass);
	   }

	   public List<MyDBContent> getAllMyDBcontent() throws SQLException, java.sql.SQLException{
			return this.queryForAll();

	   }
	}
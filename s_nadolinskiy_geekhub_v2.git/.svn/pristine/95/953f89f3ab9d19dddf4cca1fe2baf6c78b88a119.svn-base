package com.example.geekhub_3rd_homework;

import java.util.List;

import android.database.SQLException;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class MyDBPropertiesDAO extends BaseDaoImpl<MyDBProperties, Integer>{

	   protected MyDBPropertiesDAO(ConnectionSource connectionSource,
	           Class<MyDBProperties> dataClass) throws SQLException, java.sql.SQLException{
	       super(connectionSource, dataClass);
	   }

	   public List<MyDBProperties> getAllMyDBProperties() throws SQLException, java.sql.SQLException{
			return this.queryForAll();

	   }
	}
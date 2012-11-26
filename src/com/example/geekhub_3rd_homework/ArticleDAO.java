package com.example.geekhub_3rd_homework;


import java.util.List;

import android.database.SQLException;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class ArticleDAO extends BaseDaoImpl<Article, Integer>{

	   protected ArticleDAO(ConnectionSource connectionSource,
	           Class<Article> dataClass) throws SQLException, java.sql.SQLException{
	       super(connectionSource, dataClass);
	   }

	   public List<Article> getAllArticle() throws SQLException, java.sql.SQLException{
			return this.queryForAll();

	   }
	}
package com.example.geekhub_3rd_homework;


import java.util.List;

import android.database.SQLException;
import android.util.Log;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;

public class ArticleDAO extends BaseDaoImpl<Article, Integer>{

	   private static final String LOG_TAG = "ArticleDAO";

	protected ArticleDAO(ConnectionSource connectionSource,
	           Class<Article> dataClass) throws SQLException, java.sql.SQLException{
	       super(connectionSource, dataClass);
	   }

	   public List<Article> getAllArticle() throws SQLException, java.sql.SQLException{
			return this.queryForAll();
			

	   }
//	   public String[] getAllDates() throws SQLException, java.sql.SQLException{
//		
//		// TODO Auto-generated method stub
//
//		  
//		   
//		   return  this.queryBuilder().selectColumns().queryRawFirst();
//		   
//	}
	   
	   public void setLike(String id) throws SQLException, java.sql.SQLException{
		   
		   UpdateBuilder<Article, Integer> updateBuilder =  HelperFactory.GetHelper().getArticleDAO().updateBuilder();
				 updateBuilder.where().eq("id", id);
				 updateBuilder.updateColumnValue("isLiked", true);
				 updateBuilder.update();   
	  
	   }
	   public List<Article> getAllLikes() throws SQLException, java.sql.SQLException{
		   
		   QueryBuilder<Article, Integer> queryBuilder = queryBuilder();
		   queryBuilder.where().eq("isLiked", true);
		   PreparedQuery<Article> preparedQuery = queryBuilder.prepare();
		   List<Article> ArticleList = query(preparedQuery);
		   return ArticleList;
		   
		   
		   
	   }
	   
 public List<Article> getPostByID(String PostID) throws SQLException, java.sql.SQLException{
		   
		   QueryBuilder<Article, Integer> queryBuilder = queryBuilder();
		   queryBuilder.where().eq("ID", PostID);
		   PreparedQuery<Article> preparedQuery = queryBuilder.prepare();
		  // List<Article> ArticleList = query(preparedQuery);
		   if (query(preparedQuery).isEmpty()){
			   Log.d(LOG_TAG, "getPostByID query isEmpty ");
			   
			   return null;
			   }else{return query(preparedQuery);}
		   
		   
		   
	   }
	   	
	}
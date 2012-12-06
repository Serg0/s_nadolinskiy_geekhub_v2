package com.example.geekhub_3rd_homework;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper{

	  private static final String TAG = DatabaseHelper.class.getSimpleName();
	  private static final String DATABASE_NAME ="MyDB.db";
	   private static final int DATABASE_VERSION = 1;
	   private MyDBPropertiesDAO myDBcontentDAO = null;
	   private ArticleDAO articleDAO = null; 
	   
	   public DatabaseHelper(Context context){
	       super(context,DATABASE_NAME, null, DATABASE_VERSION);
	   }

	  @Override
	   public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource){
	       try
	       {
	           try {
				TableUtils.createTable(connectionSource, MyDBProperties.class);
				TableUtils.createTable(connectionSource, Article.class);
			} catch (java.sql.SQLException e) {
				 Log.e(TAG, "error creating DB java.sql.SQLException e " + DATABASE_NAME);
				e.printStackTrace();
			}
	       }
	       catch (SQLException e){
	           Log.e(TAG, "error creating DB " + DATABASE_NAME);
	           throw new RuntimeException(e);
	       }
	   }

	   @Override
	   public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVer,
	           int newVer){
	       try{
	           TableUtils.dropTable(connectionSource, MyDBProperties.class, true);
	           TableUtils.dropTable(connectionSource, Article.class, true);
	           onCreate(db, connectionSource);
	           Log.e(TAG, "Clear this from DB " + DATABASE_NAME);
	       }
	       catch (SQLException e){
	           Log.e(TAG,"error upgrading db "+DATABASE_NAME+"from ver "+oldVer);
	           throw new RuntimeException(e);
	       } catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
	   }

	   @Override
	   public void close(){
	       super.close();
	       myDBcontentDAO = null;
	       articleDAO = null;
	   }

	  
	   public MyDBPropertiesDAO getMyDBPropertiesDAO() throws SQLException, java.sql.SQLException{
	       if(myDBcontentDAO == null){
	    	   myDBcontentDAO = new MyDBPropertiesDAO(getConnectionSource(), MyDBProperties.class);
	       }
	       return myDBcontentDAO;
	   }
	   
	   public ArticleDAO getArticleDAO() throws SQLException, java.sql.SQLException{
	       if(articleDAO == null){
	    	   articleDAO = new ArticleDAO(getConnectionSource(), Article.class);
	       }
	       return articleDAO;
	   }
	   
	   
	}



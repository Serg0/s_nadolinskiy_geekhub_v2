package com.example.geekhub_3rd_homework;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper{

	  private static final String TAG = DatabaseHelper.class.getSimpleName();

	  //имя файла базы данных который будет храниться в /data/data/APPNAME/DATABASE_NAME.db
	  private static final String DATABASE_NAME ="MyDB.db";
	   
	  //с каждым увеличением версии, при нахождении в устройстве БД с предыдущей версией будет выполнен метод onUpgrade();
	   private static final int DATABASE_VERSION = 1;
	   
	   //ссылки на DAO соответсвующие сущностям, хранимым в БД
	  
	   private MyDBcontentDAO myDBcontentDAO = null;
	   private ArticleDAO articleDAO = null; 
	   
	   public DatabaseHelper(Context context){
	       super(context,DATABASE_NAME, null, DATABASE_VERSION);
	   }

	   //Выполняется, когда файл с БД не найден на устройстве
	  @Override
	   public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource){
	       try
	       {
	           try {
				TableUtils.createTable(connectionSource, MyDBContent.class);
				TableUtils.createTable(connectionSource, Article.class);
			} catch (java.sql.SQLException e) {
				// TODO Auto-generated catch block
				 Log.e(TAG, "error creating DB java.sql.SQLException e " + DATABASE_NAME);
				e.printStackTrace();
			}
	       }
	       catch (SQLException e){
	           Log.e(TAG, "error creating DB " + DATABASE_NAME);
	           throw new RuntimeException(e);
	       }
	   }

	   //Выполняется, когда БД имеет версию отличную от текущей
	   @Override
	   public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVer,
	           int newVer){
	       try{
	        //Так делают ленивые, гораздо предпочтительнее не удаляя БД аккуратно вносить изменения
	           TableUtils.dropTable(connectionSource, MyDBContent.class, true);
	           TableUtils.dropTable(connectionSource, Article.class, true);
	           onCreate(db, connectionSource);
	           Log.e(TAG, "Clear dsts from DB " + DATABASE_NAME);
	       }
	       catch (SQLException e){
	           Log.e(TAG,"error upgrading db "+DATABASE_NAME+"from ver "+oldVer);
	           throw new RuntimeException(e);
	       } catch (java.sql.SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
	   
	  
	   
	  
	   
	   //выполняется при закрытии приложения
	   @Override
	   public void close(){
	       super.close();
	       myDBcontentDAO = null;
	       articleDAO = null;
	   }

	  
	   public MyDBcontentDAO getMyDBcontentDAO() throws SQLException, java.sql.SQLException{
	       if(myDBcontentDAO == null){
	    	   myDBcontentDAO = new MyDBcontentDAO(getConnectionSource(), MyDBContent.class);
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



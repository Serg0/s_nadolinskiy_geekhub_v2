package com.example.geekhub_3rd_homework;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBConnector {
  
    // Данные базы данных и таблиц
    private static final String DATABASE_NAME = "Likes.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "MyData";
    
    // Название столбцов
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_DATE = "Date";
    private static final String COLUMN_TITLE = "Title";
    private static final String COLUMN_CONTENT = "Content";
    
    // Номера столбцов
    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_DATE = 1;
    private static final int NUM_COLUMN_TITLE = 2;
    private static final int NUM_COLUMN_CONTENT = 3;
    
    private SQLiteDatabase mDataBase;
    
    public MyDBConnector (Context context) {
     // открываем (или создаем и открываем) БД для записи и чтения
     OpenHelper mOpenHelper = new OpenHelper(context);
     mDataBase = mOpenHelper.getWritableDatabase();
    }
    
    // Класс для создания БД
    private class OpenHelper extends SQLiteOpenHelper {
    
     OpenHelper(Context context) {
      super(context, DATABASE_NAME, null, DATABASE_VERSION);	}
      @Override
      public void onCreate(SQLiteDatabase db) {
       String query = "CREATE TABLE " + TABLE_NAME + " (" + 
         COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
         COLUMN_DATE + " TEXT, " + 
         COLUMN_TITLE + " TEXT, " + 
         COLUMN_CONTENT + " TEXT); ";
       db.execSQL(query);
      }
    
      @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
       onCreate(db);
      }
     }
   }
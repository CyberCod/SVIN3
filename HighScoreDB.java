package com.smiths;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class HighScoreDB {
	
	 private static final int DATABASE_VERSION = 1;
	 private static final String SCORE_TABLE_NAME = "highscore";
	 private static final String SCORE_TABLE_CREATE = "CREATE TABLE "
	                                                     + SCORE_TABLE_NAME
	                                                     + " (_id INTEGER PRIMARY KEY autoincrement, "
	                                                     + "name TEXT NOT NULL, score INTEGER  NOT NULL)";
	 private static final String DATABASE_NAME = "scores.db";
	 // The index (key) column name for use in where clauses.
	 public static final String KEY_ID = "_id";
	 
	 // The name and column index of each column in your database.
	 public static final String KEY_NAME = "name";
	 public static final String KEY_SCORE = "score";
	 public static final int NAME_COLUMN = 1;
	 public static final int NUMBER_COLUMN = 2;
	 public static final int SCORE_COLUMN = 3;
	 
	 SQLiteDatabase db;
	 private final Context ctx;
	 private final HighScoreDbHelper dbHelper;
	
	 
	 
	 //Constructor
	public HighScoreDB(Context context) {
        this.ctx = context;
        ctx.deleteDatabase(SCORE_TABLE_NAME);
        dbHelper = new HighScoreDbHelper(context);
        db = dbHelper.getWritableDatabase();
        
    }
	
	
	
	public void close() {
        if (db != null) {
            db.close();
        }
 
    }
 
    public void createRow(String name, int score) {
        ContentValues intialValue = new ContentValues();
        intialValue.put("name", name);
        intialValue.put("score", score);
        db.insertOrThrow(SCORE_TABLE_NAME, null, intialValue);
 
    }
 
    public void deleteRow(long rowId) {
        db.delete(SCORE_TABLE_NAME, "_id=" + rowId, null);
    }
 
    public Cursor GetAllRows() {
        try {
            return db.query(SCORE_TABLE_NAME, new String[] { "_id", "name", "score" }, null,
                            null, null, null, "score DESC");
        } catch (SQLException e) {
            Log.i("Error on query", e.toString());
            return null;
        }
 
    }
 
    public void updateRow(long _id, String name, String score) {
        ContentValues args = new ContentValues();
        args.put("name", name);
        args.put("score", score);
        db.update(SCORE_TABLE_NAME, args, "_id=" + _id, null);
    }
	
	
	//inner class
    private static class HighScoreDbHelper extends SQLiteOpenHelper {
 
        public HighScoreDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
 
        }
 
        @Override
        public void onCreate(SQLiteDatabase db) {
 
            try {
                db.execSQL(SCORE_TABLE_CREATE);
            } catch (SQLException e) {
                Log.i("Error", "Error making database");
                e.printStackTrace();
            }
        }
 
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + SCORE_TABLE_NAME);
            onCreate(db);
        }
 
    }
 
   
    public void insertDefaultData(){
    	deleteRow(0);
    	deleteRow(1);
    	deleteRow(2);
    	deleteRow(3);
    	deleteRow(4);
    	deleteRow(5);
    	deleteRow(6);
    	deleteRow(7);
    	deleteRow(8);
    	deleteRow(9);
    	db.execSQL("INSERT INTO " + SCORE_TABLE_NAME +" Values ('0','player1',25000);");
    	db.execSQL("INSERT INTO " + SCORE_TABLE_NAME +" Values ('1','player1',15000);");
    	db.execSQL("INSERT INTO " + SCORE_TABLE_NAME +" Values ('2','player1',10000);");
    	db.execSQL("INSERT INTO " + SCORE_TABLE_NAME +" Values ('3','player1',7000);");
    	db.execSQL("INSERT INTO " + SCORE_TABLE_NAME +" Values ('4','player1',5000);");
    	db.execSQL("INSERT INTO " + SCORE_TABLE_NAME +" Values ('5','player1',2000);");
    	db.execSQL("INSERT INTO " + SCORE_TABLE_NAME +" Values ('6','player1',1000);");
    	db.execSQL("INSERT INTO " + SCORE_TABLE_NAME +" Values ('7','player1',750);");
    	db.execSQL("INSERT INTO " + SCORE_TABLE_NAME +" Values ('8','player1',500);");
    	db.execSQL("INSERT INTO " + SCORE_TABLE_NAME +" Values ('9','player1',200);");
    }
    
 
    
 
}
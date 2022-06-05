package com.example.gamecenterjosepfs.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 3;
    public static final String DB_NAME = "scoredb";
    public static final String TABLE_NAME = "scores";
    public static final String ID_COL = "id";
    public static final String NAME_COL = "name";
    public static final String TIME_COL = "time";
    public static final String SCORE_COL = "score";
    public static final String GAME_COL = "game";

    public DbHelper (Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME_COL + " TEXT," +
                TIME_COL + " TEXT," +
                SCORE_COL + " TEXT," +
                GAME_COL + " TEXT)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE " + TABLE_NAME);
        onCreate(db);
    }
}

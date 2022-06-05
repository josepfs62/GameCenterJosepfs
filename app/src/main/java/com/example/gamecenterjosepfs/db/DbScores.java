package com.example.gamecenterjosepfs.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.gamecenterjosepfs.entities.Scores;

import java.util.ArrayList;

public class DbScores extends DbHelper{

    Context context;

    public DbScores(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertScore(String name, String time, String score, String game){

        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("time", time);
            values.put("score", score);
            values.put("game", game);

            id = db.insert(TABLE_NAME, null, values);

        } catch (Exception ex) {
            ex.toString();
        }

        return id;
    }

    public ArrayList<Scores> showScores(){

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Scores> listScores = new ArrayList<>();
        Scores score = null;
        Cursor cursorScores = null;

        cursorScores = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if(cursorScores.moveToFirst()){
            do{
                score = new Scores();
                score.setId(cursorScores.getInt(0));
                score.setName(cursorScores.getString(1));
                score.setTime(cursorScores.getString(2));
                score.setScore(cursorScores.getString(3));
                score.setGame(cursorScores.getString(4));
                listScores.add(score);
            } while (cursorScores.moveToNext());
        }

        cursorScores.close();

        return listScores;
    }
}

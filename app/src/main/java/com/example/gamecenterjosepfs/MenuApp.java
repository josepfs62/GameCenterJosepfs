package com.example.gamecenterjosepfs;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gamecenterjosepfs.db.DbHelper;

public class MenuApp extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mainmenu);

        button = (Button) findViewById(R.id.buttonTo2048);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open2048();
            }
        });

        button = (Button) findViewById(R.id.buttonToPeg);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPeg();
            }
        });

        button = (Button) findViewById(R.id.buttonScoreBoard);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openScoreBoard();
            }
        });

    }

    public void open2048() {
        DbHelper dbHelper = new DbHelper(MenuApp.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Intent intent = new Intent(this, InputUsers.class);
        intent.putExtra("game","2048");
        startActivity(intent);
    }

    public void openPeg() {
        DbHelper dbHelper = new DbHelper(MenuApp.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Intent intent = new Intent(this, InputUsers.class);
        intent.putExtra("game","peg");
        startActivity(intent);
    }

    public void openScoreBoard() {
        DbHelper dbHelper = new DbHelper(MenuApp.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Intent i = new Intent(this, MainScore.class);
        startActivity(i);
    }

    public void openUsers() {
        DbHelper dbHelper = new DbHelper(MenuApp.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        Intent intent = new Intent(this, MenuPeg.class);
//        startActivity(intent);
    }
}

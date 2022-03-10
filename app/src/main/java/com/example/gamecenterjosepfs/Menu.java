package com.example.gamecenterjosepfs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity {

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

    }

    public void open2048(){
        Intent intent = new Intent(this, Main2048.class);
        startActivity(intent);
    }

    public void openPeg(){
        Intent intent = new Intent(this, MainPeg.class);
        startActivity(intent);
    }
}
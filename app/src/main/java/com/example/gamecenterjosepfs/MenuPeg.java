package com.example.gamecenterjosepfs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MenuPeg extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mainmenupeg);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openpeguno();
            }
        });

        button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openpegdos();
            }
        });

        button = findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openpegtres();
            }
        });

        button = findViewById(R.id.button4);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openpegcuatro();
            }
        });

    }

    public void openpeguno(){
        Intent intent = new Intent(this, MainPeg.class);
        intent.putExtra("layout", 1);
        startActivity(intent);
    }
    public void openpegdos(){
        Intent intent = new Intent(this, MainPeg.class);
        intent.putExtra("layout", 2);
        startActivity(intent);
    }
    public void openpegtres(){
        Intent intent = new Intent(this, MainPeg.class);
        intent.putExtra("layout", 3);
        startActivity(intent);
    }
    public void openpegcuatro(){
        Intent intent = new Intent(this, MainPeg.class);
        intent.putExtra("layout", 4);
        startActivity(intent);
    }
}
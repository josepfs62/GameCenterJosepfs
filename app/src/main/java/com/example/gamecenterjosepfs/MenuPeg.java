package com.example.gamecenterjosepfs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MenuPeg extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mainmenupeg);

        imageView = findViewById(R.id.layout1);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openpeguno();
            }
        });

        imageView = findViewById(R.id.layout2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openpegdos();
            }
        });

        imageView = findViewById(R.id.layout3);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openpegtres();
            }
        });

        imageView = findViewById(R.id.layout4);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openpegcuatro();
            }
        });

    }

    public void openpeguno(){
        Intent intent = new Intent(this, MainPeg.class);
        intent.putExtra("layout", 1);
        String userName = getIntent().getStringExtra("user");
        intent.putExtra("user", userName);
        startActivity(intent);
    }
    public void openpegdos(){
        Intent intent = new Intent(this, MainPeg.class);
        intent.putExtra("layout", 2);
        String userName = getIntent().getStringExtra("user");
        intent.putExtra("user", userName);
        startActivity(intent);
    }
    public void openpegtres(){
        Intent intent = new Intent(this, MainPeg.class);
        intent.putExtra("layout", 3);
        String userName = getIntent().getStringExtra("user");
        intent.putExtra("user", userName);
        startActivity(intent);
    }
    public void openpegcuatro(){
        Intent intent = new Intent(this, MainPeg.class);
        intent.putExtra("layout", 4);
        String userName = getIntent().getStringExtra("user");
        intent.putExtra("user", userName);
        startActivity(intent);
    }
}
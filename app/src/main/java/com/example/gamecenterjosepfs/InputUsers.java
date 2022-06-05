package com.example.gamecenterjosepfs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class InputUsers extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inputuser);

        Button sendButton = findViewById(R.id.play);
        Intent myIntent = getIntent();
        TextView nameUser = findViewById(R.id.nameUser);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myIntent.getStringExtra("game").equals("2048")) {
                    Intent intent = new Intent(InputUsers.this, Main2048.class);
                    intent.putExtra("user", nameUser.getText().toString());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(InputUsers.this, MenuPeg.class);
                    intent.putExtra("user", nameUser.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }


}

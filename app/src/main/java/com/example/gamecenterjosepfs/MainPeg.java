package com.example.gamecenterjosepfs;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.gridlayout.widget.GridLayout;

import java.util.ArrayList;

public class MainPeg extends AppCompatActivity {

    @SuppressLint({"ClickableViewAccessibility", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mainpeg);
        GridLayout gridLayout = (GridLayout) (findViewById(R.id.gridLayout));
        ArrayList<CustomButton> buttonsList = new ArrayList<>();

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            if(gridLayout.getChildAt(i) instanceof Button){
                buttonsList.add((CustomButton) gridLayout.getChildAt(i));
//                gridLayout.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View view, MotionEvent event) {
//                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                            CustomButton currentButton = ((CustomButton) view);
//                            currentButton.setPulsado(!currentButton.getPulsado());
//                            if (currentButton.getPulsado()) {
//                                view.setBackground(Peg.this.getDrawable(R.drawable.roundedbuttonclicked));
//                            } else {
//                                view.setBackground(Peg.this.getDrawable(R.drawable.roundedbutton));
//                            }
//                        }
//                        return false;
//                    }
//                });
            }
        }
    }
}
package com.example.gamecenterjosepfs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

public class MainPeg extends AppCompatActivity {

    private CustomButton[][] buttonsList = new CustomButton[9][9];
    private CustomButton currentButton;
    private CustomButton previousButton = null;
    private Chronometer timer;
    private TextView score;
    private TextView surrenderButton;
    private boolean timerBoolean;
    private int pegs = 0;
    private String userName;
    private Intent myIntent;

    @SuppressLint({"ClickableViewAccessibility", "UseCompatLoadingForDrawables"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        myIntent = getIntent();
        userName = myIntent.getStringExtra("user");

        int layout = getIntent().getIntExtra("layout", 1);
        if (layout == 1) {
            setContentView(R.layout.activity_mainpeg);
            pegs = 44;
        }
        if (layout == 2) {
            setContentView(R.layout.activity_mainpeg2);
            pegs = 38;
        }
        if (layout == 3) {
            setContentView(R.layout.activity_mainpeg3);
            pegs = 36;
        }
        if (layout == 4) {
            setContentView(R.layout.activity_mainpeg4);
            pegs = 9;
        }
        score = findViewById(R.id.score);
        score.setText(Integer.toString(pegs));
        surrenderButton = findViewById(R.id.surrender);
        surrenderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                surrender();
            }
        });

        GridLayout gridLayout = (GridLayout) (findViewById(R.id.gridLayout));
        ((CustomButton)gridLayout.getChildAt(40)).setEmpty(true);

        timer = (Chronometer) findViewById(R.id.timer2);
        timerBoolean = true;

        int row = -1;
        for (int column = 0; column < gridLayout.getChildCount(); column++) {
            if (column % 9 == 0) row++;
            buttonsList[row][column % 9] = (CustomButton) gridLayout.getChildAt(column);
            buttonsList[row][column % 9].setI(row);
            buttonsList[row][column % 9].setJ(column % 9);
            gridLayout.getChildAt(column).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    if (timerBoolean) {
                        timer.start();
                    }
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        if (currentButton != null) {
                            previousButton = currentButton;
                            if (((CustomButton)view).getEmpty() && (previousButton.getEmpty())){
                                previousButton = null;
                                currentButton = null;
                                return false;
                            }
                        }
                        currentButton = (CustomButton) view;
                        if(!currentButton.getEmpty()){
                            currentButton.setPulsado(!currentButton.getPulsado());
                        }
                        if(previousButton != null && currentButton != null) {
                            boolean moved = validateMovement();
                            if (!moved && !previousButton.getEmpty() && !previousButton.toString().equals(currentButton.toString())){
                                previousButton.setPulsado(false);
                            }
                            if (!moved && currentButton.getEmpty()){
                                currentButton = null;
                                previousButton = null;
                            }
                            if(moved){
                                checkWin();
                            }
                        }

                    }
                    return false;
                }
            });
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public boolean validateMovement(){
        if(currentButton.getEmpty() && !previousButton.getEmpty()) {
            if ((previousButton.getJ()- currentButton.getJ() == 0) && (Math.abs(currentButton.getI() - previousButton.getI()) == 2) || (previousButton.getI()- currentButton.getI() == 0) && (Math.abs(currentButton.getJ() - previousButton.getJ()) == 2)) {
                CustomButton middleButton = buttonsList[previousButton.getI()+(currentButton.getI()- previousButton.getI())/2][previousButton.getJ()+(currentButton.getJ()- previousButton.getJ())/2];
                if(!middleButton.getEmpty()) {
                    currentButton.setPulsado(false);
                    currentButton.setEmpty(false);
                    currentButton = null;
                    middleButton.setBackground(this.getResources().getDrawable(R.drawable.emptybutton));
                    middleButton.setEmpty(true);
                    previousButton.setBackground(this.getResources().getDrawable(R.drawable.emptybutton));
                    previousButton.setEmpty(true);
                    previousButton = null;
                    pegs--;
                    score.setText(Integer.toString(pegs));
                    return true;
                }
            }
        }
        return false;
    }

//    private void checkLose(){
//        boolean lose = true;
//        for (int i = 0; i < buttonsList.length-1; i++) {
//            for (int j = 0; j < buttonsList[0].length; j++) {
//                if ((buttonsList[i][j].isEmpty) && (buttonsList[i+1][j].isEmpty)){
//                    lose = false;
//                }
//            }
//        }
//        for (int i = 0; i < buttonsList.length; i++) {
//            for (int j = 0; j < buttonsList[0].length-1; j++) {
//                if ((buttonsList[i][j].isEmpty) && (buttonsList[i][j+1].isEmpty)){
//                    lose = false;
//                }
//            }
//        }
//        if (lose){
//            LoseFragment loseFragment = new LoseFragment();
//            loseFragment.show(getSupportFragmentManager(), null);
//        }
//    }

    private void checkWin(){
        if(pegs == 1){
            WinFragmentPeg winFragmentPeg = new WinFragmentPeg();
            Bundle args = new Bundle();
            args.putInt("pegs", pegs);
            args.putString("userName", userName);
            args.putInt("time", (int) timer.getBase());
            winFragmentPeg.setArguments(args);
            winFragmentPeg.show(getSupportFragmentManager(), null);
        }
    }

    private void surrender(){
        SurrenderFragmentPeg surrenderFragmentPeg = new SurrenderFragmentPeg();
        Bundle args = new Bundle();
        args.putInt("pegs", pegs);
        args.putString("userName", userName);
        args.putInt("time", (int) timer.getBase());
        surrenderFragmentPeg.setArguments(args);
        surrenderFragmentPeg.show(getSupportFragmentManager(), null);
    }
}
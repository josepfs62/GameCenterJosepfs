package com.example.gamecenterjosepfs;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

public class MainPeg extends AppCompatActivity {

    private CustomButton[][] buttonsList = new CustomButton[9][9];
    private CustomButton currentButton;
    private CustomButton previousButton = null;

    @SuppressLint({"ClickableViewAccessibility", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mainpeg);
        GridLayout gridLayout = (GridLayout) (findViewById(R.id.gridLayout));
        ((CustomButton)gridLayout.getChildAt(40)).setEmpty(true);

        int fila = -1;
        for (int column = 0; column < gridLayout.getChildCount(); column++) {
            if (column % 9 == 0) fila++;
            buttonsList[fila][column % 9] = (CustomButton) gridLayout.getChildAt(column);
            buttonsList[fila][column % 9].setI(fila);
            buttonsList[fila][column % 9].setJ(column % 9);
            gridLayout.getChildAt(column).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
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
//                            Log.d("Fila", "Current Fila: " + currentButton.getI());
//                            Log.d("Columna", "Current Column: " + currentButton.getJ());
//                            Log.d("Fila", "Previous Fila: " + previousButton.getI());
//                            Log.d("Columna", "Previous Column: " + previousButton.getJ());
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
                                checkLose();
                            }
                        }

                    }
                    return false;
                }
            });
        }
    }

    private boolean validateMovement(){
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
                    return true;
                }
            }
        }
        return false;
    }

    private void checkLose(){
        boolean lose = true;
        for (int i = 0; i < buttonsList.length-1; i++) {
            for (int j = 0; j < buttonsList[0].length; j++) {
                if ((buttonsList[i][j].isEmpty) && (buttonsList[i+1][j].isEmpty)){
                    lose = false;
                }
            }
        }
        for (int i = 0; i < buttonsList.length; i++) {
            for (int j = 0; j < buttonsList[0].length-1; j++) {
                if ((buttonsList[i][j].isEmpty) && (buttonsList[i][j+1].isEmpty)){
                    lose = false;
                }
            }
        }
        if (lose){
            LoseFragment loseFragment = new LoseFragment();
            loseFragment.show(getSupportFragmentManager(), null);
        }
    }

    private void checkWin(){
        boolean win = false;
        int peg = 0;
        for (int i = 0; i < buttonsList.length; i++) {
            for (int j = 0; j < buttonsList.length; j++) {
                if(!buttonsList[i][j].isEmpty){
                    peg++;
                    if(peg >= 2){
                        break;
                    }
                }
            }
        }
        if(peg == 1){
            WinFragment winFragment = new WinFragment();
            winFragment.show(getSupportFragmentManager(), null);
        }
    }

}
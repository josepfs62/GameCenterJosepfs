package com.example.gamecenterjosepfs;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Main2048 extends AppCompatActivity {
    OnSwipeTouchListener onSwipeTouchListener;
    static TextView[][] textViewArray = new TextView[4][4];
    int count = 1;
    private int score = 0;
    TextView scoreText;
    boolean win = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main2048);
        onSwipeTouchListener = new OnSwipeTouchListener(this, findViewById(R.id.pantalla));
        scoreText = (android.widget.TextView) findViewById(R.id.score);

        //añadir textviews a la array
        for (int i = 0; i < textViewArray.length; i++)
        {
            for (int j = 0; j < textViewArray[i].length; j++) {
                int id = this.getResources().getIdentifier("cuadrado" + count, "id", this.getPackageName());
                TextView textView = findViewById(id);
                textViewArray[i][j] = textView;
                Log.d("TView Added", "Added " + count + textView);
                count += 1;
            }
        }

        onSwipeTouchListener.generateNumber();
    }

    public class OnSwipeTouchListener implements View.OnTouchListener {
        private final GestureDetector gestureDetector;
        Context context;

        OnSwipeTouchListener(Context ctx, View mainView) {
            gestureDetector = new GestureDetector(ctx, new GestureListener());
            mainView.setOnTouchListener(this);
            context = ctx;
        }
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }
        public class GestureListener extends GestureDetector.SimpleOnGestureListener {
            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                            } else {
                                onSwipeLeft();
                            }
                            result = true;
                        }
                    }
                    else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom();
                        } else {
                            onSwipeTop();
                        }
                        result = true;
                    }
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        }

        private void addHorizontal() {
            for (int j = 0; j <= 2; j++) {
                boolean calculate = false;
                for (int i = 0; i <= 3; i++) {
                    String actual = String.valueOf(textViewArray[i][j].getText());
                    String horizontal = String.valueOf(textViewArray[i][j + 1].getText());
                    if ((actual).equals(horizontal) && !calculate && !actual.equals("")) {
                        int valueInteger = Integer.parseInt(String.valueOf(textViewArray[i][j].getText()));
                        int aux = valueInteger * 2;
                        score += aux;
                        textViewArray[i][j].setText(String.valueOf(aux));
                        textViewArray[i][j + 1].setText("");
                        calculate = true;
                        checkWin(aux);
                    }
                }
            }
        }

        private void addVertical() {
            for (int i = 0; i <= 2; i++) {
                boolean calculate = false;
                for (int j = 0; j <= 3; j++) {
                    String actual = String.valueOf(textViewArray[i][j].getText());
                    String vertical = String.valueOf(textViewArray[i + 1][j].getText());
                    if ((actual).equals(vertical) && !calculate && !actual.equals("")) {
                        int valueInteger = Integer.parseInt(String.valueOf(textViewArray[i][j].getText()));
                        int aux = valueInteger * 2;
                        score += aux;
                        textViewArray[i][j].setText(String.valueOf(aux));
                        textViewArray[i + 1][j].setText("");
                        calculate = true;
                        checkWin(aux);
                    }
                }
            }
        }

        void onSwipeRight() {
            bucleSwipeRight();
            addHorizontal();
            bucleSwipeRight();
            generateNumber();
            changeColors();
            updateScore();
        }

        private void bucleSwipeRight(){
            for (int i = 0; i <= 3; i++) {
                //repetir 3 veces la secuencia para evitar que no pase todas las fichas a un lado
                for (int k = 0; k < 3; k++) {
                    for (int j = 0; j < 3; j++) {
                        if ((textViewArray[i][j].getText() != "") && (textViewArray[i][j + 1].getText() == "")) {
                            textViewArray[i][j + 1].setText(textViewArray[i][j].getText());
                            textViewArray[i][j].setText("");
                        }
                    }
                }
            }
        }

        void onSwipeLeft() throws InterruptedException {
            bucleSwipeLeft();
            addHorizontal();
            bucleSwipeLeft();
            generateNumber();
            changeColors();
            updateScore();
        }

        private void bucleSwipeLeft() {
            for (int i = 0; i <= 3; i++) {
                //repetir 3 veces la secuencia para evitar que no pase todas las fichas a un lado
                Log.d(TAG, "ñ NUMERO DE BUCLE " + i + "--------------------");
                for (int k = 0; k < 3; k++) {
                    for (int j = 3; j > 0; j--) {
                        Log.d(TAG, "ñ Fila " + k);
                        Log.d(TAG, "ñ Columna " + j);
                        if ((textViewArray[i][j].getText() != "") && (textViewArray[i][j - 1].getText() == "")) {
                            textViewArray[i][j - 1].setText(textViewArray[i][j].getText());
                            textViewArray[i][j].setText("");

                        }
                    }
                }
            }
        }

        void onSwipeTop() {
            bucleSwipeTop();
            addVertical();
            bucleSwipeTop();
            generateNumber();
            changeColors();
            updateScore();
        }

        private void bucleSwipeTop() {
            for (int j = 3; j >= 0; j--) {
                //repetir 3 veces la secuencia para evitar que no pase todas las fichas
                for (int k = 0; k < 3; k++) {
                    for (int i = 3; i >= 0; i--) {
                        if (i > 0) {
                            if ((textViewArray[i][j].getText() != "") && (textViewArray[i - 1][j].getText() == "")) {
                                textViewArray[i - 1][j].setText(textViewArray[i][j].getText());
                                textViewArray[i][j].setText("");
                            }
                        }
                    }
                }
            }
        }

        void onSwipeBottom() {
            bucleSwipeBottom();
            addVertical();
            bucleSwipeBottom();
            generateNumber();
            changeColors();
            updateScore();
        }

        private void bucleSwipeBottom(){
            for (int j = 0; j <= 3; j++) {
                //repetir 3 veces la secuencia para evitar que no pase todas las fichas
                for (int k = 0; k < 3; k++) {
                    for (int i = 0; i <= 3; i++) {
                        if (i < 3) {
                            if ((textViewArray[i][j].getText() != "") && (textViewArray[i + 1][j].getText() == "")) {
                                textViewArray[i + 1][j].setText(textViewArray[i][j].getText());
                                textViewArray[i][j].setText("");
                            }
                        }
                    }
                }
            }
        }

        private void updateScore(){
            Log.d(TAG, "updateScore: " + score);
            scoreText.setText(String.valueOf(score));
        }

        //funcion que cambia todos los colores de las casillas por el color correspondiente
        void changeColors(){
            for (int l = 0; l < 4; l++) {
                for (int p = 0; p < 4; p++) {
                    if (textViewArray[l][p].getText() == ""){
                        textViewArray[l][p].setBackgroundColor(Color.parseColor("#a9a9a9"));
                    }
                    if (textViewArray[l][p].getText() == "2"){
                        textViewArray[l][p].setBackgroundColor(Color.parseColor("#ffbc40"));
                    }
                    if (textViewArray[l][p].getText() == "4"){
                        textViewArray[l][p].setBackgroundColor(Color.parseColor("#FF0000"));
                    }
                    if (textViewArray[l][p].getText() == "8"){
                        textViewArray[l][p].setBackgroundColor(Color.parseColor("#0000FF"));
                    }
                    if (textViewArray[l][p].getText() == "16"){
                        textViewArray[l][p].setBackgroundColor(Color.parseColor("#00A36C"));
                    } else {
                        textViewArray[l][p].setBackgroundColor(Color.parseColor("#a9a9a9"));
                    }
                }
            }
        }

        //funcion que genera un 2 en un textview
        public void generateNumber() {
            boolean ocupado = true;
            for (int i = 0; i < textViewArray.length; i++) {
                for (int j = 0; j < textViewArray[i].length; j++) {
                    if ( String.valueOf(textViewArray[i][j].getText()).equals("")){
                        ocupado = false;
                    }
                }
            }
            if(ocupado){
                if(checkLose()){
                    Log.d(TAG, "PERDISTE");
                    LoseFragment loseFragment = new LoseFragment();
                    loseFragment.show(getSupportFragmentManager(), null);
                };
            }
            if (!ocupado){
                boolean number = true;
                while (number) {
                    int r = (int) Math.floor(Math.random() * 4);
                    int c = (int) Math.floor(Math.random() * 4);

                    if (textViewArray[r][c].getText().equals("")) {
                        //hacer probabilidad de que aparezca y que sea 2 o 4
                        //if (Math.random())
                        textViewArray[r][c].setText("2");
                        number = false;
                    }
                }
            }
        }

        public boolean checkLose(){
            boolean lose = true;
            for (int i = 0; i < textViewArray.length; i++) {
                for (int j = 0; j < textViewArray[1].length-1; j++) {
                    if (String.valueOf(textViewArray[i][j].getText()).equals(String.valueOf(textViewArray[i][j+1].getText()))){
                        lose = false;
                    }
                }
            }
            for (int i = 0; i < textViewArray.length-1; i++) {
                for (int j = 0; j < textViewArray[1].length; j++) {
                    if (String.valueOf(textViewArray[i][j].getText()).equals(String.valueOf(textViewArray[i+1][j].getText()))){
                        lose = false;
                    }
                }
            }
            return lose;
        }

        public void checkWin(int aux){
            if(aux == 2048 && !win){
                Log.d(TAG, "GANASTE");
                WinFragment winFragment = new WinFragment();
                winFragment.show(getSupportFragmentManager(), null);
                win = true;
            }
        };
    }
}
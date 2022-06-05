package com.example.gamecenterjosepfs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Main2048 extends AppCompatActivity {
    OnSwipeTouchListener onSwipeTouchListener;
    static TextView[][] textViewArray = new TextView[4][4];
    int count = 1;
    private int score = 0;
    TextView scoreText;
    boolean win = false;
    boolean finish = false;
    private Chronometer timer;
    private TextView surrenderButton;
    private boolean timerBoolean;
    private String userName;
    private Intent myIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main2048);
        onSwipeTouchListener = new OnSwipeTouchListener(this, findViewById(R.id.pantalla));
        scoreText = (android.widget.TextView) findViewById(R.id.score);
        timer = (Chronometer) findViewById(R.id.timer);
        timerBoolean = true;
        myIntent = getIntent();
        userName = myIntent.getStringExtra("user");

        //añadir textviews a la array
        for (int i = 0; i < textViewArray.length; i++)
        {
            for (int j = 0; j < textViewArray[i].length; j++) {
                int id = this.getResources().getIdentifier("cuadrado" + count, "id", this.getPackageName());
                TextView textView = findViewById(id);
                textViewArray[i][j] = textView;
                count += 1;
            }
        }

        surrenderButton = findViewById(R.id.surrender);
        surrenderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { surrender(); }
        });

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
                if (timerBoolean && !finish){
                    timer.start();
                }
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
                changeColors();
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
                        if(!finish){
                            score += aux;
                        }
                        textViewArray[i][j].setText(String.valueOf(aux));
                        textViewArray[i][j + 1].setText("");
                        calculate = true;
                        checkWin(aux);
                    }
                }
            }
            changeColors();
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
                        if(!finish){
                            score += aux;
                        }
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
            updateScore();
        }

        private void bucleSwipeLeft() {
            for (int i = 0; i <= 3; i++) {
                //repetir 3 veces la secuencia para evitar que no pase todas las fichas a un lado
                for (int k = 0; k < 3; k++) {
                    for (int j = 3; j > 0; j--) {
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
            if(!finish){
                scoreText.setText(String.valueOf(score));
            }
        }

        //funcion que cambia todos los colores de las casillas por el color correspondiente
        void changeColors(){
            for (TextView[] tv: textViewArray) {
                for (TextView tv1: tv) {
                    if (tv1.getText().equals("")){
                        tv1.setBackgroundColor(Color.parseColor("#e8e2f6"));
                        tv1.setTextColor(this.context.getResources().getColor(R.color.white));
                    }
                    else if (tv1.getText().equals("2")){
                        tv1.setBackgroundColor(Color.parseColor("#cabcec"));
                        tv1.setTextColor(this.context.getResources().getColor(R.color.white));
                    }
                    else if (tv1.getText().equals("4")){
                        tv1.setBackgroundColor(Color.parseColor("#ad96e1"));
                        tv1.setTextColor(this.context.getResources().getColor(R.color.white));
                    }
                    else if (tv1.getText().equals("8")){
                        tv1.setBackgroundColor(Color.parseColor("#8f6fd6"));
                        tv1.setTextColor(this.context.getResources().getColor(R.color.white));
                    }
                    else if (tv1.getText().equals("16")){
                        tv1.setBackgroundColor(Color.parseColor("#7149cb"));
                        tv1.setTextColor(this.context.getResources().getColor(R.color.white));
                    }
                    else if (tv1.getText().equals("32")){
                        tv1.setBackgroundColor(Color.parseColor("#5932b2"));
                        tv1.setTextColor(this.context.getResources().getColor(R.color.white));
                    }
                    else if (tv1.getText().equals("64")){
                        tv1.setBackgroundColor(Color.parseColor("#46278b"));
                        tv1.setTextColor(this.context.getResources().getColor(R.color.white));
                    }
                    else if (tv1.getText().equals("128")){
                        tv1.setBackgroundColor(Color.parseColor("#331c65"));
                        tv1.setTextColor(this.context.getResources().getColor(R.color.white));
                    }
                    else if (tv1.getText().equals("256")){
                        tv1.setBackgroundColor(Color.parseColor("#532ea6"));
                        tv1.setTextColor(this.context.getResources().getColor(R.color.white));
                    }
                    else if (tv1.getText().equals("512")){
                        tv1.setBackgroundColor(Color.parseColor("#532ea6"));
                        tv1.setTextColor(this.context.getResources().getColor(R.color.white));
                    }
                    else if (tv1.getText().equals("1024")){
                        tv1.setBackgroundColor(Color.parseColor("#40237f"));
                        tv1.setTextColor(this.context.getResources().getColor(R.color.white));
                    }
                    else if (tv1.getText().equals("2048")){
                        tv1.setBackgroundColor(Color.parseColor("#2d1859"));
                        tv1.setTextColor(this.context.getResources().getColor(R.color.white)); } else
                    {
                        tv1.setBackgroundColor(Color.parseColor("#190e33"));
                        tv1.setTextColor(this.context.getResources().getColor(R.color.white));
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
                    LoseFragment loseFragment = new LoseFragment();
                    loseFragment.show(getSupportFragmentManager(), null);
                    timer.stop();
                };
            }
            if (!ocupado){
                boolean number = true;
                while (number) {
                    int r = (int) Math.floor(Math.random() * 4);
                    int c = (int) Math.floor(Math.random() * 4);

                    if (textViewArray[r][c].getText().equals("")) {
                        if (Math.random() < 0.9){
                            if (Math.random() < 0.7) {
                                textViewArray[r][c].setText("2");
                                textViewArray[r][c].setTextColor(this.context.getResources().getColor(R.color.white));
                                textViewArray[r][c].setBackgroundColor(Color.parseColor("#cabcec"));
                            }
                            else{
                                textViewArray[r][c].setText("4");
                                textViewArray[r][c].setTextColor(this.context.getResources().getColor(R.color.white));
                                textViewArray[r][c].setBackgroundColor(Color.parseColor("#ad96e1"));
                            }
                        }
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
                timer.stop();
                finish = true;
                WinFragment2048 winFragment2048 = new WinFragment2048();
                Bundle args = new Bundle();
                args.putInt("score", score);
                args.putInt("time", (int) timer.getBase());
                args.putString("userName", userName);
                winFragment2048.setArguments(args);
                winFragment2048.show(getSupportFragmentManager(), null);
                win = true;
                timer.stop();
            }
        };
    }
    private void surrender(){
        timer.stop();
        finish = true;
        SurrenderFragment2048 surrenderFragment2048 = new SurrenderFragment2048();
        Bundle args = new Bundle();
        args.putInt("score", score);
        args.putString("userName", userName);
        long timeDeltaFromStartInMs = (SystemClock.elapsedRealtime() - timer.getBase());
        int numLevelsElapsed = (int) (timeDeltaFromStartInMs / 10000);
        args.putInt("time", numLevelsElapsed);
        surrenderFragment2048.setArguments(args);
        surrenderFragment2048.show(getSupportFragmentManager(), null);
    }
}
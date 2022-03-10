package com.example.gamecenterjosepfs;

import android.content.Context;
import android.util.AttributeSet;

public class CustomButton extends androidx.appcompat.widget.AppCompatButton {
    private boolean pulsado = false;
    public boolean isEmpty;
    private int i;
    private int j;

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean getPulsado() {
        return pulsado;
    }

    public void setPulsado(boolean pulsado) {
        this.pulsado = pulsado;
        if (this.getPulsado()) {
            this.setBackground(this.getResources().getDrawable(R.drawable.roundedbuttonclicked));
        } else {
            this.setBackground(this.getResources().getDrawable(R.drawable.roundedbutton));
        }
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public boolean getEmpty() {
        return isEmpty;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }
}


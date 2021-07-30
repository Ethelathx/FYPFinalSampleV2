package com.example.fypfinalsample;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CartEmpty extends AppCompatActivity {
    TextView tvEmpty, tvContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_empty);

        //==================MatchingGame==========================
        tvEmpty = findViewById(R.id.tvEmpty);
        tvContinue = findViewById(R.id.tvContinue);
        //==================MatchingGame==========================

        //==================Setup==========================
        tvEmpty.setText("Your cart is currently empty.");
        tvContinue.setText("Continue browsing here.");
        //==================Setup==========================

    }
}
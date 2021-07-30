package com.example.fypfinalsample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class Checkout extends AppCompatActivity {

    TextView tvSTotal, tvShipping, tvTotal, tvBtnLogin, tvReturnBtn ;
    EditText etEmailPhone, etFirstName, etLastName, etAddress, etApartment, etCity, etCountry, etPostalCode, etPhoneNumber;
    Button btnContinue;
    CheckBox cbOffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Intent intent = getIntent();
        String TotalPrice = intent.getStringExtra("totalprice");

        //==================MatchingGame==========================
        //----------------------TextView------------------
        tvSTotal = findViewById(R.id.tvSubtotal);
        tvShipping = findViewById(R.id.tvShipping);
        tvTotal = findViewById(R.id.tvTotal);
        tvBtnLogin = findViewById(R.id.tvLoginBtn);
        tvReturnBtn = findViewById(R.id.tvReturnBtn);

        //----------------------EditText------------------
        etEmailPhone = findViewById(R.id.etEmailPhone);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etAddress = findViewById(R.id.etAddress);
        etApartment = findViewById(R.id.etApartment);
        etCity = findViewById(R.id.etCity);
        etCountry = findViewById(R.id.etCountry);
        etPostalCode = findViewById(R.id.etPostalCode);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);

        //----------------------Button/CheckBox------------------
        cbOffer = findViewById(R.id.cbOffer);
        btnContinue = findViewById(R.id.btnContinue);
        //==================MatchingGame==========================


        //=====================Setup==========================
        tvSTotal.setText(String.format("$%.2f", Double.parseDouble(TotalPrice)));
        double total = Double.valueOf(TotalPrice) + 1.8;
        tvTotal.setText(String.format("$%.2f", total));
        //=====================Setup==========================


        //----------------------LoginTVTextHandle----------------------
        tvBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Checkout.this, Login.class);
                startActivity(intent);
            }
        });
        //----------------------LoginTVTextHandle----------------------

        //----------------------ReturnCartHandle----------------------
        tvReturnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Checkout.this, Summarylist.class);
                startActivity(intent);
            }
        });
        //----------------------ReturnCartHandle----------------------


        //----------------------ContinueButton----------------------
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CheckEntire
                //PassDB
                //NextPage
                Intent intent = new Intent(Checkout.this, Login.class);
                startActivity(intent);
            }
        });
        //----------------------ContinueButton----------------------
    }
}
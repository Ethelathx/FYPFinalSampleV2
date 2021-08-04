package com.example.fypfinalsample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

        //---------------------------------SharedPref-------------------------------------------
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Checkout.this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("totalprices", String.valueOf(total));
        editor.commit();
        //---------------------------------SharedPref-------------------------------------------


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
                checkEntry();
                if (checkEntry() == false) {
                    Toast.makeText(Checkout.this, "Certain fills cannot be empty",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    //---------------------------------SharedPref-------------------------------------------
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Checkout.this);
                    SharedPreferences.Editor editor = prefs.edit();
                    //---------------------------------SharedPref-------------------------------------------

                    checkBox();
                    if (checkBox() == true) {
                        editor.putString("cbCheck", "Yes");
                    }
                    else {
                        editor.putString("cbCheck", "No");
                    }
                    //------------------------ThrowAllInEditor-------------------------------
                    editor.putString("newsContact", etEmailPhone.getText().toString());
                    editor.putString("firstName", etFirstName.getText().toString());
                    editor.putString("lastName", etLastName.getText().toString());
                    editor.putString("address", etAddress.getText().toString());
                    editor.putString("apartment", etApartment.getText().toString());
                    editor.putString("city", etCity.getText().toString());
                    editor.putString("country", etCountry.getText().toString());
                    editor.putString("postal", etPostalCode.getText().toString());
                    editor.putString("number", etPhoneNumber.getText().toString());
                    editor.commit();
                    //------------------------ThrowAllInEditor-------------------------------

                    Intent intent = new Intent(Checkout.this, Login.class);
                    startActivity(intent);
                }

            }
        });
        //----------------------ContinueButton----------------------
    }



    //==========================Constructor=========================
    private boolean checkBox(){
        if (cbOffer.isChecked() == true){
            return true;
        }
        else {
            return false;
        }
    }

    private boolean checkEntry(){
        String ep = etEmailPhone.getText().toString();
        String ln = etEmailPhone.getText().toString();
        String ad = etEmailPhone.getText().toString();
        String ct = etEmailPhone.getText().toString();
        String c = etEmailPhone.getText().toString();
        String pc = etEmailPhone.getText().toString();
        String pn = etEmailPhone.getText().toString();

        if (ep.isEmpty() || ln.isEmpty() || ad.isEmpty() || ct.isEmpty() || c.isEmpty() || pc.isEmpty() || pn.isEmpty()){
            return false;
        }
        else {
            return true;
        }
    }
    //==========================Constructor=========================
}
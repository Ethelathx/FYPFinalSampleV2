package com.example.fypfinalsample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Summarylist extends AppCompatActivity {

    ListView lv;
    ArrayAdapter aa;
    ArrayList<CustomerOrder> newCus;
    TextView tvSubText, tvSubTotal;
    Button btnCheckout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summarylist);

        //==================MatchingGame==========================
        lv = (ListView) this.findViewById(R.id.lvlist);
        tvSubText = findViewById(R.id.SubTotalText);
        tvSubTotal = findViewById(R.id.subTotal);
        btnCheckout = findViewById(R.id.btnCheckout);
        //==================MatchingGame==========================

        //==================Setup==========================
        tvSubText.setText("Subtotal");
        //==================Setup==========================

        //-------------------DBSetupArray-------------------
        DBCusOrderTemp dbh = new DBCusOrderTemp(Summarylist.this);
        newCus = new ArrayList<CustomerOrder>();
        newCus.clear();
        newCus.addAll(dbh.getCustomerData());
        dbh.close();
        //-------------------------IfListEmptyHandle------------------------
        if (newCus.size() < 1){
            Intent intent = new Intent(Summarylist.this, CartEmpty.class);
            startActivity(intent);
        }
        else {
            aa = new SummarylistAdapter(Summarylist.this, R.layout.summarylist, newCus);
            lv.setAdapter(aa);
        }
        //-------------------------IfListEmptyHandle------------------------
        //-------------------DBSetupArray-------------------


        //==================------------SubTotal-----------=========================
        double totalPrice = 0;
        for (int i = 0; i < newCus.size(); i++ ){
            double price = dbh.getCustomerData().get(i).itemPrice;
            totalPrice = totalPrice + price;
        }
        tvSubTotal.setText(String.format("$%.2f",totalPrice));
        //==================------------SubTotal-----------=========================


        //==============================BtnCheckOutHandle=========================
        double finalTotalPrice = totalPrice;
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Summarylist.this, Checkout.class);
                i.putExtra("totalprice", String.valueOf(finalTotalPrice));
                startActivity(i);
            }
        });
        //==============================BtnCheckOutHandle=========================
    }

}
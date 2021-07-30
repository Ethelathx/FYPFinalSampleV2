package com.example.fypfinalsample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class InfoList extends AppCompatActivity {

    ImageView imageView;
    ImageButton plusQuantity, minusQuantity;
    TextView quantityNumber, itemName, itemPrice, itemDetails, pricePerPack, priceDesc;
    Button addToCart;
    int quantity = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_list);

        //===================Casting=====================
        itemDetails = findViewById(R.id.descriptioninfo);
        imageView = findViewById(R.id.imageViewInfo);
        plusQuantity = findViewById(R.id.addquantity);
        minusQuantity  = findViewById(R.id.subquantity);
        quantityNumber = findViewById(R.id.quantity);
        addToCart = findViewById(R.id.addtocart);
        itemName = findViewById(R.id.ItemNameinInfo);
        itemPrice = findViewById(R.id.ItemPrice);
        pricePerPack = findViewById(R.id.PricePerPack);
        priceDesc = findViewById(R.id.ItemPriceDescription);
        //===================Casting=====================

        //------------------GetIntentData---------------------
        Item itemData;
        Intent i = getIntent();
        itemData = (Item) i.getSerializableExtra("positionData");
        //------------------GetIntentData---------------------

        //------------------UISetup-------------
        quantityNumber.setText(String.valueOf(quantity));
        Glide.with(this).load(itemData.getItemPhoto()).into(imageView);
        itemName.setText(itemData.getItemName());
        itemDetails.setText(itemData.getItemDetails());
        itemPrice.setText(String.format("%.2f", itemData.getItemPrice()));
        pricePerPack.setText("Price per pack: " + String.format("$%.2f",itemData.getItemPrice()));
        //------------------UISetup-------------

        //==========================PlusButton============================
        plusQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double basePrice = itemData.itemPrice;
                quantity++;
                displayQuantity();
                double ItemPrice = basePrice * quantity;
                String setNewPrice = String.format("%.2f",ItemPrice);
                itemPrice.setText(setNewPrice);
            }
        });
        //==========================PlusButton============================
        //==========================MinusButton============================
        minusQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double basePrice = itemData.itemPrice;
                if (quantity < 1) {
                    Toast.makeText(InfoList.this, "Cant decrease quantity < 0", Toast.LENGTH_SHORT).show();
                } else {
                    quantity--;
                    displayQuantity();
                    double ItemPrice = basePrice * quantity;
                    String setNewPrice = String.format("%.2f",ItemPrice);
                    itemPrice.setText(setNewPrice);
                }
            }
        });
        //==========================MinusButton============================

        //===========================AddToCartTempDataBase=============================
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(itemPrice);
                //------------------AddCusDataBase-------------------
                //------------------DataToBeAdded--------------------
                String pname = itemName.getText().toString();
                String cname = "EmptyUserName";
                int quantity = Integer.parseInt(quantityNumber.getText().toString());
                double price = Double.parseDouble(itemPrice.getText().toString());
                //------------------DataToBeAdded--------------------
                if (quantity >= 1) {
                    DBCusOrderTemp dbc = new DBCusOrderTemp(InfoList.this);
                    long inserted_id = dbc.insertCustomer(pname, cname, quantity, price);
                    dbc.close();
                    //------------------AddCusDataBase-------------------
                    Intent intent = new Intent(InfoList.this, Summarylist.class);
                    startActivity(intent);
                    if (inserted_id != -1) {
                        Toast.makeText(InfoList.this, "Success adding to Cart",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(InfoList.this, "Failed to add to Cart",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                //===========================CatchPreventAdd0===================
                else {
                    Toast.makeText(InfoList.this, "Quantity cannot be 0",
                            Toast.LENGTH_SHORT).show();
                }
                //===========================CatchPreventAdd0===================
            }
        });
        //===========================AddToCartTempDataBase=============================


    }

    //==================FunctionZone========================
    private void displayQuantity() {
        quantityNumber.setText(String.valueOf(quantity));
    }
    //==================FunctionZone========================
}
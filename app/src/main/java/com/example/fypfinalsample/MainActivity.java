package com.example.fypfinalsample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {


    ListView lv;
    ArrayAdapter<Item> adapter;
    ArrayList<Item> list;
    private AsyncHttpClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //=================SetupUI/ETC=====================
        client = new AsyncHttpClient();
        lv = (ListView) this.findViewById(R.id.lvItems);
        list = new ArrayList<Item>();
        adapter = new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);
        //=================SetupUI/ETC=====================

        //==================SharedPref==================
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String catID = prefs.getString("catID", "");
        RequestParams params = new RequestParams();
        //params.add("categoryId", catID);
        params.add("categoryId", String.valueOf(1));
        //==================SharedPref==================

        //=================StupidListView=====================
        client.post("http://10.0.2.2/greek_goodies/getProductInfo.php", params, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObj = response.getJSONObject(i);
                        //============-----Identify----=============
                        int id = jsonObj.getInt("id");
                        String name = jsonObj.getString("name");
                        String details = jsonObj.getString("details");
                        String photo = jsonObj.getString("photo");
                        int category = jsonObj.getInt("category");
                        double price = jsonObj.getDouble("price");
                        //============-----Identify----=============
                        Item items = new Item(id, name, details, photo, category, price);
                        list.add(items);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter = new ItemAdapter(getApplicationContext(), R.layout.itemlist, list);
                lv.setAdapter(adapter);
            }
        });
        //=================StupidListView=====================


        //---------------------LVClickHandle------------------------
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long identity) {
                Item target = list.get(position);
                Intent i = new Intent(MainActivity.this,InfoList.class);
                i.putExtra("positionData", target);
                startActivity(i);
            }
        });
        //---------------------LVClickHandle------------------------

    }
}
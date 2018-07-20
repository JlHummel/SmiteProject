package com.project.jesse.finalproject_smiteapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

public class ItemPicker extends AppCompatActivity {

    String url;
    Integer item;
    TextView name;
    JSONArray obj;
    ImageButton icon;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_builder);

        name = (TextView) findViewById(R.id.Test);
        icon = (ImageButton) findViewById(R.id.test);

        item = getIntent().getIntExtra("itemNum", -1);

        String json = null;
        try {
            InputStream is = getResources().openRawResource(R.raw.items);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            obj = new JSONArray(json);
            name.setText(obj.getJSONObject(0).getString("DeviceName"));
            url = obj.getJSONObject(0).getString("itemIcon_URL");
            Log.d("ItemPicker", "Message: " + obj.toString());

        } catch (Exception e) {
            Log.d("ItemPicker", "Error: " + e.toString());
        }

        Thread thread = new Thread(new Runnable(){
            @Override
            public void run(){
                try {
                    InputStream is = (InputStream) new URL(url).getContent();
                    Drawable d = Drawable.createFromStream(is, "src name");
                    icon.setImageDrawable(d);
                    Log.d("ItemPicker", "Message: " + d.toString());
                } catch (Exception e) {
                    Log.d("ItemPicker", "Error: " + e.toString());
                }
            }

        });
        thread.start();
        try{
            thread.join();
        } catch (Exception e){
            Log.e("ItemPicker", "Exception: " + e.getMessage());
        }

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                "ItemDescription": {
//                    "Description": "Physical Protection and Health.",
//                            "Menuitems": [
//                    {
//                        "Description": "Health",
//                            "Value": "+75"
//                    },
//                    {
//                        "Description": "Physical Protection",
//                            "Value": "+10"
//                    }
//      ],
//                    "SecondaryDescription": null
//                }
                try {
                    Intent i = new Intent(ItemPicker.this, ItemBuilder.class);
                    i.putExtra("item", item);
                    i.putExtra("itemPic",obj.getJSONObject(0).getString("itemIcon_URL"));
                    startActivity(i);
                   Log.d("ItemPicker","Message" + obj.getJSONObject(0).getJSONObject("ItemDescription").getJSONArray("Menuitems").getJSONObject(0).getString("Description"));
                }catch (Exception ex)
                {

                }
            }
        });

    }
}

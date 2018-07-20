package com.project.jesse.finalproject_smiteapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.List;

public class Builder extends AppCompatActivity {
    String godname,url;
    List<String> info;
    TextView name;
    JSONArray obj;
    ImageButton icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_builder);

        name = (TextView)findViewById(R.id.Test);
        icon = (ImageButton) findViewById(R.id.test);

        String json = null;
        try {
            InputStream is = getResources().openRawResource(R.raw.gods);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            obj = new JSONArray(json);
            name.setText(obj.getJSONObject(0).getString("Name"));
            url = obj.getJSONObject(0).getString("godIcon_URL");
            Log.d("Builder", "Message: " + obj.getJSONObject(0).getString("godIcon_URL"));

        } catch (Exception e) {
            Log.d("Builder", "Error: " + e.toString());
        }


            Thread thread = new Thread(new Runnable(){
                @Override
                public void run(){
                    try {
                    InputStream is = (InputStream) new URL(url).getContent();
                    Drawable d = Drawable.createFromStream(is, "src name");
                    icon.setImageDrawable(d);
                    Log.d("Builder", "Message: " + d.toString());
                    } catch (Exception e) {
                        Log.d("Builder", "Error: " + e.toString());
                    }
                }

            });
        thread.start();
        try{
            thread.join();
        } catch (Exception e){
            Log.e("Builder", "Exception: " + e.getMessage());
        }

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(Builder.this, ItemBuilder.class));
            }
        });




//            Log.d("Builder", "Message: " + json);
        try {
//            JSONObject obj = new JSONObject(json);
//            JSONArray m_jArry = obj.getJSONArray("Value");
//
//            for (int i = 0; i < m_jArry.length(); i++) {
//                JSONObject jo_inside = m_jArry.getJSONObject(0);
//
//
//                //Add your values in your `ArrayList` as below:
//                info.add(jo_inside.getString("Ability2"));
//                Log.d("Builder", "Message: " + jo_inside.getString("Ability1"));
//            }
        } catch (Exception e) {
Log.d("Builder", "Error: " + e.toString());
    }

    }
}

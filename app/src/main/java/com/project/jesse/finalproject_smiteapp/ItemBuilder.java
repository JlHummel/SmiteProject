package com.project.jesse.finalproject_smiteapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URL;

public class ItemBuilder extends AppCompatActivity{

    String url, currName;
    Integer lvl, item;
    double atkspeed, speed, health, mana, power,physical,magical, atkspeed_pl, speed_pl, health_pl, mana_pl, power_pl, physical_pl, magical_pl;
    TextView name, atkspd,spd, hlt, man, pwr, phys, mag;
    ImageView pic;
    JSONObject obj;
    ImageButton item1,item2;
    Button up, down;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_builder);

        name = (TextView) findViewById(R.id.name);
        atkspd = (TextView) findViewById(R.id.attackspd);
        spd = (TextView) findViewById(R.id.speed);
        hlt = (TextView) findViewById(R.id.health);
        man = (TextView) findViewById(R.id.mana);
        pwr = (TextView) findViewById(R.id.power);
        phys = (TextView) findViewById(R.id.physical);
        mag = (TextView) findViewById(R.id.magical);
        pic = (ImageView) findViewById(R.id.pic);

        item1 = (ImageButton) findViewById(R.id.item_1);
        item2 = (ImageButton) findViewById(R.id.item_2);
        up = (Button) findViewById(R.id.lvlUp);
        down = (Button) findViewById(R.id.lvlDown);

        lvl = 1;
        item = getIntent().getIntExtra("item", -1);
        String json = null;

        try {
            InputStream is = getResources().openRawResource(R.raw.gods);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            obj = new JSONArray(json).getJSONObject(0);

            String buff = obj.getString("Name") + " lvl: " + lvl;
            currName = obj.getString("Name");
            name.setText(buff);

            health_pl = obj.getDouble("HealthPerLevel");
            mana_pl = obj.getDouble("ManaPerLevel");
            power_pl = obj.getDouble("PhysicalPowerPerLevel");
            physical_pl = obj.getDouble("PhysicalProtectionPerLevel");
            magical_pl = obj.getDouble("MagicProtectionPerLevel");
            atkspeed_pl = obj.getDouble("AttackSpeedPerLevel");

            atkspeed = obj.getDouble("AttackSpeed") + atkspeed_pl;
            buff = "Attack Speed: " + obj.getString("AttackSpeed");
            atkspd.setText(buff);

            health = obj.getDouble("Health") + health_pl;
            buff = "Health: " + obj.getString("Health");
            hlt.setText(buff);

            mana = obj.getDouble("Mana") + mana_pl;
            buff = "Mana: " + obj.getString("Mana");
            man.setText(buff);

            power = obj.getDouble("PhysicalPower")+ power_pl;
            buff = "Power: " + obj.getString("PhysicalPower");
            pwr.setText(buff);

            physical = obj.getDouble("PhysicalProtection") + physical_pl;
            buff = "Physical Protections: " + obj.getString("PhysicalProtection");
            phys.setText(buff);

            magical = obj.getDouble("MagicProtection") + magical_pl;
            buff = "Magical Protections: " + obj.getString("MagicProtection");
            mag.setText(buff);

            speed = obj.getDouble("Speed") + speed_pl;
            buff = "Speed: " + obj.getString("Speed");
            spd.setText(buff);


            url = obj.getString("godCard_URL");
            Log.d("Builder", "Message: " + obj.getString("godIcon_URL"));

        } catch (Exception e) {
            Log.d("Builder", "Error: " + e.toString());
        }


        Thread thread = new Thread(new Runnable(){
            @Override
            public void run(){
                try {
                    InputStream is = (InputStream) new URL(url).getContent();
                    Drawable d = Drawable.createFromStream(is, "src name");
                    pic.setImageDrawable(d);
                    is = (InputStream) new URL(getIntent().getStringExtra("itemPic")).getContent();
                    d = Drawable.createFromStream(is,"src name");
                    if(item == 0){
                      item1.setImageDrawable(d);

                    }
                    else{
                        item2.setImageDrawable(d);
                    }
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
        Toast.makeText(ItemBuilder.this, "HEY" + atkspeed, Toast.LENGTH_LONG).show();

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lvl < 20) {
                    lvl++;
                    String buff = currName + " lvl: " + lvl;
                    name.setText(buff);

                    atkspeed += atkspeed_pl;
                    buff = "Attack Speed: " + String.format("%.2f", atkspeed);
                    atkspd.setText(buff);

                    health += health_pl;
                    buff = "Health: " + String.format("%.0f", health);
                    hlt.setText(buff);

                    mana += mana_pl;
                    buff = "Mana: " + String.format("%.0f", mana);
                    man.setText(buff);

                    power += power_pl;
                    buff = "Power: " + String.format("%.0f", power);
                    pwr.setText(buff);

                    physical += physical_pl;
                    buff = "Physical Protections: " + String.format("%.0f", physical);
                    phys.setText(buff);

                    magical += magical_pl;
                    buff = "Magical Protections: " + String.format("%.0f", magical);
                    mag.setText(buff);

                }
            }
        });

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lvl > 1) {
                    lvl--;
                    String buff = currName + " lvl: " + lvl;
                    name.setText(buff);

                    atkspeed -= atkspeed_pl;
                    buff = "Attack Speed: " + String.format("%.2f", atkspeed);
                    atkspd.setText(buff);

                    health -= health_pl;
                    buff = "Health: " + String.format("%.0f", health);
                    hlt.setText(buff);

                    mana -= mana_pl;
                    buff = "Mana: " + String.format("%.0f", mana);
                    man.setText(buff);

                    power -= power_pl;
                    buff = "Power: " + String.format("%.0f", power);
                    pwr.setText(buff);

                    physical -= physical_pl;
                    buff = "Physical Protections: " + String.format("%.0f", physical);
                    phys.setText(buff);

                    magical -= magical_pl;
                    buff = "Magical Protections: " + String.format("%.0f", magical);
                    mag.setText(buff);

                }
            }
        });

        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ItemBuilder.this, ItemPicker.class);
                i.putExtra("itemNum", 0);
                startActivity(i);            }
        });

        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(new Intent(ItemBuilder.this, ItemPicker.class));
                i.putExtra("itemNum", 1);
                startActivity(i);
            }
        });

    }




}

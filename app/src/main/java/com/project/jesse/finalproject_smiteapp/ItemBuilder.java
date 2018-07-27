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

    public static double atkspeed_il, speed_il, health_il, mana_il, physical_il, magical_il, power_il, speedIncrease, atkIncrease;
    public static Integer god;
    public static String[] itempic = new String[6];
    public static Drawable[] itempictures = new Drawable[6];
    String url, currName;
    Integer lvl, item, pickedItem;
    double atkspeed, speed, health, mana, power,physical,magical, atkspeed_pl, speed_pl, health_pl, mana_pl, power_pl, physical_pl, magical_pl;
    TextView name, atkspd,spd, hlt, man, pwr, phys, mag;
    ImageView pic;
    JSONObject obj, itemss;
    ImageButton item1,item2, item3, item4, item5, item6;
    Button up, down, clear, change;
    String json = null;

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
        item3 = (ImageButton) findViewById(R.id.item_3);
        item4 = (ImageButton) findViewById(R.id.item_4);
        item5 = (ImageButton) findViewById(R.id.item_5);
        item6 = (ImageButton) findViewById(R.id.item_6);
        up = (Button) findViewById(R.id.lvlUp);
        down = (Button) findViewById(R.id.lvlDown);
        clear = (Button) findViewById(R.id.clear);
        change = (Button) findViewById(R.id.change);
        lvl = 1;

        item = getIntent().getIntExtra("itemNum", -1);
        pickedItem = getIntent().getIntExtra("pick", 0);
        if(getIntent().getIntExtra("pos", -1) != -1)
        {
            god = getIntent().getIntExtra("pos", -1);
        }


        try {

            InputStream is = getResources().openRawResource(R.raw.gods);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            obj = new JSONArray(json).getJSONObject(god);

             is = getResources().openRawResource(R.raw.items);
            size = is.available();
            byte[] buffr = new byte[size];
            is.read(buffr);
            is.close();
            json = new String(buffer, "UTF-8");
            itemss = new JSONArray(json).getJSONObject(pickedItem);

            String buff = obj.getString("Name") + " lvl: " + lvl;
            currName = obj.getString("Name");
            name.setText(buff);

            health_pl = obj.getDouble("HealthPerLevel");
            mana_pl = obj.getDouble("ManaPerLevel");
            if(obj.getDouble("PhysicalPowerPerLevel") != 0) {
                power_pl = obj.getDouble("PhysicalPowerPerLevel");
            }else
            {
                power_pl = obj.getDouble("MagicalPowerPerLevel") * 0.2;
            }
            physical_pl = obj.getDouble("PhysicalProtectionPerLevel");
            magical_pl = obj.getDouble("MagicProtectionPerLevel");
            atkspeed_pl = obj.getDouble("AttackSpeedPerLevel");

            atkspeed = obj.getDouble("AttackSpeed") + atkspeed_pl;
            atkIncrease = atkspeed * atkspeed_il;
            atkspeed += atkIncrease;
            buff = "Attack Speed: " +  String.format("%.2f", atkspeed);
            atkspd.setText(buff);

            health = obj.getDouble("Health") + health_pl + health_il;
            buff = "Health: " +  String.format("%.0f", health);
            hlt.setText(buff);

            mana = obj.getDouble("Mana") + mana_pl + mana_il;
            buff = "Mana: " +  String.format("%.0f", mana);
            man.setText(buff);

            if(obj.getDouble("PhysicalPower") != 0) {
                power = obj.getDouble("PhysicalPower") + power_pl + power_il;
                buff = "Power: " +  String.format("%.0f", power);
                pwr.setText(buff);
            }else if(obj.getDouble("MagicalPower") != 0) {
                power = (obj.getDouble("MagicalPower") * 0.2) + power_pl + power_il;
                Log.d("ItemBuilder", "Message: " + power +" : "+ power_pl);
                buff = "Power: " +  String.format("%.0f", power);
                pwr.setText(buff);
            }

            physical = obj.getDouble("PhysicalProtection") + physical_pl + physical_il;
            buff = "Physical Protections: " +  String.format("%.0f", physical);
            phys.setText(buff);

            magical = obj.getDouble("MagicProtection") + magical_pl + magical_il;
            buff = "Magical Protections: " +  String.format("%.0f", magical);
            mag.setText(buff);

            speed = obj.getDouble("Speed") + speed_pl;
            speedIncrease = speed *speed_il;
            speed += speedIncrease;
            buff = "Speed: " +  String.format("%.0f", speed);
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

                    if(item != -1){
                        is = (InputStream) new URL(itempic[item]).getContent();
                        itempictures[item] = Drawable.createFromStream(is, "src name");
                        if(itempictures[0] != null){
                            item1.setImageDrawable(itempictures[0]);
                        }
                        if (itempictures[1] != null){
                            item2.setImageDrawable(itempictures[1]);
                        }
                        if(itempictures[2] != null){
                             item3.setImageDrawable(itempictures[2]);
                        }
                        if(itempictures[3] != null){
                            item4.setImageDrawable(itempictures[3]);
                        }
                        if (itempictures[4] != null){
                            item5.setImageDrawable(itempictures[4]);
                        }
                        if(itempictures[5] != null){
                            item6.setImageDrawable(itempictures[5]);
                        }
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
                startActivity(i);
            }
        });

        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(new Intent(ItemBuilder.this, ItemPicker.class));
                i.putExtra("itemNum", 1);
                startActivity(i);
            }
        });

        item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(new Intent(ItemBuilder.this, ItemPicker.class));
                i.putExtra("itemNum", 2);
                startActivity(i);
            }
        });

        item4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(new Intent(ItemBuilder.this, ItemPicker.class));
                i.putExtra("itemNum", 3);
                startActivity(i);
            }
        });

        item5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(new Intent(ItemBuilder.this, ItemPicker.class));
                i.putExtra("itemNum", 4);
                startActivity(i);
            }
        });

        item6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(new Intent(ItemBuilder.this, ItemPicker.class));
                i.putExtra("itemNum", 5);
                startActivity(i);
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ItemBuilder.this, Builder.class);
                startActivity(i);
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                itempictures = new Drawable[6];
                item1.setImageDrawable(null);
                item2.setImageDrawable(null);
                item3.setImageDrawable(null);
                item4.setImageDrawable(null);
                item5.setImageDrawable(null);
                item6.setImageDrawable(null);


                try {
                    String buff;
                    atkspeed -=atkIncrease;
                    buff = "Attack Speed: " + String.format("%.2f", atkspeed);
                    atkspd.setText(buff);

                    health -=  health_il;
                    buff = "Health: " + String.format("%.0f",health);
                    hlt.setText(buff);

                    mana -= mana_il;
                    buff = "Mana: " + String.format("%.0f",mana) ;
                    man.setText(buff);

                    power -= power_il;
                    buff = "Power: " + String.format("%.0f",power) ;
                    pwr.setText(buff);

                    physical -= physical_il;
                    buff = "Physical Protections: " + String.format("%.0f",physical) ;
                    phys.setText(buff);

                    magical -= magical_il;
                    buff = "Magical Protections: " +String.format("%.0f",magical) ;
                    mag.setText(buff);

                    speed -= speedIncrease;
                    buff = "Speed: " + String.format("%.0f",speed);
                    spd.setText(buff);

                    atkspeed_il = 0;
                    speed_il = 0;
                    health_il = 0;
                    mana_il = 0;
                    power_il = 0;
                    magical_il = 0;
                    physical_il = 0;



                }catch (Exception e)
                {

                }

            }
        });

    }




}

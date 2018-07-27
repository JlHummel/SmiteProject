package com.project.jesse.finalproject_smiteapp;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

class RecyclerItemHolder extends RecyclerView.ViewHolder {

    TextView name, description;
    RelativeLayout parentLayout;
    ImageView icon;

    public RecyclerItemHolder(View itemview) {
        super(itemview);
        parentLayout = (RelativeLayout) itemview.findViewById(R.id.parent_items);
        name = (TextView) itemview.findViewById(R.id.item_name);
        description = (TextView) itemview.findViewById(R.id.item_description);
        icon = (ImageView) itemview.findViewById(R.id.item_icon);
    }
}

public class Rec_Items extends RecyclerView.Adapter<RecyclerItemHolder>{
private JSONArray mDataset;
private Context mContext;
private Integer itemNum;
private double iSpeed, iPhysical, iMagical, iAtkspeed, iHealth, iMana, iPower;
private JSONArray itemObj;
private String percentage;

public Rec_Items(JSONArray myDataset,Context context, Integer itemnum){
        mDataset=myDataset;
        mContext=context;
        itemNum = itemnum;
        }

// Create new views (invoked by the layout manager)
@Override
public RecyclerItemHolder onCreateViewHolder(ViewGroup parent,
        int viewType){
        // create a new view
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.items_rec,parent,false);
        return new RecyclerItemHolder(v);
        }

// Replace the contents of a view
@Override
public void onBindViewHolder(final RecyclerItemHolder holder,final int position){

        try{
        holder.name.setText(mDataset.getJSONObject(holder.getAdapterPosition()).getString("DeviceName"));
        Thread thread=new Thread(new Runnable(){
@Override
public void run(){
        try{
        InputStream is=(InputStream)new URL(mDataset.getJSONObject(holder.getAdapterPosition()).getString("itemIcon_URL")).getContent();
        Drawable d=Drawable.createFromStream(is,"src name");
        holder.icon.setImageDrawable(d);
        Log.d("Builder","Message: "+d.toString());
        }catch(Exception e){
        Log.d("Builder","Error: "+e.toString());
        }
        }

        });
        thread.start();
        try{
        thread.join();
        }catch(Exception e){
        Log.e("Rec_Lists","Exception: "+e.getMessage());
        }
        }catch(Exception e)
        {

        }

        holder.parentLayout.setOnClickListener(new View.OnClickListener(){
@Override
public void onClick(View view) {

    try {

        itemObj = mDataset.getJSONObject(holder.getAdapterPosition()).getJSONObject("ItemDescription").getJSONArray("Menuitems");
        Log.d("ItemBuilder", itemObj.getString(0));

        for(int i = 0; i < 2; i ++) {
            if (itemObj.getJSONObject(i)!= JSONObject.NULL) {
                if (itemObj.getJSONObject(i).getString("Description").equals("Health")) {
                    iHealth = ItemBuilder.health_il + itemObj.getJSONObject(i).getDouble("Value");
                }
                else if (itemObj.getJSONObject(i).getString("Description").equals("Mana")) {
                    iMana = ItemBuilder.mana_il + itemObj.getJSONObject(i).getDouble("Value");
                }
                else if (itemObj.getJSONObject(i).getString("Description").equals("Physical Protection")) {
                    iPhysical = ItemBuilder.physical_il + itemObj.getJSONObject(i).getDouble("Value");
                }
                else if (itemObj.getJSONObject(i).getString("Description").equals("Magical Protection")) {
                    iMagical = ItemBuilder.magical_il + itemObj.getJSONObject(i).getDouble("Value");
                }
                else if (itemObj.getJSONObject(i).getString("Description").equals("Attack Speed")) {
                    percentage = itemObj.getJSONObject(i).getString("Value");
                    if(percentage.equals("+15%"))
                    {
                        iAtkspeed = ItemBuilder.atkspeed_il + .15;
                    }else if (percentage.equals("+20%"))
                    {
                        iAtkspeed = ItemBuilder.atkspeed_il + .20;
                    }else if (percentage.equals("+25%"))
                    {
                        iAtkspeed = ItemBuilder.atkspeed_il + .25;
                    }else if (percentage.equals("+30%"))
                    {
                        iAtkspeed = ItemBuilder.atkspeed_il + .3;
                    }
                }
                else if (itemObj.getJSONObject(i).getString("Description").equals("Physical Power") ||
                        itemObj.getJSONObject(i).getString("Description").equals("Magical Power")) {
                    iPower = ItemBuilder.power_il + itemObj.getJSONObject(i).getDouble("Value");
                }
                else if (itemObj.getJSONObject(i).getString("Description").equals("Movement Speed")) {
                    iSpeed = ItemBuilder.speed_il + itemObj.getJSONObject(i).getDouble("Value");
                }

            }
        }


        Intent intent = new Intent(mContext, ItemBuilder.class);
        intent.putExtra("pick", holder.getAdapterPosition());
        ItemBuilder.itempic[itemNum] = mDataset.getJSONObject(holder.getAdapterPosition()).getString("itemIcon_URL");

        ItemBuilder.speed_il = iSpeed;
        ItemBuilder.magical_il = iMagical;
        ItemBuilder.physical_il = iPhysical;
        ItemBuilder.atkspeed_il = iAtkspeed;
        ItemBuilder.health_il = iHealth;
        ItemBuilder.mana_il = iMana;
        ItemBuilder.power_il = iPower;

        intent.putExtra("itemNum", itemNum);
        mContext.startActivity(intent);
    }
catch(Exception exception){
Log.d("ItemBuilder", "Error: " + exception);
            }}
        });
        }

// Return the size of your dataset
@Override
public int getItemCount(){
        return mDataset.length();
        }
}

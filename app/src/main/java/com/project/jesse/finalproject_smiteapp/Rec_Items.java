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
        Intent intent = new Intent(mContext, ItemBuilder.class);
        intent.putExtra("pick", holder.getAdapterPosition());
        ItemBuilder.itempic[itemNum] = mDataset.getJSONObject(holder.getAdapterPosition()).getString("itemIcon_URL");
       // intent.putExtra("itemPic", mDataset.getJSONObject(holder.getAdapterPosition()).getString("itemIcon_URL"));
        intent.putExtra("itemNum", itemNum);
        mContext.startActivity(intent);
    }
catch(Exception exception){

            }}
        });
        }

// Return the size of your dataset
@Override
public int getItemCount(){
        return mDataset.length();
        }
}

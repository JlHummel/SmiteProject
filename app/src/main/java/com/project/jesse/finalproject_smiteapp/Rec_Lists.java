package com.project.jesse.finalproject_smiteapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URL;

class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView name, description;
        RelativeLayout parentLayout;
        ImageView icon;

        public RecyclerViewHolder(View itemview) {
            super(itemview);
            parentLayout = (RelativeLayout) itemview.findViewById(R.id.parent_Layout);
            name = (TextView) itemview.findViewById(R.id.godName);
            icon = (ImageView) itemview.findViewById(R.id.godImage);
        }
    }
public class Rec_Lists extends RecyclerView.Adapter<RecyclerViewHolder> {
    private JSONArray mDataset;
    private Context mContext;

    public Rec_Lists(JSONArray myDataset, Context context) {
        mDataset = myDataset;
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.builder_rec,parent,false);
        return new RecyclerViewHolder(v);
    }

    // Replace the contents of a view
    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {

        try {
            holder.name.setText(mDataset.getJSONObject(holder.getAdapterPosition()).getString("Name"));
            Thread thread = new Thread(new Runnable(){
                @Override
                public void run(){
                    try {
                        InputStream is = (InputStream) new URL(mDataset.getJSONObject(holder.getAdapterPosition()).getString("godIcon_URL")).getContent();
                        Drawable d = Drawable.createFromStream(is, "src name");
                        holder.icon.setImageDrawable(d);
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
                Log.e("Rec_Lists", "Exception: " + e.getMessage());
            }


        }catch (Exception e)
        {

        }

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,ItemBuilder.class);
                intent.putExtra("pos",holder.getAdapterPosition());
                mContext.startActivity(intent);
            }
        });
    }

    // Return the size of your dataset
    @Override
    public int getItemCount() {
        return mDataset.length();
    }
}

package com.project.jesse.finalproject_smiteapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.w3c.dom.Text;

public class Rec_Lists extends RecyclerView.Adapter<Rec_Lists.ViewHolder> {
    private JSONArray mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, description;
        ImageView icon;

        public ViewHolder(TextView n,TextView d, ImageView i) {
            super(n);
            name = n;
            description = d;
            icon = i;
        }
    }

    public Rec_Lists(JSONArray myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public Rec_Lists.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        TextView n = (TextView) inflater.inflate(R.layout.builder_rec, parent, false);
        TextView d = (TextView) inflater.inflate(R.layout.builder_rec,parent,false);
        ImageView i = (ImageView) inflater.inflate(R.layout.builder_rec,parent,false);
        ViewHolder vh = new ViewHolder(n,d,i);
        return vh;
    }

    // Replace the contents of a view
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

    }

    // Return the size of your dataset
    @Override
    public int getItemCount() {
        return mDataset.length();
    }
}

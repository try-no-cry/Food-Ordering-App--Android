package com.example.newbiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SlideAdapter extends RecyclerView.Adapter<SlideAdapter.ViewHolder> {
ArrayList<Slide_recycler> list;
Context context;

    public SlideAdapter(ArrayList<Slide_recycler> list, Context context) {
        this.list = list;
        this.context = context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivSlideRec;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivSlideRec=itemView.findViewById(R.id.ivSlideRec);

        }
    }

    @NonNull
    @Override
    public SlideAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.slide_recycler,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SlideAdapter.ViewHolder holder, int position) {
        String imageUrl=list.get(position).getSlideImageUrl();

        Glide.with(context).load(imageUrl).into(holder.ivSlideRec);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}

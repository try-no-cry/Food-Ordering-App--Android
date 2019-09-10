package com.example.newbiz.IntroSlider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newbiz.R;

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

        position=position%list.size();
        //some extra info has been given for future use,if any,maybe to
        final String slideImageID=list.get(position).getSlideImageID();
        String imageUrl=list.get(position).getSlideImageUrl();
        String food_id=list.get(position).getFoodID();

        Glide.with(context).load(imageUrl).centerCrop().into(holder.ivSlideRec);
//holder.ivSlideRec.setImageResource(R.drawable.sunset);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,slideImageID,Toast.LENGTH_SHORT).show();
                //we have food id here and thus we can get any info about any food
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
//        return Integer.MAX_VALUE;
    }


}

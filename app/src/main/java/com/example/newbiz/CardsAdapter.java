package com.example.newbiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder> {
private  ArrayList<Single_Card> list;
private Context context;

    public CardsAdapter(ArrayList<Single_Card> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivFoodImage;
        TextView tvFoodName,tvPrice,tvAreaName,tvAnyOtherInfo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivFoodImage=itemView.findViewById(R.id.ivFoodImage);
            tvFoodName=itemView.findViewById(R.id.tvFoodName);
            tvPrice=itemView.findViewById(R.id.tvPrice);
            tvAreaName=itemView.findViewById(R.id.tvAreaName);
            tvAnyOtherInfo=itemView.findViewById(R.id.tvAnyOtherInfo);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.single_card,parent,false);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String imageUrl=list.get(position).getImageUrl();
        String foodName=list.get(position).getFoodName();
        String Price=list.get(position).getFoodPrice();
        String place=list.get(position).getAddress();
        String anyInfo=list.get(position).getAnyOtherInfo();

        //set food image by glide
        Glide.with(context).load(imageUrl).into(holder.ivFoodImage);

        holder.tvFoodName.setText(foodName);
        holder.tvPrice.setText("₹"+Price);
        holder.tvAreaName.setText(place);
        holder.tvAnyOtherInfo.setText(anyInfo);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}

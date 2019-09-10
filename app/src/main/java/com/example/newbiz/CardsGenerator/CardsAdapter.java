package com.example.newbiz.CardsGenerator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newbiz.OrderPage;
import com.example.newbiz.R;

import java.util.ArrayList;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder> {
private  ArrayList<Single_Card> list;
private Context context;

//ItemSelected varItemClicked;
//
//public interface ItemSelected{
//    void onItemClick(int index);
//}

    public CardsAdapter(ArrayList<Single_Card> list, Context context) {
        this.list = list;
        this.context = context;
//        varItemClicked=(ItemSelected)context;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final String foodcard_id=list.get(position).getFoodCard_id();
        final String food_id=list.get(position).getFood_id();
        final String imageUrl = list.get(position).getImageUrl();
        final String foodName = list.get(position).getFoodName();
        final String Price = list.get(position).getFoodPrice();
        final String place = list.get(position).getAddress();
        final String anyInfo = list.get(position).getAnyOtherInfo();

        //set food image by glide
        Glide.with(context).load(imageUrl).into(holder.ivFoodImage);
//        holder.ivFoodImage.setImageResource(R.drawable.beach);

        holder.tvFoodName.setText(foodName);
        holder.tvPrice.setText("₹" + Price);
        holder.tvAreaName.setText(place);
        holder.tvAnyOtherInfo.setText(anyInfo);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context,foodName,Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context, OrderPage.class);

                Single_Card single_card=new Single_Card();
                single_card.setFoodCard_id(foodcard_id);
                single_card.setFood_id(food_id);
                single_card.setImageUrl(imageUrl);
                single_card.setFoodName(foodName);
                single_card.setFoodPrice(Price);
                single_card.setAddress(place);
                single_card.setAnyOtherInfo(anyInfo);


                Bundle bundle=new Bundle();
                bundle.putSerializable("single_card",single_card);
                intent.putExtras(bundle);
                context.startActivity(intent);


            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }


}

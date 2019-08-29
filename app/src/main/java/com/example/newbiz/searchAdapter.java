package com.example.newbiz;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.zip.Inflater;

import static androidx.core.content.ContextCompat.checkSelfPermission;

public class searchAdapter extends RecyclerView.Adapter<searchAdapter.ViewHolder> {

    private ArrayList<singleSearchInfo> list;
    private Context context;

    public searchAdapter(ArrayList<singleSearchInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tvLocation_S, tvFoodName_S;
        Button btnGoToLocation_S, btnCallToLocation_S;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv = itemView.findViewById(R.id.ivSearchImage_S);
            tvLocation_S = itemView.findViewById(R.id.tvLocation_S);
            tvFoodName_S = itemView.findViewById(R.id.tvFoodName_S);
            btnGoToLocation_S = itemView.findViewById(R.id.btnGoToLocation_S);
            btnCallToLocation_S = itemView.findViewById(R.id.btncallToLocation_S);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_search_result, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        String imageUrl = list.get(i).getImageUrl();
        final String location = list.get(i).getLocation();
        String foodName = list.get(i).getFoodName();
        final String contact = list.get(i).getCallToLocation_contact();  //will get the contact no. which we need to call after clicking it

        //set food image by glide
        Glide.with(context).load(imageUrl).into(holder.iv);
        holder.tvLocation_S.setText(location);
        holder.tvFoodName_S.setText(foodName);

        holder.btnGoToLocation_S.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String map = "http://maps.google.co.in/maps?q=" + location;
                // where str_location is the address string

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
                context.startActivity(intent);
            }
        });


        holder.btnCallToLocation_S.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + contact));//change the number


                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, 1);
                }
                else
                {
                    context.startActivity(callIntent);
                }
            }
        });


    }


    @Override
    public int getItemCount() {
        return list.size();
    }


}

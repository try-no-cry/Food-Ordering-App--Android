package com.example.newbiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class searchAdapter extends RecyclerView.Adapter<searchAdapter.ViewHolder> {

private ArrayList<singleSearchInfo> list;
private Context context;

    public searchAdapter(ArrayList<singleSearchInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tvLocation_S,tvFoodName_S;
        Button btnGoToLocation_S,btnCallToLocation_S;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv=itemView.findViewById(R.id.ivSearchImage_S);
            tvLocation_S=itemView.findViewById(R.id.tvLocation_S);
            tvFoodName_S=itemView.findViewById(R.id.tvFoodName_S);
            btnGoToLocation_S=itemView.findViewById(R.id.btnGoToLocation_S);
            btnCallToLocation_S=itemView.findViewById(R.id.btncallToLocation_S);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.single_search_result,null,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        String imageUrl=list.get(i).getImageUrl();
        String location=list.get(i).getLocation();
        String foodName=list.get(i).getFoodName();
        String  contact=list.get(i).getCallToLocation_contact();  //will get the contact no. which we need to call after clicking it
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}

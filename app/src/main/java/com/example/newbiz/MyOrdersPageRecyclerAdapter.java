package com.example.newbiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MyOrdersPageRecyclerAdapter extends RecyclerView.Adapter<MyOrdersPageRecyclerAdapter.ViewHolder> {
private ArrayList<MyOrders_SingleOrder> list;
private Context context;

    public MyOrdersPageRecyclerAdapter(ArrayList<MyOrders_SingleOrder> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView  ivFoodImage_MO;
        TextView tvOrderName_MO,tvOrderRefNo_MO,tvOrderDate_MO;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivFoodImage_MO=itemView.findViewById(R.id.ivFoodImage_MO);
            tvOrderName_MO=itemView.findViewById(R.id.tvOrderName_MO);
            tvOrderRefNo_MO=itemView.findViewById(R.id.tvOrderRefNo_MO);
            tvOrderDate_MO=itemView.findViewById(R.id.tvOrderDate_MO);
        }
    }

    @NonNull
    @Override
    public MyOrdersPageRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.single_my_orders_frag,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrdersPageRecyclerAdapter.ViewHolder holder, int position) {

        String imageUrl=list.get(position).getImageUrl();
        String orderName=list.get(position).getFoodName();
        String orderRefNo=list.get(position).getOrderRefNo();
        String orderDate=list.get(position).getOrderDate();
        String status=list.get(position).getOrderStatus();

        //set all data with holder
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}

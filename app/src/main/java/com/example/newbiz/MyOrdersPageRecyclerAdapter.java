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
        TextView tvOrderName_MO,tvOrderRefNo_MO,tvOrderDate_MO,tvOrderStatus_MO;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivFoodImage_MO=itemView.findViewById(R.id.ivFoodImage_MO);
            tvOrderName_MO=itemView.findViewById(R.id.tvOrderName_MO);
            tvOrderRefNo_MO=itemView.findViewById(R.id.tvOrderRefNo_MO);
            tvOrderDate_MO=itemView.findViewById(R.id.tvOrderDate_MO);
            tvOrderStatus_MO=itemView.findViewById(R.id.tvOrderStatus_MO);
        }
    }

    @NonNull
    @Override
    public MyOrdersPageRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.single_my_orders_frag,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrdersPageRecyclerAdapter.ViewHolder h, int position) {

        String imageUrl=list.get(position).getImageUrl();
        String orderName=list.get(position).getFoodName();
        String orderRefNo=list.get(position).getOrderRefNo();
        String orderDate=list.get(position).getOrderDate();
        String status=list.get(position).getOrderStatus();

        //set all data with holder
        Glide.with(context).load(imageUrl).into(h.ivFoodImage_MO);
        h.tvOrderName_MO.setText(orderName);
        h.tvOrderRefNo_MO.setText(orderRefNo);
        h.tvOrderDate_MO.setText(orderDate);
        h.tvOrderStatus_MO.setText(status);

        //on click there will be a dialog box to show every detail of the order



    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}

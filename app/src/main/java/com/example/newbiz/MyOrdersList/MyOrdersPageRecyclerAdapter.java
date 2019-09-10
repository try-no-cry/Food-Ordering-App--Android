package com.example.newbiz.MyOrdersList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newbiz.R;
import com.example.newbiz.order_detail_on_click;

import java.util.ArrayList;

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
    public void onBindViewHolder(@NonNull final MyOrdersPageRecyclerAdapter.ViewHolder h, final int position) {

        String imageUrl=list.get(position).getImageUrl();
        String orderName=list.get(position).getFoodName();
        String orderRefNo=list.get(position).getOrder_id();
        String orderDate=list.get(position).getOrderDate();
        String status=list.get(position).getOrderStatus();


        //set all data with holder
        Glide.with(context).load(imageUrl).into(h.ivFoodImage_MO);
//        h.ivFoodImage_MO.setImageResource(R.drawable.lotus); //temporary
        h.tvOrderName_MO.setText(orderName);
        h.tvOrderRefNo_MO.setText(orderRefNo);
        h.tvOrderDate_MO.setText(orderDate);
        h.tvOrderStatus_MO.setText(status);
        if(status.equals("Cancelled"))
            h.tvOrderStatus_MO.setTextColor(Color.RED);
        else h.tvOrderStatus_MO.setTextColor(Color.GREEN);



        //on click there will be a dialog box to show every detail of the order
        h.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOrderDetailDialog(h.itemView,position);
            }
        });



    }

    private void showOrderDetailDialog(View itemView, int position) {
        //passing order id so that it gets all info from there
        String imageUrl=list.get(position).getImageUrl();
        String orderName=list.get(position).getFoodName();
        String order_id=list.get(position).getOrder_id();
        String orderDate=list.get(position).getOrderDate();
        String status=list.get(position).getOrderStatus();


        Intent intent=new Intent(context, order_detail_on_click.class);
        intent.putExtra("order_id",order_id);
        intent.putExtra("imageUrl",imageUrl);

        intent.putExtra("foodName",orderName);
        intent.putExtra("orderDate",orderDate);
        intent.putExtra("status",status);
        //we can get address,foodPrice there from query which will fetch it
        context.startActivity(intent);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}

package com.example.myapplication;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.ViewHolder> {

    private Product[] products;

    public MyCustomAdapter(Product[] products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_row, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product myProd = products[position];

        holder.nameTV.setText(myProd.name);
        holder.priceTV.setText(myProd.price + "");
        holder.quantityTV.setText(myProd.quantity + "");

        if (myProd.bought) holder.rowLL.setBackgroundColor(Color.GREEN);
        else holder.rowLL.setBackgroundColor(Color.RED);

        holder.rowLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myProd.switchStatus();

                if (myProd.bought) holder.rowLL.setBackgroundColor(Color.GREEN);
                else holder.rowLL.setBackgroundColor(Color.RED);
            }
        });

    }

    @Override
    public int getItemCount() {
        return products.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTV;
        public TextView quantityTV;
        public TextView priceTV;
        public LinearLayout rowLL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTV = itemView.findViewById(R.id.nameTextView);
            quantityTV = itemView.findViewById(R.id.quantityTextView);
            priceTV = itemView.findViewById(R.id.priceTextView);
            rowLL = itemView.findViewById(R.id.rowLinearLayout);
        }
    }
}

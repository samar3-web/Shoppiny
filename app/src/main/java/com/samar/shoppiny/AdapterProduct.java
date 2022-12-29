package com.samar.shoppiny;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.HolderProduct> implements Filterable {

    private Context context;
    public ArrayList<ModelProduct> productList, filterList ;
    private FilterProduct filter;

    public AdapterProduct(Context context, ArrayList<ModelProduct> productList) {
        this.context = context;
        this.productList = productList;
        this.filterList = productList;
    }

    @NonNull
    @Override
    public HolderProduct onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_product,parent, false);
        return new HolderProduct(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderProduct holder, int position) {
        //get data
        ModelProduct modelProduct = productList.get(position);
        String id = modelProduct.getProductId();
        String uid = modelProduct.getUid();
        String productCategory = modelProduct.getProductCategory();
        String productDescription  = modelProduct.getProductDescription();
        String icon = modelProduct.getProductIcon();
        String title = modelProduct.getProductTitle();
        String timestamp = modelProduct.getTimestamp();
        String price = modelProduct.getProductPrice();
        //set data
        holder.title.setText(title);
        //holder.price.setText(price);
        holder.title.setText(title);
        holder.category.setText(productCategory);
        holder.description.setText(productDescription);
        try {
            Picasso.get().load(icon).placeholder(R.drawable.ic_baseline_shopping_cart_24).into(holder.productIconIv);

        }
        catch (Exception e){
            holder.productIconIv.setImageResource(R.drawable.ic_baseline_shopping_cart_24);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new FilterProduct(this, filterList);
        }
        return filter;
    }

    class HolderProduct extends RecyclerView.ViewHolder {

         private ImageView productIconIv;
         private TextView price, title, description, category;

         public HolderProduct(@NonNull View itemView) {
             super(itemView);
             productIconIv = itemView.findViewById(R.id.productIconIv);
             price = itemView.findViewById(R.id.price);
             title = itemView.findViewById(R.id.titleTv);
             description = itemView.findViewById(R.id.descriptionTv);
             category = itemView.findViewById(R.id.categoryTv);
         }
     }
}

package com.samar.shoppiny;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
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
        /*
        String id = modelProduct.getProductId();
        String uid = modelProduct.getUid();
        String productCategory = modelProduct.getProductCategory();
        String productDescription  = modelProduct.getProductDescription();
        String title = modelProduct.getProductTitle();
        String timestamp = modelProduct.getTimestamp();
        String price = modelProduct.getProductPrice();*/
        //set data
        //holder.title.setText(title);
        //holder.price.setText(price);
        //holder.title.setText(title);
        //holder.category.setText(productCategory);
        //holder.description.setText(productDescription);
        ModelProduct modelProduct = productList.get(position);
        String id = modelProduct.getProductId();
        String uid = modelProduct.getUid();
        String title = modelProduct.getProductTitle();
        String timestamp = modelProduct.getTimestamp();
        String price = modelProduct.getProductPrice();
        String icon = modelProduct.getProductIcon();
        holder.title.setText(title);
        holder.price.setText(price+"TND");
        try {
            Picasso.get().load(icon).placeholder(R.drawable.ic_baseline_shopping_cart_24).into(holder.productIconIv);

        }
        catch (Exception e){
            holder.productIconIv.setImageResource(R.drawable.ic_baseline_shopping_cart_24);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailsBottomSheet(modelProduct);
            }
        });
    }

    private void detailsBottomSheet(ModelProduct modelProduct) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.activity_mes_annonce_details,null);
        bottomSheetDialog.setContentView(view);



        ImageButton productIconIv = view.findViewById(R.id.productIconIv);
        TextView titleTv = view.findViewById(R.id.titleTv);
        TextView descriptionTv = view.findViewById(R.id.descriptionTv);
        TextView categoryTv = view.findViewById(R.id.categoryTv);
        TextView priceTv = view.findViewById(R.id.priceTv);

        String id = modelProduct.getProductId();
        String uid = modelProduct.getUid();
        String productCategory = modelProduct.getProductCategory();
        String productDescription  = modelProduct.getProductDescription();
        String title = modelProduct.getProductTitle();
        String timestamp = modelProduct.getTimestamp();
        String priceProduct = modelProduct.getProductPrice();
        String icon = modelProduct.getProductIcon();

        //set data
        titleTv.setText(title);
        descriptionTv.setText(productDescription);
        categoryTv.setText(productCategory);
        priceTv.setText(priceProduct + "TND");

        try {
            Picasso.get().load(icon).placeholder(R.drawable.ic_baseline_shopping_cart_24).into(productIconIv);

        }
        catch (Exception e){
            productIconIv.setImageResource(R.drawable.ic_baseline_shopping_cart_24);
        }
        bottomSheetDialog.show();

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
             price = itemView.findViewById(R.id.priceTv);
             title = itemView.findViewById(R.id.titleTv);
             //description = itemView.findViewById(R.id.descriptionTv);
             //category = itemView.findViewById(R.id.categoryTv);
         }
     }
}

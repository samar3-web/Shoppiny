package com.samar.shoppiny;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AsiaFoodAdapter extends RecyclerView.Adapter<AsiaFoodAdapter.AsiaFoodViewHolder> {
    private static final int REQUEST_CALL = 1;

    Context context;
    List<AsiaFood> asiaFoodList;



    public AsiaFoodAdapter(Context context, List<AsiaFood> asiaFoodList) {
        this.context = context;
        this.asiaFoodList = asiaFoodList;
    }

    @NonNull
    @Override
    public AsiaFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_other_products, parent, false);
        return new AsiaFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder( AsiaFoodViewHolder holder, int position) {
        AsiaFood modelProduct = asiaFoodList.get(position);
        String title = modelProduct.getName();
        String price = modelProduct.getPrice();
        Integer icon = modelProduct.getImageUrl();



        holder.foodImage.setImageResource(asiaFoodList.get(position).getImageUrl());
        holder.name.setText(asiaFoodList.get(position).getName());
        holder.price.setText(asiaFoodList.get(position).getPrice());
        holder.rating.setText(asiaFoodList.get(position).getRating());
        holder.restorantName.setText(asiaFoodList.get(position).getRestorantname());
        
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    detailsBottomSheet(modelProduct);
            }
        });

    }

    private void detailsBottomSheet(AsiaFood modelProduct) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.activity_details_product,null);
        bottomSheetDialog.setContentView(view);

        ImageView productIconIv = view.findViewById(R.id.productIconIv);
        TextView titleTv = view.findViewById(R.id.titleTv);
        TextView descriptionTv = view.findViewById(R.id.descriptionTv);
        TextView categoryTv = view.findViewById(R.id.categoryTv);
        TextView priceTv = view.findViewById(R.id.priceTv);
        TextView textView3 = view.findViewById(R.id.textView3);
        TextView phoneNumberTv = view.findViewById(R.id.phoneNumberTV);
        ImageView imageView11 = view.findViewById(R.id.imageView11);

        String productCategory = modelProduct.getProductCategory();
        String productDescription  = modelProduct.getProductDescription();
        String title = modelProduct.getName();
        String priceProduct = modelProduct.getPrice();
        Integer  icon = modelProduct.getImageUrl();
        String productRating = modelProduct.getRating();
        String productNumber = modelProduct.getProductNumber();


        //set data
        titleTv.setText(title);
        phoneNumberTv.setText(productNumber);
        descriptionTv.setText(productDescription);
        categoryTv.setText(productCategory);
        priceTv.setText(priceProduct);
        productIconIv.setImageResource(icon);
        textView3.setText(productRating);
        imageView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String tel = modelProduct.getProductNumber();
                /*if (tel.trim().length() > 0) {

                    if (ContextCompat.checkSelfPermission(context,
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                    } else {

                    }

                } else {
                    Toast.makeText(context, "Enter Phone Number", Toast.LENGTH_SHORT).show();
                }*/
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+tel));
                context.startActivity(intent);
            }
        });


        bottomSheetDialog.show();

    }





    @Override
    public int getItemCount() {
        return asiaFoodList.size();
    }

 //
    private void makePhoneCall() {
    }

    public static final class AsiaFoodViewHolder extends RecyclerView.ViewHolder{


        ImageView foodImage;
        TextView price, name, rating, restorantName;

        public AsiaFoodViewHolder(@NonNull View itemView) {
            super(itemView);

            foodImage = itemView.findViewById(R.id.food_image);
            price = itemView.findViewById(R.id.price);
            name = itemView.findViewById(R.id.name);
            rating = itemView.findViewById(R.id.rating);
            restorantName = itemView.findViewById(R.id.restorant_name);



        }
    }

}
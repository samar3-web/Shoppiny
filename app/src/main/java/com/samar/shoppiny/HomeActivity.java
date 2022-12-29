package com.samar.shoppiny;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private ImageView addAnnonce;
    private TextView nameTv,tabProductsTv;
    private EditText searchProductEt;
    private ImageButton logoutBtn;
    private FirebaseAuth firebaseAuth;
    private RelativeLayout productsRl;
    private ArrayList<ModelProduct> productList;
    private RecyclerView productsRv;
    private AdapterProduct adapterProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addAnnonce = findViewById(R.id.fab);
        nameTv = findViewById(R.id.nameTv);
        productsRl = findViewById(R.id.productsRl);
        logoutBtn = findViewById(R.id.logoutBtn);
        productsRv = findViewById(R.id.productsRv);
        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();
        loadAllProducts();
        showProductsUI();
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                checkUser();
            }
        });
        addAnnonce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(HomeActivity.this,addAnnonceActivity.class));
            }
        });

    }

    private void loadAllProducts() {
        productList = new ArrayList<>();
        //get all products
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(firebaseAuth.getUid()).child("Annonces")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        productList.clear();
                        for(DataSnapshot ds : snapshot.getChildren()){
                            ModelProduct modelProduct = ds.getValue(ModelProduct.class);
                            productList.add(modelProduct);

                        }
                        adapterProduct = new AdapterProduct(HomeActivity.this,productList);
                        productsRv.setAdapter(adapterProduct);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void showProductsUI() {

    }

    private void checkUser(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user==null){
            //user not logged in start activity
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        }
        else {
            //user is logger in
            loadMyInfo();

        }


    }

    private void loadMyInfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.orderByChild("uid").equalTo(firebaseAuth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds: snapshot.getChildren()){
                            String name = ""+ds.child("name").getValue();
                            nameTv.setText(name);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}
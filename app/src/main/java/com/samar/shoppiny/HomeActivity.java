package com.samar.shoppiny;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private ImageView addAnnonce, profileIv;
    private TextView nameTv,tabProductsTv;
    private EditText searchProductEt;
    private ImageView logoutBtn;
    private FirebaseAuth firebaseAuth;
    private RelativeLayout productsRl;
    private ArrayList<ModelProduct> productList;
    private RecyclerView productsRv, asia_recycler;
    private AdapterProduct adapterProduct;
    AsiaFoodAdapter asiaFoodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addAnnonce = findViewById(R.id.fab);
        //nameTv = findViewById(R.id.nameTv);
        //productsRl = findViewById(R.id.productsRl);
        logoutBtn = findViewById(R.id.logoutBtn);
        productsRv = findViewById(R.id.productsRv);
        profileIv = findViewById(R.id.profileIv);
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
        List<AsiaFood> asiaFoodList = new ArrayList<>();
        asiaFoodList.add(new AsiaFood("Iphone 14 pro max", "7600 TND", R.drawable.iphone, "4.5", "istore"));
        asiaFoodList.add(new AsiaFood(" Montre ", "120 TND", R.drawable.montre, "4.2", "City Watch"));
        asiaFoodList.add(new AsiaFood(" TV", "1500 TND", R.drawable.tv, "4.5", "LG"));
        asiaFoodList.add(new AsiaFood(" Climatiseur", "6500 TND", R.drawable.clim, "4.2", "LG"));
        asiaFoodList.add(new AsiaFood("Fiat punto", "50 000 TND", R.drawable.fiat, "4.5", "Fiat"));
        asiaFoodList.add(new AsiaFood(" Vespa", "4900 TND", R.drawable.vespa, "4.2", "Vespa"));

        setAsiaRecycler(asiaFoodList);



    }
    private void setAsiaRecycler(List<AsiaFood> asiaFoodList) {

        asia_recycler = findViewById(R.id.asia_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        asia_recycler.setLayoutManager(layoutManager);
        asiaFoodAdapter = new AsiaFoodAdapter(this, asiaFoodList);
        asia_recycler.setAdapter(asiaFoodAdapter);

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
                        productsRv = findViewById(R.id.productsRv);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this, RecyclerView.HORIZONTAL, false);
                        productsRv.setLayoutManager(layoutManager);
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
                                String profileImage = ""+ds.child("profileImage").getValue();
                                try{
                                    Picasso.get().load(profileImage).placeholder(R.drawable.ic_baseline_person_24).into(profileIv);
                                }
                                catch (Exception e){
                                    profileIv.setImageResource(R.drawable.ic_baseline_shopping_cart_24);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }
}
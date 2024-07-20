package com.app.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminCategory extends AppCompatActivity {
    ImageView electronics, clothes, footware, books,
            accessories, petsupplies,homedecor, beauty, groceries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);
        id();

        electronics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(), AdminAddNewProduct.class);
                i.putExtra("category", "electronics");
                startActivity(i);
            }
        });

        clothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(), AdminAddNewProduct.class);
                i.putExtra("category", "clothes");
                startActivity(i);
            }
        });

        footware.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(), AdminAddNewProduct.class);
                i.putExtra("category", "footware");
                startActivity(i);
            }
        });

        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(), AdminAddNewProduct.class);
                i.putExtra("category", "books");
                startActivity(i);
            }
        });

        accessories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(), AdminAddNewProduct.class);
                i.putExtra("category", "accessories");
                startActivity(i);
            }
        });


        petsupplies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(), AdminAddNewProduct.class);
                i.putExtra("category", "petsupplies");
                startActivity(i);
            }
        });

        homedecor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(), AdminAddNewProduct.class);
                i.putExtra("category", "homedecor");
                startActivity(i);
            }
        });

        beauty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(), AdminAddNewProduct.class);
                i.putExtra("category", "beauty");
                startActivity(i);
            }
        });

        groceries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(), AdminAddNewProduct.class);
                i.putExtra("category", "groceries");
                startActivity(i);
            }
        });

    }

    private void id() {
        electronics= findViewById(R.id.iv_electronics);
        clothes= findViewById(R.id.iv_clothes);
        footware= findViewById(R.id.iv_Footware);
        books= findViewById(R.id.iv_books);
        accessories= findViewById(R.id.iv_accessories);
        petsupplies= findViewById(R.id.iv_petsupplies);
        homedecor= findViewById(R.id.iv_homedecor);
        beauty= findViewById(R.id.iv_beauty);
        groceries= findViewById(R.id.iv_groceries);
    }
}
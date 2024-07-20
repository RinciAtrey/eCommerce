package com.app.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.Model.Users;
import com.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    private Button btn_main_login, btn_join;
    private ProgressDialog progressDialog;
    private ImageView iv_gif;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_main_login= findViewById(R.id.btn_main_login);
        btn_join= findViewById(R.id.btn_join);
        iv_gif= findViewById(R.id.iv_gif);
        progressDialog= new ProgressDialog(this);



        Paper.init(this);

        btn_main_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(), login.class);
                startActivity(i);
            }
        });

        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii= new Intent(getApplicationContext(), register.class);
                startActivity(ii);
            }
        });

        String UserNameKey=Paper.book().read(Prevalent.UserNameKey);
        String UserPasswordKey=Paper.book().read(Prevalent.UserPasswordKey);

        if(UserNameKey!= ""&& UserPasswordKey!= ""){
            if(!TextUtils.isEmpty(UserNameKey) && !TextUtils.isEmpty(UserPasswordKey)){
               AllowAccess(UserNameKey,UserPasswordKey);

                progressDialog.setTitle("Already Logged in");
                progressDialog.setMessage("Please wait...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
            }

        }

    }

    private void AllowAccess(String name, String password) {
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("Users").child(name).exists()){
                    Users usersData= snapshot.child("Users").child(name).getValue(Users.class);
                    if(usersData.getName().equals(name)){
                        if(usersData.getPassword().equals(password)){
                            Toast.makeText(MainActivity.this, "Please wait you are already logged in.", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            Intent i= new Intent(getApplicationContext(), HomePage.class);
                            startActivity(i);
                        }
                    }

                }
                else{
                    Toast.makeText(MainActivity.this, "Account does not exists.", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
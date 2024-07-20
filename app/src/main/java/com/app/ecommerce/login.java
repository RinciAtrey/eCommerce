package com.app.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.Model.Users;
import com.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class login extends AppCompatActivity {
    private EditText et_name, et_password;
    private Button btn_login;
    private CheckBox checkBox;
    private TextView txt_forgot_password, txt_admin, txt_notadmin;
    private ProgressDialog progressDialog;
    private String parentDbName= "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_name = findViewById(R.id.et_loginname);
        et_password = findViewById(R.id.et_loginpassword);
        btn_login= findViewById(R.id.btn_login);
        checkBox= findViewById(R.id.checkBox);
        txt_forgot_password= findViewById(R.id.txt_forgot_password);
        txt_admin= findViewById(R.id.txt_admin);
        txt_notadmin= findViewById(R.id.txt_notadmin);

        progressDialog= new ProgressDialog(this);

        //checkbox
        Paper.init(this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });


        txt_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   btn_login.setText("Login Admin");
                   txt_admin.setVisibility(View.INVISIBLE);
                   txt_notadmin.setVisibility(View.VISIBLE);
                   parentDbName="Admins";
            }
        });
        txt_notadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_login.setText("Login");
                txt_admin.setVisibility(View.VISIBLE);
                txt_notadmin.setVisibility(View.INVISIBLE);
                parentDbName="Users";
            }
        });


    }

    private void LoginUser() {
        String name = et_name.getText().toString();
        String password = et_password.getText().toString();
        if(TextUtils.isEmpty(name)){
            et_name.setError("Enter name");
            et_name.requestFocus();
        }
        else if(TextUtils.isEmpty(password)){
            et_password.setError("Enter password");
            et_password.requestFocus();
        }
        else{
            progressDialog.setTitle("Create Account");
            progressDialog.setMessage("Please wait, while your account is being created");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            AllowAccessToAccount(name, password);
        }
    }

    private void AllowAccessToAccount(String name, String password) {

        if(checkBox.isChecked()){
            Paper.book().write(Prevalent.UserNameKey, name);
            Paper.book().write(Prevalent.UserPasswordKey, password);
        }


        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              if(snapshot.child(parentDbName).child(name).exists()){
                  Users usersData= snapshot.child(parentDbName).child(name).getValue(Users.class);

                  if(usersData.getName().equals(name)){
                      if(usersData.getPassword().equals(password)){

                          if(parentDbName.equals("Admins")){
                              Toast.makeText(login.this, "Logged in sucessfully Admin.", Toast.LENGTH_SHORT).show();
                              progressDialog.dismiss();
                              Intent i= new Intent(getApplicationContext(), AdminCategory.class);
                              startActivity(i);

                          }
                          else if(parentDbName.equals("Users")){
                              Toast.makeText(login.this, "Logged in sucessfully.", Toast.LENGTH_SHORT).show();
                              progressDialog.dismiss();
                              Intent i= new Intent(getApplicationContext(), HomePage.class);
                              startActivity(i);

                          }
                      }
                  }

              }
              else{
                  Toast.makeText(login.this, "Account does not exists.", Toast.LENGTH_SHORT).show();
                  progressDialog.dismiss();
              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
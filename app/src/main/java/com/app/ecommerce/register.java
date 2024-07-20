package com.app.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class register extends AppCompatActivity {
    private Button btn_createaccount;
    private TextInputEditText et_name, et_number, et_password;
    TextView tv_login;
    private ProgressDialog progreedialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        id();

        btn_createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();
            }
        });
        progreedialog= new ProgressDialog(this);

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(), login.class);
                startActivity(i);
                finish();
            }
        });


    }

    private void CreateAccount() {
        String name= et_name.getText().toString();
        String number= et_number.getText().toString();
        String password= et_password.getText().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            et_name.setError("Full name is required");
            et_name.requestFocus();
        }
        else if(TextUtils.isEmpty(number)){
            Toast.makeText(this, "Please enter your Phone Number", Toast.LENGTH_SHORT).show();
            et_number.setError("Full name is required");
            et_number.requestFocus();

        } else if (number.length() != 10) {
            Toast.makeText(this, "Please enter valid phone number", Toast.LENGTH_SHORT).show();
            et_number.setError("Mobile number should be of 10 digits");
            et_number.requestFocus();


        } else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter your Password", Toast.LENGTH_SHORT).show();
            et_password.setError("Full name is required");
            et_password.requestFocus();
        }
        else if (password.length()<6){
            Toast.makeText(this, "Please enter a new password", Toast.LENGTH_SHORT).show();
            et_password.setError("Password too weak");
            et_password.requestFocus();
        }
        else{
            progreedialog.setTitle("Create Account");
            progreedialog.setMessage("Please wait, while your account is being created");
            progreedialog.setCanceledOnTouchOutside(false);
            progreedialog.show();

            ValidateName(name, number ,password);
        }


    }

    private void ValidateName(String name, String number, String password){
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!(snapshot.child("Users").child(name).exists())){
                    HashMap<String, Object> userdataMap= new HashMap<>();
                    userdataMap.put("name", name);
                    userdataMap.put("number", number);
                    userdataMap.put("password", password);

                    RootRef.child("Users").child(name).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(register.this, "Congratulations"+ name +"your account has been created!", Toast.LENGTH_SHORT).show();
                                    progreedialog.dismiss();
                                    }
                                    else{
                                        progreedialog.dismiss();
                                        Toast.makeText(register.this, "Network issue, try again.", Toast.LENGTH_SHORT).show();
                                    }


                                }
                            });

                }
                else{
                    Toast.makeText(register.this, name+ "already exists", Toast.LENGTH_SHORT).show();
                    progreedialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void id() {
        btn_createaccount= findViewById(R.id.btn_createaccount);
        et_name= findViewById(R.id.et_name);
        et_number= findViewById(R.id.et_number);
        et_password= findViewById(R.id.et_password);
        tv_login=findViewById(R.id.tv_login);

    }
}
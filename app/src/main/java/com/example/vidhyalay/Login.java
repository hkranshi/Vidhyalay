package com.example.vidhyalay;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;

public class Login extends AppCompatActivity {
      private TextView register;
      private TextView fpwd;
      private EditText id,pswd;
      private Button b;
      private ProgressBar pd;
      private RadioButton stud,teach;
      DatabaseReference ref;
     FirebaseAuth fire;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        register=findViewById(R.id.register);
        fpwd=findViewById(R.id.forgetPassword);
        id=findViewById(R.id.editId);
        pswd=findViewById(R.id.editPassword);
        stud=findViewById(R.id.stud);
        teach=findViewById(R.id.Teach);
        b=findViewById(R.id.Login);
        fire=FirebaseAuth.getInstance();
            ref=FirebaseDatabase.getInstance().getReference();




//after logged in
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Login.this ,Register.class);
                startActivity(i);
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=id.getText().toString().trim();
                String password=pswd.getText().toString().trim();
                    if(TextUtils.isEmpty(email)){
                        id.setError("Email is required");
                        return;
                    }
                if(TextUtils.isEmpty(password)){
                    pswd.setError("Password is required");
                    return;
                }

              //  pd.setVisibility(View.VISIBLE);
                fire.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            String id=fire.getUid();
                            System.out.println(id);
                            if(stud.isChecked()) {
                                ref.child("student").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                        if (snapshot.child(id).exists()) {
                                            Toast.makeText(Login.this,"Login Successfully",Toast.LENGTH_LONG).show();
                                            Intent i1 = new Intent(Login.this, MainAct.class);
                                            startActivity(i1);
                                            finish();
                                        } else {
                                            Toast.makeText(Login.this, "No such User Found", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                                    }
                                });
                            }
                                if(teach.isChecked()) {
                                   ref.child("teacher").addValueEventListener(new ValueEventListener() {
                                       @Override
                                       public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                           if (snapshot.child(id).exists()) {
                                               Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_LONG).show();
                                               Intent i1 = new Intent(Login.this, Teach.class);
                                               startActivity(i1);
                                               finish();
                                           }else{
                                               Toast.makeText(Login.this,"No such User Found",Toast.LENGTH_LONG).show();
                                           }
                                       }
                                       @Override
                                       public void onCancelled(@NonNull @NotNull DatabaseError error) {
                                       }
                                   });
                                }
                        }else{
                            Toast.makeText(Login.this,"Error !  "+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
fpwd.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i3=new Intent(Login.this, ForgetPassword.class);
        startActivity(i3);
    }
});
    }

    @Override
    protected void onStart() {
        super.onStart();
       //
        FirebaseUser firebaseUser=fire.getCurrentUser();
        if(firebaseUser!=null){
            String id=fire.getUid();
            ref.child("teacher").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (snapshot.child(id).exists()) {
                      //  Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_LONG).show();
                        Intent i1 = new Intent(Login.this, Teach.class);
                        startActivity(i1);
                        finish();
                    }else{
                        Intent i=new Intent(Login.this,MainAct.class);
                        startActivity(i);
                        finish();
                    }
                }
                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                }
            });
        }

        }
      /*  else {
       Intent i=new Intent(Login.this,Login.class);
            startActivity(i);
            finish();
        }*/

    }

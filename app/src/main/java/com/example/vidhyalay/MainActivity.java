package com.example.vidhyalay;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
        FirebaseAuth fauth;
        DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fauth=FirebaseAuth.getInstance();
        String id1=fauth.getUid();
        Thread t=new Thread(){
          public void run() {

              try {
                  sleep(2000);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
              finally {
                  FirebaseUser firebaseUser = fauth.getCurrentUser();

                      Intent i = new Intent(MainActivity.this, com.example.vidhyalay.Login.class);
                      startActivity(i);
                      finish();
                  }
              }


        }; t.start();

    }
}
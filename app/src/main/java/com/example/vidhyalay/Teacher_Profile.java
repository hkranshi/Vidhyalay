package com.example.vidhyalay;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class Teacher_Profile extends AppCompatActivity {
            ImageView img;
            TextView name,email,news;
            Button edit;
            FloatingActionButton fbutton;
    FirebaseFirestore fstore;
    FirebaseAuth fb;
    FirebaseUser user;
    FirebaseAuth fauth;
    String uid;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);
        img=findViewById(R.id.circleImage);
        name=findViewById(R.id.textView15);
        email=findViewById(R.id.textView17);
        news=findViewById(R.id.textView18);
        fbutton=findViewById(R.id.floatingActionButton2);
        fstore=FirebaseFirestore.getInstance();
        fauth=FirebaseAuth.getInstance();
        uid=fauth.getCurrentUser().getUid();
      //  lout=findViewById(R.id.button9);
        edit=findViewById(R.id.button8);
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri=Uri.parse("https://www.gktoday.in/current-affairs/");
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Teacher_Profile.this,editTeacherProfile.class);
                startActivity(i);
                finish();
            }
        });
        fb=FirebaseAuth.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();
        StorageReference prof=storageReference.child("users/"+ fb.getCurrentUser().getUid()+"/profile.jpg");
        prof.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(img);
            }
        });
        DocumentReference documentReference=fstore.collection("Teacher").document(uid);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                String name1=value.getString("UName");
                String id1= value.getString("Email");
                name.setText(name1);
                email.setText(id1);
            }
        });
        fbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,1000);
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000){
            if(resultCode== Activity.RESULT_OK){
                Uri image=data.getData();

                // profilePic.setImageURI(image);
                uploadImage(image);
            }
        }
    }

    private void uploadImage(Uri imageuri) {
        StorageReference file=storageReference.child("users/"+fb.getCurrentUser().getUid()+"/profile.jpg");
        file.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Picasso.get().load(imageuri).into(img);
                Toast.makeText(Teacher_Profile.this,"Image Upload",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(Teacher_Profile.this,"Failed",Toast.LENGTH_LONG).show();
            }
        });
    }
}
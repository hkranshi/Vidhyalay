package com.example.vidhyalay;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vidhyalay.ui.home1.Home;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class Post extends AppCompatActivity {
    ImageView imageView;
    EditText text;
    Button choose,upload;
    private Uri filepath;
    ProgressBar progressBar;
private FirebaseStorage firebaseStorage;
private StorageReference storageReference;
private DatabaseReference root= FirebaseDatabase.getInstance().getReference("Post");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        imageView=findViewById(R.id.myImage);
        choose=findViewById(R.id.chose);
        upload=findViewById(R.id.uplo);
        text=findViewById(R.id.title1);
      //  progressBar.setVisibility(View.INVISIBLE);
        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference("Post");
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage(v);
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage(v);
            }
        });
    }
    private void uploadImage(View v) {
        String title=text.getText().toString();
        if (title.trim().equals("")) {
            text.setError("Please give a title");
        }
        if(filepath!=null){
            uploadFirebase(filepath);
        }else {
            Toast.makeText(Post.this,"Please Select Image",Toast.LENGTH_LONG).show();
        }

    }

    private void uploadFirebase(Uri filepath) {
        String t1=text.getText().toString();
        StorageReference fileref=storageReference.child(System.currentTimeMillis()+"."+getFileExt(filepath));
        fileref.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Post_Member member=new Post_Member(uri.toString(),t1, Home.name2);
                        String mid=root.push().getKey();
                        root.child(mid).setValue(member);
                   Toast.makeText(Post.this,"Uploaded Successfully",Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {
               // progressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
               // progressBar.setVisibility(View.INVISIBLE);
           Toast.makeText(Post.this,"Uploading Failed !!",Toast.LENGTH_LONG).show();
            }
        });
    }

    private String getFileExt(Uri filepath) {
        ContentResolver cr=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(filepath));
    }

    private void chooseImage(View v) {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Image"),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            filepath=data.getData();
            try {
             Bitmap   bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
package com.example.vidhyalay;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vidhyalay.ui.account.accountFragment;
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
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditChoice extends AppCompatActivity {
    FloatingActionButton floatButton;
    EditText name, id, phone,branch, gender;
    static TextView course;
    Button save;
    ImageView pic;
    Spinner s;
    FirebaseAuth fauth;
    FirebaseFirestore fstore;
    FirebaseUser fuser;
    String uid;
    StorageReference storageReference;
    Uri img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_choice);
        name = findViewById(R.id.editTextTextPersonName);
        id = findViewById(R.id.editTextTextPersonName2);
        phone = findViewById(R.id.editTextTextPersonName3);
        gender = findViewById(R.id.editTextTextPersonName4);
        course = findViewById(R.id.editTextTextPersonName6);
        s=findViewById(R.id.spinner2);
         branch=findViewById(R.id.editTextTextPersonName8);
        floatButton = findViewById(R.id.floatingActionButton);
        save = findViewById(R.id.button2);
        pic = findViewById(R.id.imageView4);
        fauth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        fuser=fauth.getCurrentUser();
        uid=fauth.getCurrentUser().getUid();
        storageReference= FirebaseStorage.getInstance().getReference();
        //get Image
        StorageReference prof=storageReference.child("users/"+ fauth.getCurrentUser().getUid()+"/profile.jpg");
        prof.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(pic);
            }
        });

        DocumentReference documentReference=fstore.collection("Students").document(uid);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                String name1=value.getString("UName");
                String id1= value.getString("Email");
                String no= value.getString("Phone_no");
                String gen= value.getString("Gender");
                String cou= value.getString("Course");
                String br= value.getString("Branch");
                    name.setText(name1);
                    id.setText(id1);
                    phone.setText(no);
                    gender.setText(gen);
                    course.setText(cou);
                    branch.setText(br);

            }
        });
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (validateMobile(phone.getText().toString())) {
                    save.setEnabled(true);
                } else {
                    save.setEnabled(false);
                    phone.setError("Invalid Mobile No");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditChoice.this, Course_Edit.class);
                startActivity(i);
                String c = Course_Edit.s;
                course.setText(c);
                spin();
            }
        });
            floatButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // CropImage.activity().start(EditChoice.this);

                   Intent openGall=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                   startActivityForResult(openGall,1000);
                }
            });
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (name.getText().toString().isEmpty() || id.getText().toString().isEmpty() || phone.getText().toString().isEmpty() || gender.getText().toString().isEmpty() || branch.getText().toString().isEmpty()) {
                        Toast.makeText(EditChoice.this, "Fill all the required field", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (!gender.getText().toString().equals("Male") && !gender.getText().toString().equals("Female") && !gender.getText().toString().equals("Other")) {
                        Toast.makeText(EditChoice.this, "Edit Gender field to Male/Female/Other", Toast.LENGTH_LONG).show();
                        return;
                    }
                    String mail = id.getText().toString();
                    String item = null;
                    if (s == null ||s.getSelectedItem() == null) {
                        Toast.makeText(EditChoice.this,"Select branch from the spinner and if you not pressed course then press on the course",Toast.LENGTH_LONG).show();
                        //  ((TextView) s.getSelectedView()).setError("None Selected");
                    }
                   else {
                        item = s.getSelectedItem().toString();
                        branch.setText(item);

                        fuser.updateEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                DocumentReference doc = fstore.collection("Students").document(fuser.getUid());
                                Map<String, Object> edit = new HashMap<>();
                                edit.put("UName", name.getText().toString());
                                edit.put("Email", mail);
                                edit.put("Gender", gender.getText().toString());
                                edit.put("Phone_no", phone.getText().toString());
                                edit.put("Course", course.getText().toString());
                                edit.put("Branch", branch.getText().toString());
                                doc.update(edit);
                                Toast.makeText(EditChoice.this, "Profile Updated", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(EditChoice.this, MainAct.class);
                                startActivity(i);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Toast.makeText(EditChoice.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
              }
            });

    }
    boolean validateMobile(String input) {
        Pattern p = Pattern.compile("[6-9][0-9]{9}");
        Matcher m = p.matcher(input);
        return m.matches();
    }

    private void spin() {
        ArrayAdapter<String> cd1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.Twelth));
        ArrayAdapter<String> cd = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.Tenth));
        ArrayAdapter<String> cd2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.Btech));
        ArrayAdapter<String> cd5 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.UPSC));
        ArrayAdapter<String> cd6 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.iit_jam));
        ArrayAdapter<String> cd3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.iit_jam));
        if(course.getText().toString().trim().equals("10th (Matriculation)")){
            cd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            s.setAdapter(cd);
        }
        if(course.getText().toString().trim().equals("12th (Secondary School)")){
            cd1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            s.setAdapter(cd1);
        }
        if(course.getText().toString().trim().equals("B Tech")){
            cd2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            s.setAdapter(cd2);
        }
        if(course.getText().toString().trim().equals("IIT JAM")){
            cd6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            s.setAdapter(cd6);
        }
        if(course.getText().toString().trim().equals("Upsc")){
            cd5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            s.setAdapter(cd5);
        }
        if(course.getText().toString().trim().equals("Gate")){
            cd2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            s.setAdapter(cd2);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000){
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            if(resultCode== Activity.RESULT_OK){
                 img=data.getData();

                // profilePic.setImageURI(image);
                uploadImage(img);
            }
        }
    }
    private void uploadImage(Uri imageuri) {
        StorageReference file=storageReference.child("users/"+fauth.getCurrentUser().getUid()+"/profile.jpg");
        file.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               // CropImage.activity(imageuri)
                 //       .setGuidelines(CropImageView.Guidelines.ON).setMultiTouchEnabled(true).start(EditChoice.this);
                Picasso.get().load(imageuri).into(pic);
                Toast.makeText(EditChoice.this,"Image Upload",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(EditChoice.this,"Failed",Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(EditChoice.this,MainAct.class);
        startActivity(i);
        finish();
    }
}
package com.example.vidhyalay;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vidhyalay.ui.Upload.Upload;
import com.example.vidhyalay.ui.account.accountFragment;
import com.example.vidhyalay.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vidhyalay.databinding.ActivityTeachBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Teach extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityTeachBinding binding;
    FirebaseAuth fauth;
    FirebaseFirestore fstore;
    FirebaseUser fuser;
    String uid;
  //  String name;
   static String name1,email;
    StorageReference storageReference;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTeachBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarTeach.toolbar);
        binding.appBarTeach.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
         navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.home, R.id.live_session, R.id.upload,R.id.schedule)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_teach);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationView nv=findViewById(R.id.nav_view);
        fauth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        fuser=fauth.getCurrentUser();
        uid=fauth.getCurrentUser().getUid();
        floatingActionButton=findViewById(R.id.fab);
        storageReference= FirebaseStorage.getInstance().getReference();
        DocumentReference documentReference=fstore.collection("Teacher").document(uid);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
               name1=value.getString("UName");
               email=value.getString("Email");
                String branch= value.getString("Branch");
                String course=value.getString("Course");
                    System.out.println(branch);
                Bundle b=new Bundle();
                b.putString("uname", name1);
                b.putString("branch",branch);
                b.putString("course",course);
                Upload.putArguments(b);


            }
        });
                    updateHeader();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Teach.this,TeacherPost.class);
                startActivity(i);
               // finish();
            }
        });
    }

    private void updateHeader() {
        NavigationView nv=findViewById(R.id.nav_view);
        View hv=nv.getHeaderView(0);
        TextView name=hv.findViewById(R.id.text);
        TextView eid=hv.findViewById(R.id.textView);
        TextView profile=hv.findViewById(R.id.textView19);
        ImageView pic=hv.findViewById(R.id.imageView);
        //get Image
        StorageReference prof=storageReference.child("users/"+ fauth.getCurrentUser().getUid()+"/profile.jpg");
        prof.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(Teach.this).load(uri).into(pic);
            }
        });
        //get name and id
        DocumentReference documentReference=fstore.collection("Teacher").document(uid);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
              String  name11=value.getString("UName");
               String email1=value.getString("Email");
                name.setText(name11);
                eid.setText(email1);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Teach.this,Teacher_Profile.class);
                startActivity(i);
              //  finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.teach, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_settings){
            Intent i=new Intent(Teach.this,SettingsActivity.class);
            startActivity(i);
          //  finish();
        }
        if(id==R.id.log){
            FirebaseAuth.getInstance().signOut();
          Intent i=new Intent(Teach.this, MainActivity.class);
            startActivity(i);
            finish();
           // moveTaskToBack(true);
            //android.os.Process.killProcess(android.os.Process.myPid());
           // System.exit(1);
        }
        if(id==R.id.shareit){
            ApplicationInfo api= getApplicationContext().getApplicationInfo();
            String apk=api.sourceDir;
            Intent i=new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            String body="Download this App";
            String sub="http://play.google.com";
            i.putExtra(Intent.EXTRA_TEXT,body);
            i.putExtra(Intent.EXTRA_TEXT,sub);
            startActivity(Intent.createChooser(i,"Share using"));
        }
        if(id==R.id.action_about){
            Intent i=new Intent(Teach.this,AboutSection.class);
            startActivity(i);
        }
        return  true;
    }
    @Override
    public void onBackPressed() {

            super.onBackPressed();
        }


            // finish();


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_teach);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}
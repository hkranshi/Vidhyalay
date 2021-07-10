package com.example.vidhyalay;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.Toolbar;
import android.app.SearchManager;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.vidhyalay.databinding.ActivityMain2Binding;
import com.example.vidhyalay.ui.account.accountFragment;
import com.example.vidhyalay.ui.home1.Home;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.drjacky.imagepicker.ImagePicker;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.concurrent.Executor;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAct extends AppCompatActivity {
    private ActivityMain2Binding binding;
        FirebaseAuth fbw;
    FirebaseFirestore fstore;
    String uid;
    BottomNavigationView navView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_download, R.id.navigation_notes,R.id.navigation_test,R.id.navigation_account)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main2);
      //  NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
            fbw=FirebaseAuth.getInstance();
      setSupportActionBar(binding.toolbar);
        fstore= FirebaseFirestore.getInstance();
        uid=fbw.getCurrentUser().getUid();
      //  String branch,course;
        DocumentReference documentReference=fstore.collection("Students").document(uid);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                String name=value.getString("UName");
                String id= value.getString("Email");
                  String   branch=value.getString("Branch");
                  String  course=value.getString("Course");
                Bundle b=new Bundle();
                b.putString("uname", name);
                b.putString("id",id);
                accountFragment.putArguments(b);
                Bundle b1=new Bundle();
                b1.putString("course", course);
                b1.putString("branch",branch);
                b1.putString("name",name);
                Home.putArguments(b1);
                Bundle b2=new Bundle();
                b2.putString("course1", course);
                b2.putString("branch1",branch);
                com.example.vidhyalay.ui.notes.notesFragment.putArguments(b2);
                Bundle b3=new Bundle();
                b3.putString("course1", course);
                b3.putString("branch1",branch);
                com.example.vidhyalay.ui.test.Test_Fragment.putArguments(b3);


            }
        });
    }
    @Override
    public void onBackPressed() {
        if(navView.getSelectedItemId()==R.id.navigation_home) {
          // super.onBackPressed();
            finishAffinity();
           System.exit(0);

           // finish();
        }
        navView.setSelectedItemId(R.id.navigation_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       int id=item.getItemId();
       if(id==R.id.about){
                Intent i=new Intent(MainAct.this,AboutSection.class);
                startActivity(i);
             //   finish();
       }
       if(id==R.id.share){
           ApplicationInfo api= getApplicationContext().getApplicationInfo();
           String apk=api.sourceDir;
           Intent i=new Intent(Intent.ACTION_SEND);
           i.setType("text/plain");
           String body="Download this App";
           String sub="http://play.google.com/apk";
           i.putExtra(Intent.EXTRA_TEXT,body);
           i.putExtra(Intent.EXTRA_TEXT,sub);
           startActivity(Intent.createChooser(i,"Share using"));

       }
     if(id==R.id.search){
         FirebaseAuth.getInstance().signOut();
        // moveTaskToBack(true);
        // android.os.Process.killProcess(android.os.Process.myPid());
        // System.exit(1);
         Intent i=new Intent(MainAct.this, MainActivity.class);
         startActivity(i);
         finish();
               }




          // h.search();

       if(id==R.id.seting){
            Intent i=new Intent(MainAct.this,SettingsActivity.class);
            startActivity(i);
       }
      return  true;
    }

}
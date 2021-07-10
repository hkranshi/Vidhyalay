package com.example.vidhyalay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PreviousPaperActivity2 extends AppCompatActivity {
        RecyclerView pid;
    ArrayList<Paper> datalist;
    FirebaseFirestore fstore;
    PaperAdapter paperAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_paper2);
        pid=(RecyclerView)findViewById(R.id.pid);
        fstore= FirebaseFirestore.getInstance();
        pid.setHasFixedSize(true);
        pid.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        datalist=new ArrayList<>();
        paperAdapter=new PaperAdapter(datalist);
        pid.setAdapter(paperAdapter);
        fstore.collection("Paper"+" "+com.example.vidhyalay.ui.test.Test_Fragment.course+" "+com.example.vidhyalay.ui.test.Test_Fragment.branch).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot d:list){
                    Paper obj=d.toObject(Paper.class);
                    datalist.add(obj);
                }
                paperAdapter.notifyDataSetChanged();
            }
        });
    }
}
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

public class NotesActivity extends AppCompatActivity {
        RecyclerView nid;
    ArrayList<Notes> datalist1;
    FirebaseFirestore fstore;
    NotesAdapter adapter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        nid=(RecyclerView)findViewById(R.id.nid);
        fstore= FirebaseFirestore.getInstance();
        nid.setHasFixedSize(true);
        nid.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        datalist1=new ArrayList<>();
        adapter1=new NotesAdapter(datalist1);
        nid.setAdapter(adapter1);
        fstore.collection("Notes"+" "+com.example.vidhyalay.ui.notes.notesFragment.course+" "+com.example.vidhyalay.ui.notes.notesFragment.branch).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot d:list){
                    Notes obj=d.toObject(Notes.class);
                    datalist1.add(obj);
                }
                adapter1.notifyDataSetChanged();
            }
        });


    }
}
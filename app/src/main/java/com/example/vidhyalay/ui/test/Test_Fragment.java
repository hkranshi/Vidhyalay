package com.example.vidhyalay.ui.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vidhyalay.Book;
import com.example.vidhyalay.Notes;
import com.example.vidhyalay.NotesAdapter;
import com.example.vidhyalay.Paper;
import com.example.vidhyalay.PaperAdapter;
import com.example.vidhyalay.R;
import com.example.vidhyalay.TestAdapter;
import com.example.vidhyalay.databinding.FragmentTestBinding;
import com.example.vidhyalay.test;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Test_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Test_Fragment extends Fragment {
    private FragmentTestBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Test_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Test_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Test_Fragment newInstance(String param1, String param2) {
        Test_Fragment fragment = new Test_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
public static String course,branch;
    public static void putArguments(Bundle b3) {
       course=b3.getString("course1");
       branch=b3.getString("branch1");
       System.out.println(course+" "+branch);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
        RecyclerView test,paper;
    TestAdapter adapter;
    FirebaseFirestore fstore;
    PaperAdapter paperAdapter;
    ArrayList<Paper> datalist;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_test_, container, false);
        test=(RecyclerView) v.findViewById(R.id.recyclerView);
        paper=(RecyclerView) v.findViewById(R.id.paper);
        fstore= FirebaseFirestore.getInstance();
     //   test.setHasFixedSize(true);
        test.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        paper.setHasFixedSize(true);
        paper.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        FirebaseRecyclerOptions<com.example.vidhyalay.test> options=new FirebaseRecyclerOptions.Builder<test>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child(course+" "+branch+" "+"Paper").child("Quiz"),test.class).build();
        adapter=new TestAdapter(options);
        test.setAdapter(adapter);
        datalist=new ArrayList<>();
        paperAdapter=new PaperAdapter(datalist);
        paper.setAdapter(paperAdapter);
        fstore.collection("Paper"+" "+course+" "+branch).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
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
        return v;
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
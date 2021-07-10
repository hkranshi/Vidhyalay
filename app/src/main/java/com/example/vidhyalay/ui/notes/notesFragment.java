package com.example.vidhyalay.ui.notes;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vidhyalay.Book;
import com.example.vidhyalay.BookAdapter;
import com.example.vidhyalay.Notes;
import com.example.vidhyalay.NotesAdapter;
import com.example.vidhyalay.R;
import com.example.vidhyalay.SyllabusPdf;
import com.example.vidhyalay.databinding.FragmentNotesBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link notesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class notesFragment extends Fragment {
        private FragmentNotesBinding binding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public notesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static notesFragment newInstance(String param1, String param2) {
        notesFragment fragment = new notesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
  public   static String course,branch;

    public static void putArguments(Bundle b2) {
        course =b2.getString("course1");
        branch=b2.getString("branch1");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    TextView syllabus;
    RecyclerView book,notes;
    FirebaseFirestore fstore;
    BookAdapter adapter;
    ArrayList<Book> datalist;
    NotesAdapter adapter1;
    ArrayList<Notes> datalist1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_notes, container, false);
        syllabus=(TextView) v.findViewById(R.id.syllabus);
        book=(RecyclerView) v.findViewById(R.id.booksrecycle);
        notes=(RecyclerView) v.findViewById(R.id.notesrecycle);
        fstore= FirebaseFirestore.getInstance();
        syllabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(), SyllabusPdf.class);
                startActivity(i);
            }
        });

        System.out.println("Book"+" "+course+" "+branch);
        book.setHasFixedSize(true);
        book.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        datalist=new ArrayList<>();
        adapter=new BookAdapter(datalist);
        book.setAdapter(adapter);
        fstore.collection("Book"+" "+course+" "+branch).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list= queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot d:list){
                    Book obj=d.toObject(Book.class);
                    datalist.add(obj);
                }
                adapter.notifyDataSetChanged();
            }
        });
        notes.setHasFixedSize(true);
        notes.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        datalist1=new ArrayList<>();
        adapter1=new NotesAdapter(datalist1);
        notes.setAdapter(adapter1);
        fstore.collection("Notes"+" "+course+" "+branch).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
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


        return  v;
    }


}
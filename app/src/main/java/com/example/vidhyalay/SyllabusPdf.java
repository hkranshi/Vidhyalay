package com.example.vidhyalay;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SyllabusPdf extends AppCompatActivity {
   private PDFView pdfView;
    FirebaseFirestore fstore;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus_pdf);
        fstore= FirebaseFirestore.getInstance();
        pdfView=findViewById(R.id.pdfsyllabus);
        DocumentReference documentReference=fstore.collection("Syllabus").document(com.example.vidhyalay.ui.notes.notesFragment.course+" "+com.example.vidhyalay.ui.notes.notesFragment.branch);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                 url=value.getString("url");
                 System.out.println(url);
               //  Uri uri=Uri.parse(url);
///pdfView.fromUri(uri).load();


                 new PdfDownload().execute(url);
            }
        });
    }
    private class PdfDownload extends AsyncTask<String,Void, InputStream>{

        @Override
        protected InputStream doInBackground(String... strings) {
                    InputStream inputStream=null;
            try {
                URL url=new URL(strings[0]);
                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                if(httpURLConnection.getResponseCode()==200){
                    inputStream=new BufferedInputStream(httpURLConnection.getInputStream());

                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
           pdfView.fromStream(inputStream).load();
        }
    }
}
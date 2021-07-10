package com.example.vidhyalay;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class PreviousPdf extends AppCompatActivity {
   // WebView notes1;
    //InputStream inputStream=null;
    PDFView notes1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_pdf);
        notes1=(PDFView) findViewById(R.id.pdfsyllabus1);
       // notes1.getSettings().setJavaScriptEnabled(true);
        String fname=getIntent().getStringExtra("title");
        String furl=getIntent().getStringExtra("url");

        new PdfDownload().execute(furl);

      /*  notes1.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        String url="";
        try {
            url= URLEncoder.encode(furl,"UTF-8");
        }catch (Exception e){}
        notes1.loadUrl("https://docs.google.com/gview?embedded=true&url="+url);*/
    }
    private class PdfDownload extends AsyncTask<String,Void, InputStream> {

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
            notes1.fromStream(inputStream).load();
        }
    }

}
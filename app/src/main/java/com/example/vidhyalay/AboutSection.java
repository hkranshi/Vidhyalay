package com.example.vidhyalay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AboutSection extends AppCompatActivity {
        TextView about;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_section);
        about=(TextView)findViewById(R.id.about1);


    }
}
package com.example.vidhyalay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Course_Edit extends AppCompatActivity {


    Button tenth,twelth,btech,upsc,gate,iit_jam;
     static String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_edit);
        tenth=findViewById(R.id.button15);
        twelth=findViewById(R.id.button16);
        btech=findViewById(R.id.button3);
        iit_jam=findViewById(R.id.button4);
        upsc=findViewById(R.id.button7);
        gate=findViewById(R.id.button6);
        tenth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 s= tenth.getText().toString();
                ///EditChoice. course.setText(s);
                Intent i=new Intent(Course_Edit.this,EditChoice.class);
                startActivity(i);
                finish();

            }
        });
        twelth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s= twelth.getText().toString();
                //EditChoice. course.setText(s);
                Intent i=new Intent(Course_Edit.this,EditChoice.class);
                startActivity(i);
                finish();

            }
        });
        btech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s= btech.getText().toString();
              //  EditChoice. course.setText(s);
                Intent i=new Intent(Course_Edit.this,EditChoice.class);
                startActivity(i);
                finish();

            }
        });
            iit_jam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s= iit_jam.getText().toString();
              //  EditChoice. course.setText(s);
                Intent i=new Intent(Course_Edit.this,EditChoice.class);
                startActivity(i);
                    finish();
            }
        });
            upsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s= upsc.getText().toString();
              //  EditChoice. course.setText(s);
                Intent i=new Intent(Course_Edit.this,EditChoice.class);
                startActivity(i);
                finish();
            }
        });
            gate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    s= gate.getText().toString();
                //   EditChoice. course.setText(s);
                    Intent i=new Intent(Course_Edit.this,EditChoice.class);
                    startActivity(i);
                        finish();
                }
            });

    }


}
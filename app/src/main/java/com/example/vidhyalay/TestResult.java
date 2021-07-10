package com.example.vidhyalay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TestResult extends AppCompatActivity {
            TextView total,correct,wrong,attempt;
            Button finish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);
        total=(TextView)findViewById(R.id.textView26);
        correct=(TextView)findViewById(R.id.textView28);
        wrong=(TextView)findViewById(R.id.textView30);
        attempt=(TextView)findViewById(R.id.textView32);
        finish=(Button) findViewById(R.id.button9);
        Intent i=getIntent();
        String t=i.getStringExtra("total");
        String c=i.getStringExtra("correct");
        String w=i.getStringExtra("incorrect");
        String a=i.getStringExtra("attempt");
        total.setText(t);
        correct.setText(c);
        wrong.setText(w);
        attempt.setText(a);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(TestResult.this,MainAct.class);
                startActivity(i);
                finish();
            }
        });
    }
}
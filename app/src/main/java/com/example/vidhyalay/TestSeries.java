package com.example.vidhyalay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.vidhyalay.ui.test.Test_Fragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class TestSeries extends AppCompatActivity {
        TextView timer,question;
        RadioGroup select;
        Button next;
        RadioButton o1,o2,o3,o4,ans;
        int correct=0;
        int attempt=0;
        int wrong=0;
        int total=1;
    String fname;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_series);
        timer=(TextView)findViewById(R.id.timer);
        question=(TextView)findViewById(R.id.question);
        select=(RadioGroup)findViewById(R.id.select);
        next=(Button)findViewById(R.id.next);
        o1=(RadioButton)findViewById(R.id.optiona);
        o2=(RadioButton)findViewById(R.id.optionb);
        o3=(RadioButton)findViewById(R.id.optionc);
        o4=(RadioButton)findViewById(R.id.optiond);
        fname=getIntent().getStringExtra("title");
        System.out.println(fname);
        databaseReference=database.getReference().child(Test_Fragment.course+" "+Test_Fragment.branch+" "+"Paper").child(fname);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                   count=(int)snapshot.getChildrenCount();
                   // System.out.println(count);
                    updateQuery();
                    int i=count*30;
                    reverseTimer(i,timer);
                }
                else{}
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });
    }

    private void updateQuery() {
        if(total==count){
            next.setText("Submit");
        }
        if(total>count){
            Intent i=new Intent(TestSeries.this,TestResult.class);
            System.out.println(total+" "+ correct+" "+ wrong+" "+attempt);
            i.putExtra("total",String.valueOf(total-1));
            i.putExtra("correct",String.valueOf(correct));
            i.putExtra("incorrect",String.valueOf(wrong));
            i.putExtra("attempt",String.valueOf(attempt));
            startActivity(i);
            finish();


        }
        else{
            databaseReference=database.getReference().child(Test_Fragment.course+" "+Test_Fragment.branch+" "+"Paper").child(fname).child(String.valueOf(total));
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    com.example.vidhyalay.question q=snapshot.getValue(com.example.vidhyalay.question.class);
                    question.setText(q.getQuestion());
                    o1.setText(q.getOption1());
                    o2.setText(q.getOption2());
                    o3.setText(q.getOption3());
                    o4.setText(q.getOption4());
                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int rid = select.getCheckedRadioButtonId();
                            System.out.println(rid);
                            if (rid == -1) {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        total++;
                                        updateQuery();

                                    }
                                }, 1500);
                            }
                          else {
                                ans = (RadioButton) findViewById(rid);
                                if(ans.getText().toString().equals(q.getAnswer())){
                                    correct++;
                                    attempt++;
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            total++;
                                            updateQuery();
                                        }
                                    }, 1500);
                                }
                                else{
                                    wrong++;
                                    attempt++;
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            total++;
                                            updateQuery();
                                        }
                                    }, 1500);
                                }
                            }
                        }
                    });

                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }
    }
    public void reverseTimer(int second,final TextView tv){
        new CountDownTimer(second*1000+1000,1000){
            @Override
            public void onTick(long millisUntilFinished) {
              int sec=(int)(millisUntilFinished/1000);
              int minute=sec/60;
              sec=sec%60;
              tv.setText(String.format("%02d",minute)
              + ":" +String.format("%02d",sec));
            }

            @Override
            public void onFinish() {
                tv.setText("Completed");
                Intent intent=new Intent(TestSeries.this,TestResult.class);
                intent.putExtra("total",String.valueOf(count));
                    intent.putExtra("correct",String.valueOf(correct));
                    intent.putExtra("incorrect",String.valueOf(wrong));
                    intent.putExtra("attempt",String.valueOf(attempt));
                    startActivity(intent);
                    finish();
            }
        }.start();
    }
}
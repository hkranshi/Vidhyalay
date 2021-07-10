package com.example.vidhyalay;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    TextView date;
     int y,m,d;
     Button r;
     Spinner s;
     Spinner c;
     String text;
 EditText name,id,pwd,cpwd;
 EditText no;
 String uid;
private  RadioButton student,teacher;
 FirebaseAuth fauth;
 FirebaseFirestore fstore;
 RadioGroup g,t;
public RadioButton gen;
    DatabaseReference myRef,myRef2;
    FirebaseDatabase root;
 private DatePickerDialog.OnDateSetListener mDateSetListener;
 int rid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        date = (TextView) findViewById(R.id.date);
        r = (Button) findViewById(R.id.button);
        name = (EditText) findViewById(R.id.uName);
        id = (EditText) findViewById(R.id.mail);
        no = (EditText) findViewById(R.id.phone);
        student=(RadioButton)findViewById(R.id.student);
        teacher=(RadioButton)findViewById(R.id.teacher);
        pwd = (EditText) findViewById(R.id.pwd);
        cpwd = (EditText) findViewById(R.id.cpwd);
        g = (RadioGroup) findViewById(R.id.gender);
        t = (RadioGroup) findViewById(R.id.type);
        fauth=FirebaseAuth.getInstance();
        root=FirebaseDatabase.getInstance();
        fstore=FirebaseFirestore.getInstance();
            myRef=root.getReference("teacher");
            myRef2=root.getReference("student");
        date.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                y = cal.get(Calendar.YEAR);
                m = cal.get(Calendar.MONTH);
                d = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(Register.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, y, m, d);
                dpd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dpd.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month + 1;
                // dayOfMonth=dayOfMonth+1;
                String d = month + "/" + dayOfMonth + "/" + year;
                date.setText(d);
                // date.setText(SimpleDateFormat.getDateInstance().format(cal.getTime()));

            }

        };


        c = (Spinner) findViewById(R.id.course);
        s = (Spinner) findViewById(R.id.branch);
        spinnerSpecification();
                no.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if(validateMobile(no.getText().toString())){
                                r.setEnabled(true);
                            }else{
                                r.setEnabled(false);
                                no.setError("Invalid Mobile No");
                            }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

            r.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email=id.getText().toString().trim();
                    String password=pwd.getText().toString();
                 String phone=no.getText().toString();
                        String da=date.getText().toString();
                        String  userName=name.getText().toString();
                    if(!date.getText().toString().trim().equals("")&&!id.getText().toString().trim().equals("")&&
                            !name.getText().toString().trim().equals("")&&!no.getText().toString().trim().equals("")&&!pwd.getText().toString().trim().equals("")&&!cpwd.getText().toString().trim().equals("")) {

                        if (password.length() < 6) {
                            pwd.setError("Password must be >= 6 character");
                        } else {
                            if (!pwd.getText().toString().equals(cpwd.getText().toString())) {
                                cpwd.setError("Enter valid password");
                                // Toast.makeText(getBaseContext(), "Please enter the Correct Password", Toast.LENGTH_LONG).show();
                            } else {
                                //REGISTER USER IN FIREBASE
                                fauth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            uid=fauth.getCurrentUser().getUid();

                                            if (student.isChecked()) {
                                                String type=student.getText().toString();
                                                rid = g.getCheckedRadioButtonId();
                                                gen = (RadioButton) findViewById(rid);
                                                String gender=gen.getText().toString();
                                                String branch= s.getSelectedItem().toString();
                                                String course=c.getSelectedItem().toString();
                                                Student info1 = new Student(userName, email, gender, phone, da, branch, course, type);
                                                DocumentReference documentReference=fstore.collection("Students").document(uid);
                                                Map<String,Object> user=new HashMap<>();
                                                user.put("UName",userName);
                                                user.put("Email",email);
                                                user.put("Gender",gender);
                                                user.put("Phone_no",phone);
                                                user.put("Dob",da);
                                                user.put("Branch",branch);
                                                user.put("Course",course);
                                                user.put("Type",type);
                                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Intent i = new Intent(Register.this, Login.class);
                                                        startActivity(i);
                                                    }
                                                });

                                                FirebaseDatabase.getInstance().getReference("student").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                        .setValue(info1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull @NotNull Task<Void> task) {

                                                        Toast.makeText(getBaseContext(), "Registered Successfully", Toast.LENGTH_LONG).show();

                                                    }
                                                });
                                            }
                                            else{
                                                String type=teacher.getText().toString();
                                                rid = g.getCheckedRadioButtonId();
                                                gen = (RadioButton) findViewById(rid);
                                                String gender=gen.getText().toString();
                                                String branch= s.getSelectedItem().toString();
                                                String course=c.getSelectedItem().toString();

                                                User info = new User(userName, email, gender, phone, da, branch, course, type);
                                                DocumentReference documentReference=fstore.collection("Teacher").document(uid);
                                                Map<String,Object> user=new HashMap<>();
                                                user.put("UName",userName);
                                                user.put("Email",email);
                                                user.put("Gender",gender);
                                                user.put("Phone_no",phone);
                                                user.put("Dob",da);
                                                user.put("Branch",branch);
                                                user.put("Course",course);
                                                user.put("Type",type);
                                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                    }
                                                });
                                                FirebaseDatabase.getInstance().getReference("teacher").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                        .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull @NotNull Task<Void> task) {

                                                        Toast.makeText(getBaseContext(), "Registered Successfully", Toast.LENGTH_LONG).show();
                                                        Intent i = new Intent(Register.this, Login.class);
                                                        startActivity(i);
                                                            finish();
                                                    }
                                                });

                                            }
                                        }
                                        else {
                                            Toast.makeText(getBaseContext(), "Error ! "+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            } } }
                    else{
                            Toast.makeText(getBaseContext(), "Please Enter all fields...", Toast.LENGTH_LONG).show();
                        }
                    }
            });
    }
    private void spinnerSpecification(){
       ArrayAdapter<String> cd1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.Twelth));
       ArrayAdapter<String> cd = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.Tenth));
       ArrayAdapter<String> cd2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.Btech));
       ArrayAdapter<String> cd5 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.UPSC));
       ArrayAdapter<String> cd6 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.iit_jam));

       c.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                   @Override
                   public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String item[]=getResources().getStringArray(R.array.course);
                        text=item[position];
                if(position==0){
                               cd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                               s.setAdapter(cd);
                           }
                            if(position==1) {
                               cd1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                               s.setAdapter(cd1);
                           }
                           if(position==2) {
                               cd2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                               s.setAdapter(cd2);

                           }
                           if(position==3) {
                               cd5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                               s.setAdapter(cd5);

                           }
                            if(position==4) {
                               cd2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                               s.setAdapter(cd2);
                           }
                            if(position==5) {
                               cd6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                               s.setAdapter(cd6);
                           }
                   }
           @Override
           public void onNothingSelected(AdapterView<?> parent) {
           }
           });
       }
       boolean validateMobile(String input){
           Pattern p=Pattern.compile("[6-9][0-9]{9}");
           Matcher m=p.matcher(input);
           return m.matches();
       }

    }



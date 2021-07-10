package com.example.vidhyalay;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.util.Properties;
import java.util.Random;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ForgetPassword extends AppCompatActivity {

        static int randomNo;
    EditText mailId;
    Button reset1;
    FirebaseAuth auth1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        reset1=findViewById(R.id.reset);
                mailId=findViewById(R.id.id);
        auth1=FirebaseAuth.getInstance();
        reset1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPass();
              //  Toast.makeText(getApplicationContext(),"Code send Successfully",Toast.LENGTH_LONG).show();



            }
        });

    }

    private void resetPass() {
        String email=mailId.getText().toString().trim();
        if(email.isEmpty()){
            mailId.setError("Email is required");
            mailId.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mailId.setError("Please provide valid email");
            return;
        }
        auth1.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
           if(task.isSuccessful()){
               Toast.makeText(ForgetPassword.this,"Check your email to Reset your password!",Toast.LENGTH_LONG).show();
               Intent i=new Intent(ForgetPassword.this,Login.class);
               startActivity(i);
           }
           else{
               Toast.makeText(ForgetPassword.this,"Try again something wrong happened!",Toast.LENGTH_LONG).show();
           }
            }
        });
    }
/*private  class Pass extends AsyncTask<Void,Void,Void>{

    @Override
    protected Void doInBackground(Void... voids) {
        Random ra=new Random();
        randomNo=ra.nextInt(99999);
        String host="smtp.gmail.com";
        String user="vidhyalaya1@gmail.com";
        String pass="98765@dfr";
        String to=mailId.getText().toString();
        String subject="Reseting Code";
        String msg="Your reset code is"+randomNo;
        boolean session=false;
        Properties pros=System.getProperties();

        pros.put("mail.smtp.starttls.enable","true");
        pros.put("mail.smtp.host","host");
        pros.put("mail.smtp.port","587");
        pros.put("mail.smtp.auth","true");
    //    pros.put("");
        pros.put("mail.smtp.starttls.required","true");
       // java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.provider());
        Session mailSession=Session.getDefaultInstance(pros,new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(user,pass);
            }
        });
        try{
            Message mes=new MimeMessage(mailSession);
            mes.setFrom(new InternetAddress(user));
            Address add=new InternetAddress(to);
            mes.setRecipient(Message.RecipientType.TO,add );
            mes.setSubject(subject);
            mes.setText(msg);
            Transport tran=mailSession.getTransport("smtp");
            tran.connect(host,user,pass);
            tran.sendMessage(mes,mes.getAllRecipients());
            tran.close();


        }
        catch (Exception e){
            throw new RuntimeException(e);
        }


        return null;
    }
} */

}

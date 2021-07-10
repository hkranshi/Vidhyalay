package com.example.vidhyalay.ui.Upload;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.vidhyalay.Login;
import com.example.vidhyalay.Member;
import com.example.vidhyalay.R;
import com.example.vidhyalay.Register;
import com.example.vidhyalay.databinding.FragmentUploadBinding;
import com.example.vidhyalay.ui.account.accountFragment;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;


public class Upload extends Fragment {
    private static final int PICK_VIDEO =1 ;
    FirebaseUser user;
    StorageReference storageReference;
    FirebaseFirestore fstore;
    FirebaseAuth fb;
    VideoView videoView;
    Button show,choose,upload;
    ProgressBar progressBar;
    EditText title;
    private Uri vurl;
    UploadTask uploadTask;
    MediaController mediaController;
    DatabaseReference databaseReference;
    Member member;
        String uid;
    static String name,branch,course;
    public static void putArguments(Bundle b) {
        name =b.getString("uname");
        branch=b.getString("branch");
        course=b.getString("course");
        System.out.println(branch);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_upload, container, false);
        fb=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
    databaseReference= FirebaseDatabase.getInstance().getReference(course+" "+branch);
            member=new Member();
        storageReference= FirebaseStorage.getInstance().getReference("Video");
        videoView=v.findViewById(R.id.videoView);
        choose=v.findViewById(R.id.choose);
        upload=v.findViewById(R.id.upload);
        progressBar=v.findViewById(R.id.progress);
        title=v.findViewById(R.id.vname);
        mediaController=new MediaController(getContext());
        videoView.setMediaController(mediaController);
        videoView.start();
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseVideo( v);
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadVideo(v);
            }
        });
        uid=fb.getCurrentUser().getUid();


System.out.println(branch);


    return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_VIDEO || resultCode == Activity.RESULT_OK || data!= null||data.getData()!=null){
          try {
              vurl = data.getData();
              videoView.setVideoURI(vurl);
          }catch (Exception e){}
        }
    }


    public  void chooseVideo(View view){
        Intent intent=new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_VIDEO);
    }
    public  void showVideo(View view){

    }
    private void uploadVideo(View view) {
        String t = title.getText().toString();
        String ur=vurl.toString();
        if (t.trim().equals("")) {
            title.setError("Please give a title");
        }
        if(ur.equals("")){
            Toast.makeText(getActivity(),"Choose the Vedio",Toast.LENGTH_LONG).show();
        }
        if(vurl!=null||!TextUtils.isEmpty(t)){
            progressBar.setVisibility(View.VISIBLE);
            final StorageReference reference=storageReference.child(System.currentTimeMillis()+"."+getExt(vurl));
            uploadTask=reference.putFile(vurl);
            Task<Uri> uriTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull @NotNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();
                    }
                    return  reference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Uri> task) {
                    if(task.isSuccessful()) {
                        Uri download =task.getResult();
                        String s=download.toString();
                        String sea=t.toLowerCase();
                        progressBar.setVisibility(View.INVISIBLE);
                        member.setUname(name);
                        member.setTitle(t);
                        member.setVedioUrl(s);
                        member.setSearch(sea);
                       String i=databaseReference.push().getKey();
                       databaseReference.child(i).setValue(member);

                        Toast.makeText(getActivity(),"Video Uploaded",Toast.LENGTH_LONG).show();
                      /*  DocumentReference documentReference = fstore.collection(course+" "+branch).document(System.currentTimeMillis()+"");
                        Map<String, Object> user = new HashMap<>();
                        user.put("Title", t);
                        user.put("VedioUrl",s);
                        user.put("Uname",name);
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getActivity(), "Vedio Uploaded", Toast.LENGTH_LONG).show();
                            }
                        });*/
                    }else {
                        Toast.makeText(getActivity(),"Failed",Toast.LENGTH_LONG).show();
                    }
                }
            });

        }

    }

    private String getExt(Uri vurl) {
        ContentResolver contentResolver=getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(vurl));

    }
}
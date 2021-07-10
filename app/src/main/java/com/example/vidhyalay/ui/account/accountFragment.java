package com.example.vidhyalay.ui.account;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.vidhyalay.EditChoice;
import com.example.vidhyalay.Login;
import com.example.vidhyalay.MainAct;
import com.example.vidhyalay.R;
//import com.example.vidhyalay.databinding.FragmentAccountBinding;
import com.example.vidhyalay.SettingsActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link accountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class accountFragment extends Fragment {
          //  private FragmentAccountBinding binding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public accountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment accountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static accountFragment newInstance(String param1, String param2) {
        accountFragment fragment = new accountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    static String name1,email;
    public static void putArguments(Bundle b) {
       name1 =b.getString("uname");
       email=b.getString("id");
        //System.out.println(st);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
          //  System.out.println(mParam1+" "+mParam2);
         //   name.setText(mParam1);
          //  idDisplay.setText(mParam2);
        }
    }
    FloatingActionButton fbutton;
    ImageView profilePic;
    FirebaseAuth fb;
    Button changePass,edit,log;
        TextView Schedule,current_affair,sett;
            TextView name,idDisplay;
         FirebaseUser user;
         StorageReference storageReference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_account, container, false);
        changePass=(Button) v.findViewById(R.id.button5);
        edit=v.findViewById(R.id.editProfilr);
       // log=v.findViewById(R.id.lout);
        profilePic=v.findViewById(R.id.circleImage);
        Schedule=v.findViewById(R.id.uytrsfdg);
        current_affair=v.findViewById(R.id.rdghg);
        sett=v.findViewById(R.id.setting);
        name=v.findViewById(R.id.name);
        fbutton=v.findViewById(R.id.floatingActionButton3);
        idDisplay=v.findViewById(R.id.idDisplay);
        fb=FirebaseAuth.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();
        StorageReference prof=storageReference.child("users/"+ fb.getCurrentUser().getUid()+"/profile.jpg");
        prof.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profilePic);
            }
        });
            name.setText(name1);
            idDisplay.setText(email);
         user=fb.getCurrentUser();
       /* String message = getArguments().getString("message");
        System.out.println(message);
        name.setText(message);*/
        fbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,1000);
            }
        });

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final  EditText changeP=new EditText(v.getContext());
                final AlertDialog.Builder passwordReset=new AlertDialog.Builder(v.getContext());
                passwordReset.setTitle("Change Password");
                passwordReset.setMessage("Enter password >6 Character long");
                passwordReset.setView(changeP);
                passwordReset.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newPass=changeP.getText().toString();
                        user.updatePassword(newPass).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getActivity(),"Password set Sucessfully",Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Toast.makeText(getActivity(),"Password reset Failure",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
                passwordReset.create().show();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(), EditChoice.class);
                startActivity(i);
            }
        });

            Schedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(),"No Schedule available yet !",Toast.LENGTH_LONG).show();
                }
            });
            sett.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(getActivity(), SettingsActivity.class);
                    startActivity(i);
                }
            });
            current_affair.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri=Uri.parse("https://www.gktoday.in/current-affairs/");
                    startActivity(new Intent(Intent.ACTION_VIEW,uri));
                }
            });


        return v;


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000){
            if(resultCode== Activity.RESULT_OK){
                Uri image=data.getData();

               // profilePic.setImageURI(image);
                uploadImage(image);
            }
        }
    }

    private void uploadImage(Uri imageuri) {
            StorageReference file=storageReference.child("users/"+fb.getCurrentUser().getUid()+"/profile.jpg");
                    file.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Picasso.get().load(imageuri).into(profilePic);
                       Toast.makeText(getActivity(),"Image Upload",Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Toast.makeText(getActivity(),"Failed",Toast.LENGTH_LONG).show();
                        }
                    });
    }
}
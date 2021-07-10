package com.example.vidhyalay.ui.home1;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.transition.Transition;
import com.example.vidhyalay.AdapterPost;
import com.example.vidhyalay.FullScreen;
import com.example.vidhyalay.MainAct;
import com.example.vidhyalay.Member;
import com.example.vidhyalay.NotesActivity;
import com.example.vidhyalay.Post;
import com.example.vidhyalay.Post_Member;
import com.example.vidhyalay.PreviousPaperActivity2;
import com.example.vidhyalay.R;
import com.example.vidhyalay.ViewAdapter;
import com.example.vidhyalay.databinding.FragmentHome1Binding;
import com.example.vidhyalay.ui.home.HomeFragment;
import com.example.vidhyalay.ui.notes.notesFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class Home extends Fragment {
   // public static String branch;
    private FragmentHome1Binding binding;
    public Home() {
        // Required empty public constructor
    }
   public static String branch,course,name2;
private View v;
    RecyclerView recyclerView,postrecyclerview;
    ArrayList<Member> datalist;
    FirebaseAuth firebaseAuth;
    String url,name1;
   // FirebaseFirestore firebaseFirestore;
  //  CollectionReference databaseReference;
    DatabaseReference databaseReference1;
   static DatabaseReference databaseReference;
    FirebaseDatabase database;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static void putArguments(Bundle b1) {
        branch=b1.getString("branch");
        course=b1.getString("course");
        name2=b1.getString("name");
        System.out.println(branch+""+course);
    }

    @Override
    public void onStart() {
        super.onStart();
        myadapter.startListening();
        FirebaseRecyclerOptions<Member> options=new FirebaseRecyclerOptions.Builder<Member>().setQuery(databaseReference1, Member.class).build();
        FirebaseRecyclerAdapter<Member,ViewAdapter> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Member, ViewAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull @NotNull ViewAdapter holder, int position, @NonNull @NotNull Member model) {
                String uid=getRef(position).getKey();

                databaseReference.child(uid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if(snapshot.hasChild("vedioUrl")){
                            String vurl=snapshot.child("vedioUrl").getValue().toString();
                            String name=snapshot.child("uname").getValue().toString();
                            String title=snapshot.child("title").getValue().toString();
                            System.out.println(vurl+""+name+""+title);
                            holder.setExo(getActivity().getApplication(),name,title,vurl);
                            holder.setOnClicklistener(new ViewAdapter.Clicklistener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    name1=getItem(position).getTitle();
                                    url=getItem(position).getVedioUrl();
                                    Intent i =new Intent(getActivity(), FullScreen.class);
                                   i.putExtra("nam",name1);
                                   i.putExtra("url",url);
                                    startActivity(i);

                                }
                                @Override
                                public void onItemLongClick(View view, int position) {
                                        //not for Student
                                }
                            });
                        }
                        else{
                            String name=snapshot.child("uname").getValue().toString();
                            String title=snapshot.child("title").getValue().toString();
                            holder.textView.setText(name);
                            holder.textView1.setText(title);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) { }
                });
            }
            @NonNull
            @NotNull
            @Override
            public ViewAdapter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.lecture,parent,false);
                ViewAdapter viewAdapter=new ViewAdapter(view);
                return viewAdapter;
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
        System.out.println(branch+" "+course);
    }
ViewAdapter viewAdapter;
    Query query;
   Button post,videonotes,previouspaper;
   public static Button notes;
    AdapterPost myadapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         v=inflater.inflate(R.layout.fragment_home1, container, false);
        recyclerView=v.findViewById(R.id.recycle);
        post=v.findViewById(R.id.button11);
        videonotes=v.findViewById(R.id.button14);
        previouspaper=v.findViewById(R.id.button13);
        notes=v.findViewById(R.id.button12);
        datalist=new ArrayList<>();
        firebaseAuth=FirebaseAuth.getInstance();
        postrecyclerview=v.findViewById(R.id.postrecyclerView);
            notes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
             //   Intent i=new Intent(getActivity(), MainAct.)
                }
            });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        database=FirebaseDatabase.getInstance();
        databaseReference1=database.getReference(course+" "+branch);
        databaseReference=FirebaseDatabase.getInstance().getReference(course+" "+branch);
        postrecyclerview.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        FirebaseRecyclerOptions<Post_Member> options=
                new FirebaseRecyclerOptions.Builder<Post_Member>()
                        .setQuery(FirebaseDatabase.getInstance()
                                .getReference().child("Post"),Post_Member.class).build();
        myadapter=new AdapterPost(options);
        postrecyclerview.setAdapter(myadapter);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(), Post.class);
                startActivity(i);
            }
        });
//setQuery onStart()
        videonotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((course.equals("B Tech") && branch.equals("Mechanical Engineering")) || (course.equals("Gate") && branch.equals("Mechanical Engineering"))){
                    Uri uri = Uri.parse("https://nptel.ac.in/courses/112/103/112103108/");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }
                    if ((course.equals("B Tech") && branch.equals("Civil Engineering") )|| (course.equals("Gate") && branch.equals("Civil Engineering"))){
                        Uri uri = Uri.parse("https://civilblog.org/civil-engineering-video-lectures/");
                        startActivity(new Intent(Intent.ACTION_VIEW, uri));
                    }
                        if ((course.equals("B Tech") && branch.equals("Electronics and Communication Engineering") )|| (course.equals("Gate") && branch.equals("Electronics and Communication Engineering"))){
                            Uri uri = Uri.parse("https://nptel.ac.in/courses/117/102/117102059/");
                            startActivity(new Intent(Intent.ACTION_VIEW, uri));
                        }
                            if ((course.equals("B Tech") && branch.equals("Computer Science Engineering")) || (course.equals("Gate") && branch.equals("Computer Science Engineering"))) {
                                Uri uri = Uri.parse("https://ocw.mit.edu/courses/electrical-engineering-and-computer-science/6-00-introduction-to-computer-science-and-programming-fall-2008/video-lectures/");
                                startActivity(new Intent(Intent.ACTION_VIEW, uri));
                            }
                            if(course.equals("IIT JAM")){
                                if(branch.equals("Mathematics")){
                                    Uri uri = Uri.parse("https://nptel.ac.in/courses/111/104/111104145/");
                                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                                }
                                if(branch.equals("Chemistry")){
                                    Uri uri = Uri.parse("https://nptel.ac.in/courses/104/104/104104080/");
                                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                                }
                                if(branch.equals("Physics")){
                                    Uri uri = Uri.parse("https://nptel.ac.in/courses/115/106/115106123/");
                                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                                }
                            }
                if(course.equals("12th (Secondary School)")){
                    if(branch.equals("PCM")){
                        Uri uri = Uri.parse("https://in.video.search.yahoo.com/search/video;_ylt=AwrxiDccgOVgxHEAYzq7HAx.;_ylu=Y29sbwNzZzMEcG9zAzEEdnRpZAMEc2VjA3Nj?p=class+12+pcm+lectures&fr=mcafee");
                        startActivity(new Intent(Intent.ACTION_VIEW, uri));
                    }
                    if(branch.equals("PCB")){
                        Uri uri = Uri.parse("https://in.video.search.yahoo.com/search/video;_ylt=AwrPhx59gOVgHCkAkRrmHAx.;_ylu=c2VjA3NlYXJjaAR2dGlkAw--;_ylc=X1MDMjExNDcyMzA0NgRfcgMyBGFjdG4DY2xrBGNzcmNwdmlkA09LMUdpekV3TGpMUy55RVBZRWp6aVFDYk1qUXdPUUFBQUFEcjNrNlIEZnIDbWNhZmVlBGZyMgNzYS1ncARncHJpZANxVFlmQXVBY1QuZU10cmVxSkQ2YzFBBG5fcnNsdAMwBG5fc3VnZwMxBG9yaWdpbgNpbi52aWRlby5zZWFyY2gueWFob28uY29tBHBvcwMwBHBxc3RyAwRwcXN0cmwDBHFzdHJsAzUzBHF1ZXJ5A2NsYXNzJTIwMTIlMjBwaHlzaWNzJTIwY2hlbWlzdHJ5JTIwYmlvbG9neSUyMGxlY3R1cmVzBHRfc3RtcAMxNjI1NjUzMzkz?p=class+12+physics+chemistry+biology+lectures&ei=UTF-8&fr2=p%3As%2Cv%3Av%2Cm%3Asa&fr=mcafee");
                        startActivity(new Intent(Intent.ACTION_VIEW, uri));
                    }
                    if(branch.equals("COMMERCE")){
                        Uri uri = Uri.parse("https://in.video.search.yahoo.com/search/video;_ylt=AwrPhtuRgOVgGAMAoAbmHAx.;_ylu=c2VjA3NlYXJjaAR2dGlkAw--;_ylc=X1MDMjExNDcyMzA0NgRfcgMyBGFjdG4DY2xrBGNzcmNwdmlkA3hZNjYxekV3TGpMUy55RVBZRWp6aVFBa01qUXdPUUFBQUFEdERyTXMEZnIDbWNhZmVlBGZyMgNzYS1ncARncHJpZAN4anc2azdoSVFfZUYyaTVRT2lKaTZBBG5fcnNsdAM2MARuX3N1Z2cDMARvcmlnaW4DaW4udmlkZW8uc2VhcmNoLnlhaG9vLmNvbQRwb3MDMARwcXN0cgMEcHFzdHJsAwRxc3RybAMzMgRxdWVyeQNjbGFzcyUyMDEyJTIwY29tbWVyY2UlMjBsZWN0dXJlcwR0X3N0bXADMTYyNTY1MzQ0Nw--?p=class+12+commerce+lectures&ei=UTF-8&fr2=p%3As%2Cv%3Av%2Cm%3Asa&fr=mcafee");
                        startActivity(new Intent(Intent.ACTION_VIEW, uri));
                    }
                }
                if(course.equals("Upsc")){
                    Uri uri = Uri.parse("https://study91.co.in/free-online-video-class/exam/all/upsc");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }
                if(course.equals("10th (Matriculation)")){
                    if(branch.equals("Science(English Medium)")){
                        Uri uri = Uri.parse("https://www.topperlearning.com/cbse-class-10-videos");
                        startActivity(new Intent(Intent.ACTION_VIEW, uri));
                    }
                    if(branch.equals("Arts")){
                        Uri uri = Uri.parse("https://in.video.search.yahoo.com/search/video;_ylt=AwrPhSvJf.VgNzQAkqm7HAx.;_ylu=Y29sbwNzZzMEcG9zAzEEdnRpZAMEc2VjA3Nj?p=class+10+home+science+video+lectures&fr=mcafee");
                        startActivity(new Intent(Intent.ACTION_VIEW, uri));
                    }
                    if(branch.equals("(Science)Hindi Medium")){
                        Uri uri = Uri.parse("https://www.youtube.com/watch?v=nlptK8-qwjE");
                        startActivity(new Intent(Intent.ACTION_VIEW, uri));
                    }

                }


            }
        });
        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent i=new Intent(getActivity(), NotesActivity.class);
            startActivity(i);
            }
        });
        previouspaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(), PreviousPaperActivity2.class);
                startActivity(i);
            }
        });


        return  v;
    }

    public static class ViewAdapter extends RecyclerView.ViewHolder {
        SimpleExoPlayer simpleExoPlayer;
        PlayerView playerView;
        TextView textView, textView1;
        View view;
        public ViewAdapter(@NonNull @NotNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   mClick.onItemClick(view,getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mClick.onItemLongClick(view,getAdapterPosition());
                    return false;
                }
            });
            view = itemView;
            textView = itemView.findViewById(R.id.tit);
            textView1 = itemView.findViewById(R.id.titl);
            playerView = itemView.findViewById(R.id.exo);
        }

        public void setExo(Application application, String name, String title, String videoUrl) {
            textView.setText("By "+name);
            textView1.setText(title);
            try {
                BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter.Builder(application).build();
                TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
                simpleExoPlayer = (SimpleExoPlayer) ExoPlayerFactory.newSimpleInstance(application);
                Uri uri = Uri.parse(videoUrl);
                DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("Video");
                ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                MediaSource mediaSource = new ExtractorMediaSource(uri, dataSourceFactory, extractorsFactory, null, null);
                playerView.setPlayer(simpleExoPlayer);
               // to stop multi play simpleExoPlayer.prepare(mediaSource);
                simpleExoPlayer.setPlayWhenReady(false);
            } catch (Exception e) {
                Log.e("VideoHolder", "exoPlayer error" + e.toString());
            }
        }
        private ViewAdapter.Clicklistener mClick;
        public interface Clicklistener{
            void onItemClick(View view,int position);
                void onItemLongClick(View view,int position);

        }
        public void setOnClicklistener(ViewAdapter.Clicklistener clicklistener){
            mClick=clicklistener;
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu,menu);
        MenuItem menuItem=menu.findItem(R.id.search);
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               System.out.println("anshi");
                search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
              search(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void search(String search){
        String query=search.toLowerCase();

        com.google.firebase.database.Query firebase=databaseReference.orderByChild("search").startAt(query).endAt(query+"\uf8ff");
        FirebaseRecyclerOptions<Member> options=new FirebaseRecyclerOptions.Builder<Member>().setQuery(firebase, Member.class).build();
        FirebaseRecyclerAdapter<Member, Home.ViewAdapter> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Member, Home.ViewAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull @NotNull Home.ViewAdapter holder, int position, @NonNull @NotNull Member model) {
                String uid = getRef(position).getKey();
                holder.setExo(getActivity().getApplication(), model.getUname(), model.getTitle(), model.getVedioUrl());
                holder.setOnClicklistener(new ViewAdapter.Clicklistener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        name1=getItem(position).getUname();
                        url=getItem(position).getVedioUrl();
                        Intent i =new Intent(getActivity(), FullScreen.class);
                        i.putExtra("nam",name1);
                        i.putExtra("url",url);
                        startActivity(i);

                    }
                    @Override
                    public void onItemLongClick(View view, int position) {
                        //not for Student
                    }
                });

            }
            @NonNull
            @NotNull
            @Override
            public Home.ViewAdapter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.lecture,parent,false);
                Home.ViewAdapter viewAdapter=new Home.ViewAdapter(view);
                return viewAdapter;
            }
        };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        myadapter.stopListening();
    }
}
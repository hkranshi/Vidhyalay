package com.example.vidhyalay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.auth.data.model.IntentRequiredException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class FullScreen extends AppCompatActivity {
TextView name;
private SimpleExoPlayer simpleExoPlayer;
private PlayerView playerView;
private String url;
private  boolean playwhenready=false;
private int currentWindow=0;
private  long playbackposition=0;
boolean fullscreen=false;
ImageView fullscreenButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Fullscreen");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        playerView=findViewById(R.id.exo_fullscreen);
        name=findViewById(R.id.fullscreen);
        fullscreenButton=playerView.findViewById(R.id.exo_fullscreen_icon);
        Intent intent=getIntent();
        url=intent.getExtras().getString("url");
        String title=intent.getExtras().getString("nam");
        name.setText(title);
        fullscreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fullscreen){
                    fullscreenButton.setImageDrawable(ContextCompat.getDrawable(FullScreen.this,R.drawable.ic_baseline_fullscreen_24));
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    if(getSupportActionBar()!=null){
                        getSupportActionBar().show();
                    }
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    LinearLayout.LayoutParams params=(LinearLayout.LayoutParams) playerView.getLayoutParams();
                    params.width=params.MATCH_PARENT;
                    params.height=(int)(200* getApplicationContext().getResources().getDisplayMetrics().density);
                    playerView.setLayoutParams(params);
                    fullscreen=false;
                   // name.setVisibility(View.VISIBLE);
                }
                else{
                    //name.setVisibility(View.INVISIBLE);
                    fullscreenButton.setImageDrawable(ContextCompat.getDrawable(FullScreen.this,R.drawable.ic_baseline_fullscreen_exit_24));
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                    if(getSupportActionBar()!=null){
                        getSupportActionBar().hide();
                    }

                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    LinearLayout.LayoutParams params=(LinearLayout.LayoutParams) playerView.getLayoutParams();
                    params.width= params.MATCH_PARENT;
                    params.height=params.MATCH_PARENT;
                    playerView.setLayoutParams(params);
                    fullscreen=true;
                }
            }
        });



    }
    private MediaSource builtMedia(Uri uri){
        DataSource.Factory datasourceFactory =new DefaultHttpDataSourceFactory("Video");
        return new ProgressiveMediaSource.Factory(datasourceFactory).createMediaSource(uri);
    }
    private void initializePlayer(){
        simpleExoPlayer= ExoPlayerFactory.newSimpleInstance(this);
        playerView.setPlayer(simpleExoPlayer);
        Uri uri=Uri.parse(url);
        MediaSource mediaSource=builtMedia(uri);
        simpleExoPlayer.setPlayWhenReady(playwhenready);
        simpleExoPlayer.seekTo(currentWindow,playbackposition);
        simpleExoPlayer.prepare(mediaSource,false,false);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(Util.SDK_INT>=26){
            initializePlayer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Util.SDK_INT>=26|| simpleExoPlayer==null){
           // initializePlayer();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(Util.SDK_INT>26){
            releasePlayer();
            // initializePlayer();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(Util.SDK_INT>=26){
            releasePlayer();
            // initializePlayer();
        }
    }

    private void releasePlayer() {
        if(simpleExoPlayer!=null){
            playwhenready=simpleExoPlayer.getPlayWhenReady();
            playbackposition=simpleExoPlayer.getCurrentPosition();
            currentWindow=simpleExoPlayer.getCurrentWindowIndex();
            simpleExoPlayer=null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        simpleExoPlayer.stop();
        releasePlayer();
        final Intent i=new Intent();
        setResult(RESULT_OK,i);
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
          if(id==android.R.id.home){
              simpleExoPlayer.stop();
              releasePlayer();
              final Intent i=new Intent();
              setResult(RESULT_OK,i);
              finish();
         //   Intent i1=new Intent(FullScreen.this,MainAct.class);
           // startActivity(i1);
            //finish();

        }
    /*  if(id==android.R.id.home){
            Intent i=new Intent(SettingsActivity.this,Teach.class);
            startActivity(i);
            finish();
        }*/

        return super.onOptionsItemSelected(item);
    }

}
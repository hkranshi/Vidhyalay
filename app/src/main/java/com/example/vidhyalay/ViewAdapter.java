package com.example.vidhyalay;
import android.app.Application;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vidhyalay.ui.home1.Home;
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
import org.jetbrains.annotations.NotNull;
    public class ViewAdapter extends RecyclerView.ViewHolder {
        SimpleExoPlayer simpleExoPlayer;
        PlayerView playerView;
            TextView textView,textView1;
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

            view=itemView;
            textView = itemView.findViewById(R.id.tit);
            textView1 = itemView.findViewById(R.id.titl);
            playerView = itemView.findViewById(R.id.exo);
        }

        public void setExo(Application application, String name, String title, String videoUrl) {

            textView.setText(name);
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
                simpleExoPlayer.prepare(mediaSource);
                simpleExoPlayer.setPlayWhenReady(false);
            } catch (Exception e) {
                Log.e("VideoHolder", "exoPlayer error" + e.toString());
            }
        }
        private Home.ViewAdapter.Clicklistener mClick;
        public interface Clicklistener{
            void onItemClick(View view,int position);
            void onItemLongClick(View view,int position);

        }
        public void setOnClicklistener(Home.ViewAdapter.Clicklistener clicklistener){
            mClick=clicklistener;
        }
    }

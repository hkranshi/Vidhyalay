package com.example.vidhyalay;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.jetbrains.annotations.NotNull;

public class AdapterPost extends FirebaseRecyclerAdapter<Post_Member,AdapterPost.myViewholder> {

    public AdapterPost(@NonNull @NotNull FirebaseRecyclerOptions<Post_Member> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull myViewholder holder, int position, @NonNull @NotNull Post_Member model) {
        holder.title.setText(model.getTitle());
        holder.name.setText(model.getName());
        String s=model.getImageUri();
        System.out.println(s);
        Glide.with(holder.img.getContext()).load(model.getImageUri()).into(holder.img);
    }
    @NonNull
    @NotNull
    @Override
    public myViewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.postrecycler,parent,false);
        return new myViewholder(view);
    }

    class myViewholder extends RecyclerView.ViewHolder{
ImageView img;
TextView name,title;
        public myViewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            img=(ImageView)itemView.findViewById(R.id.postimage);
            name=(TextView)itemView.findViewById(R.id.textname);
            title=(TextView)itemView.findViewById(R.id.texttitle);

        }
    }
}

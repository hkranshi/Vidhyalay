package com.example.vidhyalay;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class NotesAdapter extends  RecyclerView.Adapter<NotesAdapter.myViewHolder>{
    ArrayList<Notes> datalist;

    public NotesAdapter(ArrayList<Notes> datalist) {
        this.datalist = datalist;
    }
    @NonNull
    @NotNull
    @Override
    public NotesAdapter.myViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notes,parent,false);
        return new NotesAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NotesAdapter.myViewHolder holder, int position) {
        holder.title.setText(datalist.get(position).getTitle());
        //   System.out.println(.getName());
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(holder.img.getContext(),NotesPdf.class);
                i.putExtra("title",datalist.get(position).getTitle());
                i.putExtra("url",datalist.get(position).getUrl());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.img.getContext().startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }
    class myViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView title;
        public myViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.imageViewnotes);
            title=itemView.findViewById(R.id.namet);
        }
    }

}

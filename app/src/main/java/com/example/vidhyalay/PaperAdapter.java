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

public class PaperAdapter extends RecyclerView.Adapter<PaperAdapter.myViewHolder> {
    ArrayList<Paper> datalist;

    public PaperAdapter(ArrayList<Paper> datalist) {
        this.datalist = datalist;
    }

    @NonNull
    @NotNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.paper,parent,false);
        return new PaperAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull myViewHolder holder, int position) {
        holder.title.setText(datalist.get(position).getText());
        //   System.out.println(.getName());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(holder.title.getContext(),PreviousPdf.class);
                i.putExtra("title",datalist.get(position).getText());
                i.putExtra("url",datalist.get(position).getUrl());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.title.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }
    class myViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        public myViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.paperp);
        }
    }
}

package com.example.vidhyalay;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.myViewHolder> {
    ArrayList<Book> datalist;

    public BookAdapter(ArrayList<Book> datalist) {
        this.datalist = datalist;
    }

    @NonNull
    @NotNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.book,parent,false);
        return new BookAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull myViewHolder holder, int position) {
        holder.title.setText(datalist.get(position).getName());
     //   System.out.println(.getName());
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(holder.img.getContext(),BookPdf.class);
                i.putExtra("name",datalist.get(position).getName());
                i.putExtra("url",datalist.get(position).getBookurl());
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
            img=itemView.findViewById(R.id.imageViewbook);
            title=itemView.findViewById(R.id.textView20);
        }
    }
}

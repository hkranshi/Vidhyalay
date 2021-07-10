package com.example.vidhyalay;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.jetbrains.annotations.NotNull;

public class TestAdapter extends FirebaseRecyclerAdapter<test,TestAdapter.myView> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public TestAdapter(@NonNull @NotNull FirebaseRecyclerOptions<test> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull myView holder, int position, @NonNull @NotNull test model) {
      //  System.out.println(model.getText());
        holder.text.setText(model.getText());
        String t=model.getText();
        holder.attempt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(holder.attempt.getContext(),TestSeries.class);
                i.putExtra("title",t);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.attempt.getContext().startActivity(i);
            }
        });
    }

    @NonNull
    @NotNull
    @Override
    public myView onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.test,parent,false);
        return  new myView(v);
    }

    class myView extends RecyclerView.ViewHolder{
            ImageView img;
            TextView text;
            Button attempt;
        public myView(@NonNull @NotNull View itemView) {
            super(itemView);
            img=(ImageView)itemView.findViewById(R.id.titletest);
            text=(TextView)itemView.findViewById(R.id.testtext);
            attempt=(Button)itemView.findViewById(R.id.attempt);
        }
    }
}

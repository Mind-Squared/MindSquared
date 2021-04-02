package com.example.dshbrd;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DoesAdapter extends RecyclerView.Adapter<DoesAdapter.MyViewHolder>{

    Context context;
    ArrayList<MyNeeds> myNeeds;

    public DoesAdapter(Context c, ArrayList<MyNeeds> p) {
        context = c;
        myNeeds = p;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_does,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        myViewHolder.titledoes.setText(myNeeds.get(i).getTitledoes());
        myViewHolder.descdoes.setText(myNeeds.get(i).getDescdoes());
        myViewHolder.datedoes.setText(myNeeds.get(i).getDatedoes());

        final String getTitleTask = myNeeds.get(i).getTitledoes();
        final String getDescTask = myNeeds.get(i).getDescdoes();
        final String getDateTask = myNeeds.get(i).getDatedoes();
        final String getKeyDoes = myNeeds.get(i).getKeydoes();

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent a = new Intent(context, EditTask.class);

                a.putExtra("titledoes", getTitleTask);
                a.putExtra("descdoes", getDescTask);
                a.putExtra("datedoes", getDateTask);
                a.putExtra("keydoes", getKeyDoes);

                context.startActivity(a);

            }
        });

    }

    @Override
    public int getItemCount() {
        return myNeeds.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titledoes, descdoes, datedoes, keydoes;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            titledoes = (TextView) itemView.findViewById(R.id.titledoes);
            descdoes = (TextView) itemView.findViewById(R.id.descdoes);
            datedoes = (TextView) itemView.findViewById(R.id.datedoes);
        }
    }

}

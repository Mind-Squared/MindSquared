package com.example.dshbrd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ConversatieAdapter extends RecyclerView.Adapter<ConversatieAdapter.ConversatieViewHolder> {
    Context context;
    ArrayList<Mesaj_conversatie>mesaje;
    public ConversatieAdapter(Chat_Conversatie _context , ArrayList<Mesaj_conversatie>_mesaj_converatie_array){
        context = _context ;
        mesaje = _mesaj_converatie_array;
    }
    public void add(Mesaj_conversatie m ){
        mesaje.add(m);
    }
    @NonNull
    @Override
    public ConversatieAdapter.ConversatieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.conversatie_row,parent, false);
        return  new ConversatieViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ConversatieAdapter.ConversatieViewHolder holder, int position) {
        holder.nume.setText(mesaje.get(position).destinatar.firstname + " " + mesaje.get(position).destinatar.lastname);
        holder.mesaj.setText(mesaje.get(position).text);
    }

    @Override
    public int getItemCount() {
        return mesaje.size();
    }

    public class ConversatieViewHolder extends RecyclerView.ViewHolder{
        TextView nume, mesaj;
        public ConversatieViewHolder(@NonNull View itemView) {
            super(itemView);

            nume = itemView.findViewById(R.id.textViewnumedestinatar);
            mesaj = itemView.findViewById(R.id.textViewmesajdestinatar);

        }
    }
}

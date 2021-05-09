package com.example.dshbrd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ConversatieAdapter extends RecyclerView.Adapter<ConversatieAdapter.ConversatieViewHolder> {

    private static final int msg_type_right = 1;
    private static final int msg_type_left = 0;

    Context context;
    ArrayList<Mesaj_conversatie>mesaje;
    FirebaseAuth firebaseAuth;

    public ConversatieAdapter(Chat_Conversatie _context , ArrayList<Mesaj_conversatie>_mesaj_converatie_array){
        context = _context ;
        mesaje = _mesaj_converatie_array;

        firebaseAuth = FirebaseAuth.getInstance();
    }
    public void add(Mesaj_conversatie m ){
        mesaje.add(m);
    }
    @NonNull
    @Override
    public ConversatieAdapter.ConversatieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if ( viewType == msg_type_right ){
            View view = LayoutInflater.from(context).inflate(R.layout.conversatie_row_dreapta, parent, false);
            return new ConversatieAdapter.ConversatieViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(context).inflate(R.layout.conversatie_row_stanga, parent, false);
            return new ConversatieAdapter.ConversatieViewHolder(view);
        }

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

    @Override
    public int getItemViewType(int position) {
        if (mesaje.get(position).destinatar.cod.equals(firebaseAuth.getUid())){
            return msg_type_right;
        }

        else {
            return msg_type_left;
        }
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

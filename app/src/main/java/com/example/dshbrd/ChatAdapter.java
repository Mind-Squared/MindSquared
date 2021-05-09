package com.example.dshbrd;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder>  {

    private OnChatListener mOnChatListener;

    Context context;
    String userID;
    String firstname , lastname;
    String[] cod =new String [100];
    ArrayList<JoinedClass_Chat> clase;
    public ChatAdapter(Context ct , String uid , ArrayList<JoinedClass_Chat> jc , String[] c , OnChatListener ocl1 , String _firstname , String _lastname){
        context = ct;
        userID = uid;
        clase = jc;
        cod = c;
        this.mOnChatListener = ocl1;
        this.firstname = _firstname;
        this.lastname = _lastname;
    }


    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.chat_menu_row, parent , false);
        return new ChatViewHolder(view , mOnChatListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ChatViewHolder holder, int position) {
        holder.nume_conversatie.setText(clase.get(position).nume);
        holder.ultimul_mesaj.setText("Ultimul mesaj");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , Chat_Conversatie.class);
//
                intent.putExtra("CodClasa", clase.get(position).uid);
                intent.putExtra("NumeClasa", clase.get(position).nume);

                intent.putExtra("userID", userID);
                intent.putExtra("userFirstname" , firstname);
                intent.putExtra("userLastname", lastname);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        //Log.d("showData3", Integer.toString(clase.size()));
        return  clase.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        TextView nume_conversatie , ultimul_mesaj;
        OnChatListener onChatListener;
        public ChatViewHolder(@NonNull View itemView, OnChatListener onChatListener){
            super(itemView);

            nume_conversatie = itemView.findViewById(R.id.textViewNume);
            ultimul_mesaj = itemView.findViewById(R.id.textViewMesaj);

        }

        @Override
        public void onClick(View view) {
            onChatListener.OnChatClick(getAdapterPosition());
        }
    }

    public interface  OnChatListener{
        void OnChatClick(int position);
    }
}

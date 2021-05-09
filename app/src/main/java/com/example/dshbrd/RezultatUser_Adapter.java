package com.example.dshbrd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RezultatUser_Adapter extends RecyclerView.Adapter<RezultatUser_Adapter.MyViewHolder>{

    Context context;
    ArrayList<RezultatUser> mRezultatUser;

    public RezultatUser_Adapter(Context c, ArrayList<RezultatUser> p){
        context = c;
        mRezultatUser = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_test_elev, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        myViewHolder.firstname.setText(mRezultatUser.get(position).getFirstname());
        myViewHolder.lastname.setText(mRezultatUser.get(position).getLastname());
        myViewHolder.nrQuestionsSolved.setText(mRezultatUser.get(position).getCorrect_questions_answered().toString());
        myViewHolder.nrQuestionsT.setText(mRezultatUser.get(position).getNrQuestions());

    }

    @Override
    public int getItemCount() {
        return mRezultatUser.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView lastname, firstname, nrQuestionsSolved, nrQuestionsT;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            lastname = (TextView) itemView.findViewById(R.id.lastname_id);
            firstname = (TextView) itemView.findViewById(R.id.firstname_id);
            nrQuestionsSolved = (TextView) itemView.findViewById(R.id.nrQuestionsSolved_id);
            nrQuestionsT = (TextView) itemView.findViewById(R.id.nrQuestionsT_id);

        }
    }

}

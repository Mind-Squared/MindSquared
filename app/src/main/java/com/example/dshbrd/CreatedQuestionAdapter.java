package com.example.dshbrd;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CreatedQuestionAdapter extends RecyclerView.Adapter<CreatedQuestionAdapter.MyViewHolder>{

    Context context;
    ArrayList<CreatedQuestions> mCreatedQuestions;

    public CreatedQuestionAdapter(Context c,  ArrayList<CreatedQuestions> p){
        context = c;
        mCreatedQuestions = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_created_question, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        myViewHolder.titleQuestion.setText(mCreatedQuestions.get(position).getTitleQuestion());



    }

    @Override
    public int getItemCount() {
        return mCreatedQuestions.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titleQuestion;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            titleQuestion = (TextView) itemView.findViewById(R.id.titleCreatedQuestion_id);

        }

    }

}

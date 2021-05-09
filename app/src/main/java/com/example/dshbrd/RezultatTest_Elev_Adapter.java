package com.example.dshbrd;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RezultatTest_Elev_Adapter extends RecyclerView.Adapter<RezultatTest_Elev_Adapter.MyViewHolder> {

    Context context;
    ArrayList<RezultatTest_Elev> mRezultatTest_Elev;

    public  RezultatTest_Elev_Adapter(Context c, ArrayList<RezultatTest_Elev> p){

        context = c;
        mRezultatTest_Elev = p;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_solved_test, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        myViewHolder.solvedTestName.setText(mRezultatTest_Elev.get(position).getTitleTest());
        myViewHolder.nrQuestionsSolved.setText(mRezultatTest_Elev.get(position).getCorrect_questions_answered());
        myViewHolder.nrQuestionsT.setText(mRezultatTest_Elev.get(position).getNrQuestions());

        final String getTest_id = mRezultatTest_Elev.get(position).getTest_id();

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, QuestionsResults.class);
                intent.putExtra("test_id", getTest_id);
                context.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return mRezultatTest_Elev.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView solvedTestName, nrQuestionsSolved, nrQuestionsT;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            solvedTestName = (TextView) itemView.findViewById(R.id.solvedTestName_id);
            nrQuestionsSolved = (TextView) itemView.findViewById(R.id.nrQuestionsSolved_id);
            nrQuestionsT = (TextView) itemView.findViewById(R.id.nrQuestionsT_id);

        }



    }

}

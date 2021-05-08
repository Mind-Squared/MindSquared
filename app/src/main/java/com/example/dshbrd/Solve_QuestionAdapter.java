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

public class Solve_QuestionAdapter extends RecyclerView.Adapter<Solve_QuestionAdapter.MyViewHolder>{

    Context context;
    ArrayList<Solve_Question> mSolve_Question;

    public  Solve_QuestionAdapter(Context c, ArrayList<Solve_Question> p){

        context = c;
        mSolve_Question = p;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_solve_question, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        myViewHolder.solveQuestionText.setText(mSolve_Question.get(position).getTitleQuestion());
//        myViewHolder.answer_A_choice.setText(mSolve_Question.get(position).getAnswer_A());
//        myViewHolder.answer_B_choice.setText(mSolve_Question.get(position).getAnswer_B());
//        myViewHolder.answer_C_choice.setText(mSolve_Question.get(position).getAnswer_C());
//        myViewHolder.answer_D_choice.setText(mSolve_Question.get(position).getAnswer_D());

        final String getSolveQuestionText = mSolve_Question.get(position).getTitleQuestion();
        final String getAnswer_A_choice = mSolve_Question.get(position).getAnswer_A();
        final String getAnswer_B_choice = mSolve_Question.get(position).getAnswer_B();
        final String getAnswer_C_choice = mSolve_Question.get(position).getAnswer_C();
        final String getAnswer_D_choice = mSolve_Question.get(position).getAnswer_D();
        final String getQuestion_id = mSolve_Question.get(position).getQuestion_id();
        final String getTestCreator = mSolve_Question.get(position).getQuestion_creator();
        final String getTest_id = mSolve_Question.get(position).getTest_id();
        final String getAnswer_correct = mSolve_Question.get(position).getAnswer_correct();

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ChoiceAnswer.class);

                Log.d("testa", getTestCreator);

                intent.putExtra("answer_A", getAnswer_A_choice);
                intent.putExtra("answer_B", getAnswer_B_choice);
                intent.putExtra("answer_C", getAnswer_C_choice);
                intent.putExtra("answer_D", getAnswer_D_choice);
                intent.putExtra("titleQuestion", getSolveQuestionText);
                intent.putExtra("question_id", getQuestion_id);
                intent.putExtra("testCreator", getTestCreator);
                intent.putExtra("test_id", getTest_id);
                intent.putExtra("answer_correct", getAnswer_correct);

                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mSolve_Question.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView solveQuestionText, answer_A_choice, answer_B_choice, answer_C_choice, answer_D_choice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            solveQuestionText = (TextView) itemView.findViewById(R.id.solveQuestionText_id);
//            answer_A_choice = (TextView) itemView.findViewById(R.id.answer_A_choice_id);
//            answer_B_choice = (TextView) itemView.findViewById(R.id.answer_B_choice_id);
//            answer_C_choice = (TextView) itemView.findViewById(R.id.answer_C_choice_id);
//            answer_D_choice = (TextView) itemView.findViewById(R.id.answer_D_choice_id);

        }

    }

}

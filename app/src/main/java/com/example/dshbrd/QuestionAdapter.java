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

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.MyViewHolder> {

    Context context;
    ArrayList<Questions> mQuestions;

    public  QuestionAdapter(Context c, ArrayList<Questions> p){
        context = c;
        mQuestions = p;
    }


    @NonNull
    @Override
    public QuestionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_question, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int position) {

        myViewHolder.Question.setText(mQuestions.get(position).getTitleQuestion());

        final String getTitleQuestion = mQuestions.get(position).getTitleQuestion();
        final String getAnswer_A = mQuestions.get(position).getAnswer_A();
        final String getAnswer_B = mQuestions.get(position).getAnswer_B();
        final String getAnswer_C = mQuestions.get(position).getAnswer_C();
        final String getAnswer_D = mQuestions.get(position).getAnswer_D();
        final String getAnswerCorrect = mQuestions.get(position).getAnswer_correct();
        final String getSerialNumber = mQuestions.get(position).getSerial_number();
        final String getQuestion_id = mQuestions.get(position).getQuestion_id();

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditQuestion.class);

                intent.putExtra("titleQuestion", getTitleQuestion);
                intent.putExtra("answer_A", getAnswer_A);
                intent.putExtra("answer_B", getAnswer_B);
                intent.putExtra("answer_C", getAnswer_C);
                intent.putExtra("answer_D", getAnswer_D);
                intent.putExtra("answerCorrect", getAnswerCorrect);
                intent.putExtra("serial_number", getSerialNumber);
                intent.putExtra("question_id", getQuestion_id);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mQuestions.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Question;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Question = (TextView) itemView.findViewById(R.id.Question_id);
        }
    }
}

package com.example.dshbrd;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ResultQuestion_elev_Adapter extends RecyclerView.Adapter<ResultQuestion_elev_Adapter.MyViewHolder>{

    Context context;
    ArrayList<ResultQuestion_elev> mResultQuestion_elev;

    public ResultQuestion_elev_Adapter(Context c, ArrayList<ResultQuestion_elev> p){

        context = c;
        mResultQuestion_elev = p;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_result_question, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        Log.d("testb", position+"");

        myViewHolder.questionTitle.setText(mResultQuestion_elev.get(position).getTitleQuestion());
        myViewHolder.answer_correct.setText(mResultQuestion_elev.get(position).getAnswer_correct());
        myViewHolder.user_answer.setText(mResultQuestion_elev.get(position).getUser_answer());

    }

    @Override
    public int getItemCount() {
        return mResultQuestion_elev.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView questionTitle, user_answer, answer_correct;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            questionTitle = (TextView) itemView.findViewById(R.id.questionTitle_id);
            user_answer = (TextView) itemView.findViewById(R.id.user_answer_id);
            answer_correct = (TextView) itemView.findViewById(R.id.answer_correct_id);

        }
    }

}

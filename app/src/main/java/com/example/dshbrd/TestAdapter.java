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

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.MyViewHolder>{

    Context context;
    ArrayList<Tests> mTests;

    public TestAdapter(Context c, ArrayList<Tests> p){
        context = c;
        mTests = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_test, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        myViewHolder.testTitle.setText(mTests.get(position).getTitleTest());
        myViewHolder.clasaTest.setText(mTests.get(position).getClasaTest());
        myViewHolder.nrQuestions.setText(mTests.get(position).getNrQuestions());

        final String getTitleTest = mTests.get(position).getTitleTest();
        final String getClasaTest = mTests.get(position).getClasaTest();
        final String getNrQuestions = mTests.get(position).getNrQuestions();
        final String getTest_id = mTests.get(position).getTest_id();

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, TestOptions.class);

                intent.putExtra("titleTest", getTitleTest);
                intent.putExtra("clasaTest", getClasaTest);
                intent.putExtra("nrQuestions", getNrQuestions);
                intent.putExtra("test_id", getTest_id);

                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mTests.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView testTitle, clasaTest, nrQuestions;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            testTitle = (TextView) itemView.findViewById(R.id.testTitle_id);
            clasaTest = (TextView) itemView.findViewById(R.id.clasaTest_id);
            nrQuestions = (TextView) itemView.findViewById(R.id.nrQuestions_id);

        }
    }

}

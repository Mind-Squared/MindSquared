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

public class AdapterClassList extends RecyclerView.Adapter<AdapterClassList.HolderClassList> {

    private Context context;
    private ArrayList<ClassInfo> classInfo;

    public AdapterClassList(Context context, ArrayList<ClassInfo> classInfo) {
        this.context = context;
        this.classInfo = classInfo;
    }

    @NonNull
    @Override
    public HolderClassList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_class, parent, false);

        return new HolderClassList(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderClassList holder, int position) {
        //ClassInfo model = classInfo.get(position);
        //String className = model.getClassName();

       // holder.classNameTv.setText(className);

        holder.classNameTv.setText(classInfo.get(position).getClassName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context , CreateTask.class);

                intent.putExtra("CodClasa", classInfo.get(position).entry);
                intent.putExtra("NumeClasa", classInfo.get(position).className);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return classInfo.size();
    }

    class HolderClassList extends RecyclerView.ViewHolder{
        public TextView classNameTv;

        public HolderClassList(@NonNull View itemView) {
            super(itemView);

            classNameTv = itemView.findViewById(R.id.clasNameTv);
        }

        public TextView getClassNameTv() {
            return classNameTv;
        }
    }
}

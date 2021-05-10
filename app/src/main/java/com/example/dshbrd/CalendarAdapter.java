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

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.MyViewHolder> {
    ArrayList<CalendarElement> calendarElement;
    Context context;

    public CalendarAdapter(ArrayList<CalendarElement> calendarElement)
    {
        this.calendarElement = calendarElement;
    }


    @NonNull
    @Override
    public CalendarAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calendar_thing, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarAdapter.MyViewHolder holder, int position) {
        String Title = calendarElement.get(position).getElementTitle();
        String Time = calendarElement.get(position).getElementTime();
        String Date = calendarElement.get(position).getElementDate();

        holder.calendarTime.setText(Time);
        holder.calendarTitle.setText(Title);
        holder.calendarData.setText(Date);
        
    }

    @Override
    public int getItemCount() {
        return calendarElement.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView calendarTitle, calendarTime, calendarData;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            calendarData = itemView.findViewById(R.id.calendardate);
            calendarTitle = itemView.findViewById(R.id.calendartitle);
            calendarTime = itemView.findViewById(R.id.calendartime);
        }
    }
}

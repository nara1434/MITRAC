package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adaptor extends RecyclerView.Adapter<adaptor.MyHolder> {
    Context context;
    ArrayList<subClass> li ;
    LayoutInflater layoutInflater;

    public adaptor(Context context, ArrayList<subClass> li) {
        this.context = context;
        this.li = li;
        layoutInflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.pid.setText(li.get(position).getpId());

    }

    @Override
    public int getItemCount() {
        return li.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        TextView pid;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            //pid = itemView.findViewById(R.id.txt);
        }
    }
}

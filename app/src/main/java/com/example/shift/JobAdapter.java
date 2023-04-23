package com.example.shift;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder> {

    ArrayList<Job> list;

    private Context context;

    private OnClickJobListener jobListener;

    public JobAdapter(ArrayList<Job> list, Context context, OnClickJobListener jobListener){
        this.list = list;
        this.context = context;
        this.jobListener = jobListener;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView roleText;
        public TextView dateText;
        public TextView timeText;
        public TextView payText;

        public CardView card;
        public TextView trainingText;

        public ImageView logoImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            roleText = itemView.findViewById(R.id.role);
            dateText = itemView.findViewById(R.id.date);
            timeText = itemView.findViewById(R.id.time);
            payText = itemView.findViewById(R.id.pay);
            trainingText = itemView.findViewById(R.id.training);
            logoImage = itemView.findViewById(R.id.logoImage);
            card = itemView.findViewById(R.id.cards);


        }

    }

    @androidx.annotation.NonNull
    @Override
    public ViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        View contactView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull ViewHolder holder, int position) {
        Job current = list.get(position);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jobListener.onJobClick(current);
            }
        });

        TextView roleText = holder.roleText;
        roleText.setText(current.getRole());

        TextView dateText = holder.dateText;
        dateText.setText(current.getDate());

        TextView timeText = holder.timeText;
        timeText.setText(current.getTime());

        TextView payText = holder.payText;
        payText.setText(current.getPay());

        TextView trainingText = holder.trainingText;
        if (current.getchecked_in()) {
            if (!current.getCompleted()) {
                trainingText.setText("Incomplete Requirements");
                trainingText.setTextColor(Color.parseColor("#FF0000"));
            } else {
                trainingText.setText("Completed Requirements!");
                trainingText.setTextColor(Color.parseColor("#3CB043"));
            }
        } else {
            trainingText.setText("");
        }

        ImageView logoImage = holder.logoImage;
        Log.d("wtf", String.format("%d", current.getImageId()));
        Log.d("wtf", String.format("%d", R.drawable.fedex_logo));
        logoImage.setImageResource(current.getImageId());
//        logoImage.setImageResource(R.drawable.mcdonalds_logo);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}

package com.example.shift;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.shift.ui.notifications.NotificationsFragment;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder> {

    ArrayList<Job> list;

    public JobAdapter(ArrayList<Job> list){
        this.list = list;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView roleText;
        public TextView dateText;
        public TextView timeText;
        public TextView payText;

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

        TextView roleText = holder.roleText;
        roleText.setText(current.getRole());

        TextView dateText = holder.dateText;
        dateText.setText(current.getDate());

        TextView timeText = holder.timeText;
        timeText.setText(current.getTime());

        TextView payText = holder.payText;
        payText.setText(current.getPay());

        TextView trainingText = holder.trainingText;
        if (!current.getTraining()) {
            trainingText.setText("");
        } else {
            trainingText.setText("Incomplete Requirements");
        }

        ImageView logoImage = holder.logoImage;
        logoImage.setImageResource(current.getImageId());



    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}

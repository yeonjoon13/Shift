package com.example.shift;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.ViewHolder>{

    ArrayList<Job> list;

    private Context context;

    private OnClickJobListener videoListener;


    public QuizAdapter(ArrayList<Job> list, Context context, OnClickJobListener videoListener){
        this.list = list;
        this.context = context;
        this.videoListener = videoListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView titleText;

        public CardView quizCard;

        public ImageView quizImage;

        public ImageView completedImage;

        public ViewHolder(@org.checkerframework.checker.nullness.qual.NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.quizTitle);
            quizCard = itemView.findViewById(R.id.quiz_card);
            quizImage= itemView.findViewById(R.id.quiz_background);
            completedImage = itemView.findViewById(R.id.complete);
        }
    }


    @NonNull
    @Override
    public QuizAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View contactView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quiz_card, parent, false);

        // Return a new holder instance
        QuizAdapter.ViewHolder viewHolder = new QuizAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuizAdapter.ViewHolder holder, int position) {
        Job current = list.get(position);

        holder.quizCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoListener.onJobClick(current);
            }
        });

        ImageView playImage = (ImageView)holder.completedImage;
        if (!current.getCompleted()) {
            //playImage.setImageResource(R.drawable.baseline_play_circle_outline_24);
            //playImage.setVisibility(View.INVISIBLE);
            playImage.setImageResource(current.getImageId());
        } else {
            playImage.setImageResource(R.drawable.baseline_check_circle_outline_24);
            //playImage.setVisibility(View.VISIBLE);
        }

        ImageView quizImage = holder.quizImage;
        quizImage.setImageResource(R.drawable.quiz_background);

        TextView titleText = holder.titleText;
        String text = current.getCompany() + " Quiz";
        titleText.setText(text);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

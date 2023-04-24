package com.example.shift;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder>{

    ArrayList<Video> videos;
    private Context context;

    private OnClickJobListener videoListener;

    public VideoAdapter(ArrayList<Video> list, Context context, OnClickJobListener videoListener){
        this.videos = list;
        this.context = context;
        this.videoListener = videoListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView titleText;

        public CardView videoCard;

        public ImageView thumbnailImage;

        public ImageView playImage;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.video_title);
            videoCard = itemView.findViewById(R.id.video_card);
            thumbnailImage= itemView.findViewById(R.id.thumbnail);
            playImage = itemView.findViewById(R.id.play);
        }

    }

    @androidx.annotation.NonNull
    @Override
    public ViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        View contactView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_card, parent, false);

        // Return a new holder instance
        VideoAdapter.ViewHolder viewHolder = new VideoAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull ViewHolder holder, int position) {
        Video current = videos.get(position);

        holder.videoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoListener.onVideoClick(current);
            }
        });

        ImageView playImage = holder.playImage;
        if (!current.getWatched()) {
            playImage.setImageResource(R.drawable.baseline_play_circle_outline_24);
        } else {
            playImage.setImageResource(R.drawable.baseline_check_circle_outline_24);
        }

        ImageView thumbnailImage = holder.thumbnailImage;
        thumbnailImage.setImageResource(current.getThumbnail());

        TextView titleText = holder.titleText;
        titleText.setText(current.getTitle());




    }

    @Override
    public int getItemCount() {return videos.size();}

}

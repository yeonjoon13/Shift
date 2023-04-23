package com.example.shift;

import android.content.Context;
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

    public VideoAdapter(ArrayList<Video> list, Context context){
        this.videos = list;
        this.context = context;
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
        return null;
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {return videos.size();}

}

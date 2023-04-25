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

public class SearchJobAdapter extends RecyclerView.Adapter<SearchJobAdapter.ViewHolder> {
    ArrayList<Job> list;

    private Context context;

    private OnClickJobListener jobListener;


    public SearchJobAdapter(ArrayList<Job> list, Context context, OnClickJobListener jobListener){
        this.list = list;
        this.context = context;
        this.jobListener = jobListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView roleText;
        public TextView companyText;
        public ImageView logoImage;
        public CardView search_card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            roleText = itemView.findViewById(R.id.search_role);
            companyText = itemView.findViewById(R.id.search_name);
            logoImage = itemView.findViewById(R.id.search_logo);
            search_card = itemView.findViewById(R.id.search_card);

        }

    }

    @androidx.annotation.NonNull
    @Override
    public ViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        View contactView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_job, parent, false);

        // Return a new holder instance
        SearchJobAdapter.ViewHolder viewHolder = new SearchJobAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull ViewHolder holder, int position) {
        Job current = list.get(position);

        holder.search_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jobListener.onJobClick(current);
            }
        });

        TextView roleText = holder.roleText;
        roleText.setText(current.getRole());

        TextView companyText = holder.companyText;
        companyText.setText(current.getCompany());

        ImageView logoImage = holder.logoImage;
        logoImage.setImageResource(current.getImageId());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}

package com.example.academy;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class SuggestionAdapter extends FirebaseRecyclerAdapter<Post , SuggestionAdapter.SuggestionViewHolder>
{
    public SuggestionAdapter(@NonNull FirebaseRecyclerOptions<Post> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull SuggestionViewHolder holder, int position, @NonNull Post post)
    {
        holder.suggestion_video_title.setText(post.getVideo_title());
        holder.suggestion_uploader_name.setText(post.getUploader_name());
        holder.suggestion_date_time.setText(post.getData_time());
        final String vid_url = post.getVideo_link();
        Glide.with(holder.itemView).load(vid_url).into(holder.suggestion_video_img);
        final String up_na = post.getUploader_name();
        final String cr_na = post.getCourse_name();
        final String vid_desc = post.getVideo_description();
        final String dt = post.getData_time();
        final String vt = post.getVideo_title();
        holder.suggestion_layout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext() , Video_activity.class);
                intent.putExtra("video_link" , vid_url);
                intent.putExtra("up_name" ,up_na);
                intent.putExtra("dt" , dt);
                intent.putExtra("vt" , vt);
                intent.putExtra("cr_na" , cr_na);
                intent.putExtra("vid_desc" , vid_desc);
                v.getContext().startActivity(intent);
            }
        });


    }

    @NonNull
    @Override
    public SuggestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.suggestion , parent , false);
        return new SuggestionViewHolder(view);
    }

    class SuggestionViewHolder extends RecyclerView.ViewHolder
    {
        TextView suggestion_uploader_name , suggestion_video_title , suggestion_date_time;
        ImageView suggestion_video_img;
        RelativeLayout suggestion_layout;

        public SuggestionViewHolder(@NonNull View itemView)
        {
            super(itemView);
            suggestion_video_title = (TextView)itemView.findViewById(R.id.suggestion_video_title);
            suggestion_uploader_name = (TextView)itemView.findViewById(R.id.suggestion_uploader_name);
            suggestion_date_time  = (TextView)itemView.findViewById(R.id.suggestion_date_time);
            suggestion_video_img = (ImageView)itemView.findViewById(R.id.suggestion_video_img);
            suggestion_layout = (RelativeLayout)itemView.findViewById(R.id.suggestion_layout);


        }
    }
}

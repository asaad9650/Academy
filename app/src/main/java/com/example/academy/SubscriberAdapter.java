package com.example.academy;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class SubscriberAdapter extends FirebaseRecyclerAdapter<Post , SubscriberAdapter.SubscriberViewHolder>
{
    public SubscriberAdapter(@NonNull FirebaseRecyclerOptions<Post> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull SubscriberViewHolder holder, int position, @NonNull Post model)
    {
        holder.subscriber_uploader_name.setText(model.getUploader_name());
        holder.subscriber_video_title.setText(model.getVideo_title());
        holder.subscriber_course_name.setText(model.getCourse_name());
        final String up_na = model.getUploader_name();
        final String cr_na = model.getCourse_name();
        final String vid_desc = model.getVideo_description();
        final String dt = model.getData_time();
        final String vt = model.getVideo_title();
        // holder.da_ti.setText(post.getData_time());
        //  holder.up_na.setText(post.getUploader_name());
        final String vid_url = model.getVideo_link();
        Glide.with(holder.itemView).load(vid_url).into(holder.subscriber_video_img);
        holder.subscriber_card_view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext() , Video_activity.class);
             //   intent.putExtra("subscriber",from);
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
    public SubscriberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subscribed_video_list , parent , false);
        return new SubscriberAdapter.SubscriberViewHolder(view);
    }

    public class SubscriberViewHolder extends RecyclerView.ViewHolder
    {

        ImageView subscriber_video_img;
        TextView subscriber_video_title , subscriber_course_name , subscriber_uploader_name;
        CardView subscriber_card_view;
        public SubscriberViewHolder(@NonNull View itemView)
        {
            super(itemView);
            subscriber_video_img = itemView.findViewById(R.id.subscribed_video_img);
            subscriber_video_title = itemView.findViewById(R.id.subscribed_video_title);
            subscriber_course_name = itemView.findViewById(R.id.subscribed_course_name);
            subscriber_uploader_name = itemView.findViewById(R.id.subscribed_uploader_name);
            subscriber_card_view = itemView.findViewById(R.id.subscribed_card_view);
        }
    }
}

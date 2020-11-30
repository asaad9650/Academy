package com.example.academy;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class SaveAdapter extends FirebaseRecyclerAdapter<Post , SaveAdapter.SaveViewHolder>
{
    public SaveAdapter(@NonNull FirebaseRecyclerOptions<Post> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull SaveViewHolder holder, int position, @NonNull Post model)
    {
        holder.save_uploader_name.setText(model.getUploader_name());
        holder.save_video_title.setText(model.getVideo_title());
        holder.save_course_name.setText(model.getCourse_name());
        final String up_na = model.getUploader_name();
        final String cr_na = model.getCourse_name();
        final String vid_desc = model.getVideo_description();
        final String dt = model.getData_time();
        final String vt = model.getVideo_title();
        // holder.da_ti.setText(post.getData_time());
        //  holder.up_na.setText(post.getUploader_name());
        final String vid_url = model.getVideo_link();
        Glide.with(holder.itemView).load(vid_url).into(holder.save_video_img);
        holder.save_card_view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String from = "SaveAdapter";
                Intent intent = new Intent(v.getContext() , Video_activity.class);
                intent.putExtra("save",from);
                intent.putExtra("video_link" , vid_url);
                intent.putExtra("up_name" ,up_na);
                intent.putExtra("dt" , dt);
                intent.putExtra("vt" , vt);
                intent.putExtra("cr_na" , cr_na);
                intent.putExtra("vid_desc" , vid_desc);
                v.getContext().startActivity(intent);
            }
        });

        holder.save_card_view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v)
            {
                Toast.makeText(v.getContext(), "Long clicked", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }

    @NonNull
    @Override
    public SaveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.save_list , parent , false);
        return new SaveViewHolder(view);
    }

    class SaveViewHolder extends RecyclerView.ViewHolder
    {
        //IIA lab saturday
        ImageView save_video_img;
        TextView save_video_title , save_course_name , save_uploader_name;
        CardView save_card_view;

        public SaveViewHolder(@NonNull View itemView)
        {

            super(itemView);
            save_video_img = itemView.findViewById(R.id.save_video_img);
            save_video_title = itemView.findViewById(R.id.save_video_title);
            save_course_name = itemView.findViewById(R.id.save_course_name);
            save_uploader_name = itemView.findViewById(R.id.save_uploader_name);
            save_card_view = itemView.findViewById(R.id.save_card_view);
            
        }
    }
}

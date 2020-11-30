package com.example.academy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Search_adapter extends RecyclerView.Adapter<Search_adapter.MyAdapterViewHolder>
{
    public Context myContext;
    public ArrayList<Post> arrayList;

    public Search_adapter(Context context , ArrayList<Post> arrayList)
    {
        this.myContext = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_search_item, parent , false);
        return new MyAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyAdapterViewHolder holder, int position)
    {
        Post post = arrayList.get(position);
        final String vid_url = post.getVideo_link();
        final String up_na = post.getUploader_name();
        final String cr_na = post.getCourse_name();
        final String vid_desc = post.getVideo_description();
        final String dt = post.getData_time();
        final String vt = post.getVideo_title();

        holder.search_vid_title.setText(post.getVideo_title());
        holder.search_course_name.setText(post.getCourse_name());
        holder.search_uploader_name.setText(post.getUploader_name());
        Glide.with(holder.itemView).load(vid_url).into(holder.search_img);
        holder.search_layout.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class MyAdapterViewHolder extends RecyclerView.ViewHolder
    {
        public TextView search_vid_title, search_course_name , search_uploader_name;
        public ImageView search_img;
        public RelativeLayout search_layout;
        public MyAdapterViewHolder(@NonNull View itemView)
        {
            super(itemView);
            search_vid_title = itemView.findViewById(R.id.search_vid_title);
            search_course_name = itemView.findViewById(R.id.search_course_name);
            search_uploader_name = itemView.findViewById(R.id.search_uploader_name);
            search_img = itemView.findViewById(R.id.search_img);
            search_layout = itemView.findViewById(R.id.search_layout);
        }
    }
}

package com.example.academy;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class SubsAdapter extends FirebaseRecyclerAdapter<Subscribe,SubsAdapter.SubsViewHolder>
{

    public SubsAdapter(@NonNull FirebaseRecyclerOptions<Subscribe> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull SubsViewHolder holder, int position, @NonNull Subscribe subscribe)
    {

        //String uploader_name = subscribe.getUploader_name();
        holder.text_uploader_name.setText(subscribe.getUploader_name());
        final String name = subscribe.getUploader_name();
        holder.subs_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(v.getContext(), name + " clicked." , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext() , Subscriber.class);
                intent.putExtra("name",name);
                v.getContext().startActivity(intent);
            }
        });

    }

    @NonNull
    @Override
    public SubsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subscribed_list , parent , false);
        return new SubsViewHolder(view);
    }

    class SubsViewHolder extends RecyclerView.ViewHolder
    {
        ImageView img_uploader_logo;
        TextView text_uploader_name ;
        CardView subs_layout;

        public SubsViewHolder(@NonNull View itemView)
        {
            super(itemView);
            text_uploader_name = itemView.findViewById(R.id.subscription_name);
            img_uploader_logo = itemView.findViewById(R.id.subscription_logo);
            subs_layout = itemView.findViewById(R.id.subscription_layout);
        }
    }
}

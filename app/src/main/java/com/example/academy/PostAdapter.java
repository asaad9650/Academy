package com.example.academy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.Image;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.ImageHeaderParserUtils;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends FirebaseRecyclerAdapter<Post , PostAdapter.PostViewHolder> implements Filterable
{
    private List<Post> postList;
    private List<Post> postListFull;


    PostAdapter(@NonNull FirebaseRecyclerOptions<Post> options ) {
        super(options);
       // this.postList = postList;
        //postListFull = postList;
       // super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull Post post)
    {
        holder.cr_na.setText(post.getCourse_name());
        final String up_na = post.getUploader_name();
        final String cr_na = post.getCourse_name();
        final String vid_desc = post.getVideo_description();
        final String dt = post.getData_time();
        final String vt = post.getVideo_title();
       // holder.da_ti.setText(post.getData_time());
      //  holder.up_na.setText(post.getUploader_name());
        final String vid_url = post.getVideo_link();
        //holder.vi_de.setText(post.getVideo_description());
        holder.vi_ti.setText(post.getVideo_title());
        //holder.vi_li.setText(post.getVideo_link());
        //Glide.with().asBitmap().load().apply().into(img_thumbnail);

        //holder.img_thumbnail.setVideoURI(Uri.parse(vid_url));
        Glide.with(holder.itemView).load(vid_url).into(holder.img_thumbnail);

        holder.img_thumbnail.setOnClickListener(new View.OnClickListener() {
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

       /* holder.img_thumbnail.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(this, Video_activity.class);
            }
        });*/


//        Bitmap thumb = ThumbnailUtils.createVideoThumbnail(path,
//               MediaStore.Images.Thumbnails.MINI_KIND);

       /* Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(vid_url, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
     //   bMap = transformBitmapToCircularBitmap(bMap);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        out = new FileOutputStream(land.getPath());
        out.write(byteArray);
        out.close();
        holder.img_thumbnail.setImageBitmap(bitmap);


        Picasso.get().load(vid_url).into(holder.img_thumbnail);*/

    }

   /* public static Bitmap transformBitmapToCircularBitmap(Bitmap source)
    {
        int size = Math.min(source.getWidth(), source.getHeight());

        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap,
                BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);

        squaredBitmap.recycle();
        return bitmap;
    }*/

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post , parent , false);
        return new PostViewHolder(view);
    }

    class PostViewHolder extends RecyclerView.ViewHolder
    {
        TextView cr_na, da_ti , up_na , vi_de , vi_ti ,vi_li ;
        ImageView img_thumbnail;

        public PostViewHolder(@NonNull View itemView)
        {
            super(itemView);
            cr_na = itemView.findViewById(R.id.cr_na);
            //da_ti = itemView.findViewById(R.id.da_ti);
            //up_na = itemView.findViewById(R.id.up_na);
            //vi_de = itemView.findViewById(R.id.vi_de);
            vi_ti = itemView.findViewById(R.id.vi_ti);
           //vi_li = itemView.findViewById(R.id.vi_li);
            img_thumbnail = itemView.findViewById(R.id.img_thumbnail);

         }

        }

        public void setSearchOperation(List<Post> newList)
        {
          //  my_List = new ArrayList<>();
           // my_List.addAll(newList);
            notifyDataSetChanged();
        }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {
            List<Post> filteredList = new ArrayList<>();
            if (constraint ==  null || constraint.length() == 0)
            {
                filteredList.addAll(postListFull);
            }
            else
                {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Post post : postListFull)
                    {
                        if (post.getVideo_title().contains(filterPattern) || post.getCourse_name().contains(filterPattern))
                        {
                            filteredList.add(post);
                        }
                    }
                }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            postList.clear();
            postList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}
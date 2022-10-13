package com.example.witssocial_;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class textPostAdapter extends FirebaseRecyclerAdapter<Post,textPostAdapter.myViewHolder> {

    public textPostAdapter(@NonNull FirebaseRecyclerOptions<Post> options) {
        super(options);

    }



    @Override
    protected void onBindViewHolder(@NonNull textPostAdapter.myViewHolder holder, int position, @NonNull Post post) {
//        Glide.with(holder.post.getContext())
//                .load(post.getPost())
//                .placeholder(R.drawable.ic_baseline_person_24)
//                .error(R.drawable.ic_launcher_foreground)
//                .into(holder.post);
        holder.post.setText(post.getPost());
        holder.handle.setText(post.getUsername());
        holder.caption.setText(post.getCaption());
        //holder.userprofile.setImageResource(R.drawable.img_1);
        Glide.with(holder.userprofile.getContext())
                .load(R.drawable.img_1)
                .placeholder(R.drawable.ic_baseline_person_24)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.userprofile);
    }

    @NonNull
    @Override
    public textPostAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_text_layout2,parent,false);
        return new textPostAdapter.myViewHolder(view);
    }

    static class myViewHolder extends RecyclerView.ViewHolder{
        TextView handle, caption, post;
        ImageView userprofile;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            handle = itemView.findViewById(R.id.handle);
            caption = itemView.findViewById(R.id.caption);
            post = itemView.findViewById(R.id.post);
            userprofile=itemView.findViewById(R.id.userpropic);
        }
    }



}

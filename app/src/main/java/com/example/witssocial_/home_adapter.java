package com.example.witssocial_;


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

public class home_adapter extends FirebaseRecyclerAdapter<Post,home_adapter.myViewHolder> {

    public home_adapter(@NonNull FirebaseRecyclerOptions<Post> options){
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Post post) {
            Glide.with(holder.post.getContext())
                    .load(post.getPost())
                    .placeholder(R.drawable.ic_baseline_person_24)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(holder.post);
            holder.handle.setText(post.getUsername());
            holder.caption.setText(post.getCaption());
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_layout,parent,false);
        return new myViewHolder(view);
    }

    static class myViewHolder extends RecyclerView.ViewHolder{
        TextView handle, caption;
        ImageView post;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            handle = itemView.findViewById(R.id.handle);
            caption = itemView.findViewById(R.id.caption);
            post = itemView.findViewById(R.id.post);
        }
    }

}

package com.example.witssocial_;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class FollowingAdapter extends FirebaseRecyclerAdapter<user_class,FollowingAdapter.myViewHolder> {

    Context context;
    String remove_delete_button;
    public FollowingAdapter(@NonNull FirebaseRecyclerOptions<user_class> options, Context context,String remove_delete_button){
        super(options);
        this.context=context;
        this.remove_delete_button=remove_delete_button;
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull user_class post) {

        holder.email.setText(post.getEmail());
        holder.username.setText(post.getUsername());
        if(remove_delete_button.equals("yes")){
            holder.unfollow_user_in_list_of_following.setBackgroundColor(Color.WHITE);
        }else{
            holder.unfollow_user_in_list_of_following.setOnClickListener(view -> {
                String email="karabol@gmail.com";
                try{email=Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail()).replace("@","").replace(".","");}catch (Exception e){email="karabo@gmail.com";}
                String branch1= email.replace("@","").replace(".","");
                String branch2=post.getEmail().replace("@","").replace(".","");
                //remove from list of people im following
                FirebaseDatabase.getInstance().getReference()
                        .child("Wits Social Database1")
                        .child("User Following")
                        .child(branch1)
                        .child(branch2)
                        .removeValue();
                //remove from list of list of people who are following me
                FirebaseDatabase.getInstance().getReference()
                        .child("Wits Social Database1")
                        .child("User Followers")
                        .child(branch2)
                        .child(branch1)
                        .removeValue();
            });
        }
        Glide.with(holder.friend_profile_chat.getContext())
                .load(post.getImage())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_baseline_person_24)
                .into(holder.friend_profile_chat);

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.follow_following,parent,false);
        return new myViewHolder(view);
    }

    static class myViewHolder extends RecyclerView.ViewHolder{
        TextView email, username;
        de.hdodenhof.circleimageview.CircleImageView friend_profile_chat;
        AppCompatImageView unfollow_user_in_list_of_following;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.email);
            username = itemView.findViewById(R.id.username);
            unfollow_user_in_list_of_following=itemView.findViewById(R.id.unfollow_user_in_list_of_following);
            friend_profile_chat=itemView.findViewById(R.id.friend_profile_chat);
        }
    }

}

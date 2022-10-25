package com.example.witssocial_;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class home_adapter extends FirebaseRecyclerAdapter<Post,home_adapter.myViewHolder> {
    Context context;
    String came_from,email,username,description;
    public home_adapter(@NonNull FirebaseRecyclerOptions<Post> options,Context context,String came_from,String email,String username,String description){
        super(options);
        this.context=context;
        this.came_from=came_from;
        this.email=email;
        this.username=username;
        this.description=description;
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Post post) {
        holder.post.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
        if(!post.getType().equals("text_post")){
            holder.post.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 385));
            Glide.with(holder.post.getContext())
                    .load(post.getPost())
                    .fitCenter()
                    .transform(new RoundedCorners(10))
                    .placeholder(R.drawable.ic_baseline_person_24)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(holder.post);
        }

        holder.caption.setText(post.getCaption());
        FirebaseDatabase.getInstance().getReference().child("Wits Social Database1")
                .child("Users")
                .child(post.getUsername()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        user_class user_class=snapshot.getValue(com.example.witssocial_.user_class.class);
                        assert user_class != null;
                        holder.handle.setText(user_class.getEmail());
                        holder.username.setText("@"+user_class.getUsername());
                    Glide.with(holder.userprofile.getContext())
                            .load(user_class.getImage())
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .error(R.drawable.ic_baseline_person_24)
                            .into(holder.userprofile);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        String ID = post.getId();
        String user = "";
        try {user= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();}
        catch(Exception e) {
            //  Block of code to handle errors
            user="karabo@gmail.com";
        }
        likers like = new likers(user);

        //check if you already liked post
        final boolean[] check = {false};
        assert user != null;
        FirebaseDatabase.getInstance().getReference()
                .child("Wits Social Database1")
                .child("ID")
                .child(ID)
                .child(user.replace("@","").replace(".",""))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            check[0]=true;
                            holder.heart.setImageResource(R.drawable.img_5);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        //count number of likes
        FirebaseDatabase.getInstance().getReference()
                .child("Wits Social Database1")
                .child("ID")
                .child(ID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            int count=(int)snapshot.getChildrenCount();
                            holder.likeCount.setText(Integer.toString(count));
                        }else{
                            holder.likeCount.setText("0");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        //count number of comments
        FirebaseDatabase.getInstance().getReference()
                .child("Wits Social Database1")
                .child("Comments")
                .child(ID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            int count=(int)snapshot.getChildrenCount();
                            holder.commentsCount.setText(Integer.toString(count));
                        }else{
                            holder.commentsCount.setText("0");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        //like or unlike
        String finalUser = user;
        holder.heart.setOnClickListener(view -> {

            if(!check[0]){


                FirebaseDatabase.getInstance().getReference("Wits Social Database1")
                        .child("ID")
                        .child(ID)
                        .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser().getEmail()).replace("@","").replace(".",""))
                        .setValue(like);
                holder.heart.setImageResource(R.drawable.img_5);
                check[0] =true;

            }else{
                /*Remove like frome database*/
                FirebaseDatabase.getInstance().getReference()
                        .child("Wits Social Database1")
                        .child("ID")
                        .child(ID)
                        .child(finalUser.replace("@","").replace(".",""))
                        .removeValue();
                holder.heart.setImageResource(R.drawable.heart2);
                check[0] =false;

            }
        });

        //view post comments
        holder.go_to_comments.setOnClickListener(view -> {
            Intent intent=new Intent(context,Comment_Section.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("post_id",post.getId());
            intent.putExtra("came_from",came_from);
            if(came_from.equals("friend_profile")){
                intent.putExtra("receiver_id",email);
                intent.putExtra("receiver_username",username);
                intent.putExtra("receiver_description",description);
                intent.putExtra("receiver_profile_pic",post.getUsername());
            }

            context.startActivity(intent);
        });
        //view the post
        holder.post.setOnClickListener(v -> {
            if(!post.getPost().equals("null")){
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setType("*/*");
                intent.setData(Uri.parse(post.getPost()));
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_layout2,parent,false);
        return new myViewHolder(view);
    }

    static class myViewHolder extends RecyclerView.ViewHolder{
        TextView handle, caption, username,likeCount,commentsCount;
        ImageView  userprofile, heart,go_to_comments;
        ImageView post;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            handle = itemView.findViewById(R.id.handle);
            username=itemView.findViewById(R.id.username);
            caption = itemView.findViewById(R.id.caption);
            post = itemView.findViewById(R.id.post);
            userprofile=itemView.findViewById(R.id.userpropic);
            heart = itemView.findViewById(R.id.heart);
            likeCount = itemView.findViewById(R.id.likeCount);
            go_to_comments = itemView.findViewById(R.id.go_to_comments);
            commentsCount=itemView.findViewById(R.id.commentsCount);
        }
    }

}

package com.example.witssocial_;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
    public home_adapter(@NonNull FirebaseRecyclerOptions<Post> options,Context context){
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Post post) {
        Glide.with(holder.post.getContext())
                .load(post.getPost())
                .placeholder(R.drawable.ic_baseline_person_24)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.post);
        holder.caption.setText(post.getCaption());
        FirebaseDatabase.getInstance().getReference().child("Wits Social Database")
                .child("Users")
                .child(post.getUsername()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        user_class user_class=snapshot.getValue(com.example.witssocial_.user_class.class);
                        assert user_class != null;
                        holder.handle.setText(user_class.getEmail());
                        holder.username.setText("@"+user_class.getUsername());
                    /*Glide.with(holder.userprofile.getContext())
                            .load(user_class.getUrl())
                            .placeholder(R.drawable.ic_baseline_person_24)
                            .error(R.drawable.ic_launcher_foreground)
                            .into(holder.userprofile);*/
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        Glide.with(holder.userprofile.getContext())
                .load(R.drawable.img_1)
                .placeholder(R.drawable.ic_baseline_person_24)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.userprofile);
        String ID = post.getId();
        String user = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        likers like = new likers(user);

        final boolean[] check = {false};
        FirebaseDatabase.getInstance().getReference()
                .child("Wits Social Database")
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

        //int count = post.getCountLikes();

        FirebaseDatabase.getInstance().getReference()
                .child("Wits Social Database")
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

        //Toast.makeText(context, , Toast.LENGTH_SHORT).show();
        holder.heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!check[0]){


                    FirebaseDatabase.getInstance().getReference("Wits Social Database")
                            .child("ID")
                            .child(ID)
                            .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser().getEmail()).replace("@","").replace(".",""))
                            .setValue(like);
                    holder.heart.setImageResource(R.drawable.img_5);
                    check[0] =true;

                }else{
                    /*Remove like frome database*/
                    FirebaseDatabase.getInstance().getReference()
                            .child("Wits Social Database")
                            .child("ID")
                            .child(ID)
                            .child(user.replace("@","").replace(".",""))
                            .removeValue();
                    holder.heart.setImageResource(R.drawable.heart2);
                    check[0] =false;

                }
                // holder.likeCount.setText(String.valueOf(count[0]));
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
        TextView handle, caption, username,likeCount;
        ImageView post, userprofile, heart;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            handle = itemView.findViewById(R.id.handle);
            username=itemView.findViewById(R.id.username);
            caption = itemView.findViewById(R.id.caption);
            post = itemView.findViewById(R.id.post);
            userprofile=itemView.findViewById(R.id.userpropic);
            heart = itemView.findViewById(R.id.heart);
            likeCount = itemView.findViewById(R.id.likeCount);
        }
    }

}

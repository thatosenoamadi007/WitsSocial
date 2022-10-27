package com.example.witssocial_;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//This is the Adapter for the comment section

public class CommentsAdapter extends FirebaseRecyclerAdapter<comment, CommentsAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     */
    Context context;
    public CommentsAdapter(@NonNull FirebaseRecyclerOptions<comment> options) {
        super(options);
        this.context=context;
    }
    //Set the holder to show comment
    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull comment model) {

        FirebaseDatabase.getInstance().getReference().child("Wits Social Database1")
                .child("Users")
                .child(model.getId())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            user_class user_class=snapshot.getValue(com.example.witssocial_.user_class.class);
                            assert user_class != null;
                            holder.comment_name.setText(user_class.getEmail());
                            holder.comment_content.setText(model.getComment());
                            holder.comment_username.setText("@"+user_class.getUsername());
                            Glide.with(holder.commenter_profile_pic.getContext())
                                    .load(user_class.getImage())
                                    .placeholder(R.drawable.ic_baseline_person_24)
                                    .error(R.drawable.ic_launcher_foreground)
                                    .into(holder.commenter_profile_pic);

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



    }
    //set the layout to comment
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment,parent,false);
        return new myViewHolder(view);
    }

    static class myViewHolder extends RecyclerView.ViewHolder{
        AppCompatTextView comment_name,comment_content,comment_username;
        de.hdodenhof.circleimageview.CircleImageView commenter_profile_pic;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            comment_name= itemView.findViewById(R.id.comment_name);
            comment_username= itemView.findViewById(R.id.comment_username);
            comment_content= itemView.findViewById(R.id.comment_content);
            commenter_profile_pic=itemView.findViewById(R.id.commenter_profile_pic);
        }

    }

}

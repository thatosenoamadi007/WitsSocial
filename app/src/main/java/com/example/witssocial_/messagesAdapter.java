package com.example.witssocial_;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class messagesAdapter extends FirebaseRecyclerAdapter<user_class, messagesAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     */
    Context context;
    public messagesAdapter(@NonNull FirebaseRecyclerOptions<user_class> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull user_class model) {
        String email= model.getEmail();
        String username=model.getUsername();
        String description= model.getDescription();
        holder.friend_name.setText(email);
        holder.friend_username.setText(username);
        Glide.with(holder.friend_profile.getContext())
                .load(model.getImage())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_baseline_person_24)
                .into(holder.friend_profile);

        holder.message_layout.setOnClickListener(view -> {
            Intent intent=new Intent(context,a_FriendProfile.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("receiver_id",email);
            intent.putExtra("receiver_username",username);
            intent.putExtra("receiver_description",description);
            intent.putExtra("receiver_profile_pic",model.getImage());
            context.startActivity(intent);
        });


    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.messageapperance,parent,false);
        return new myViewHolder(view);
    }

    static class myViewHolder extends RecyclerView.ViewHolder{
        TextView friend_name,friend_username;
        de.hdodenhof.circleimageview.CircleImageView friend_profile;
        CardView message_layout;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            friend_name= itemView.findViewById(R.id.email);
            friend_username= itemView.findViewById(R.id.username);
            friend_profile= itemView.findViewById(R.id.userpropic);
            message_layout= itemView.findViewById(R.id.message_friend_id);

        }

    }

}


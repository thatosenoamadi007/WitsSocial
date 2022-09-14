package com.example.witssocial_;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class messagesAdapter extends FirebaseRecyclerAdapter<user, messagesAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     */
    Context context;
    public messagesAdapter(@NonNull FirebaseRecyclerOptions<user> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull user model) {

        String name= model.getUsername();
        holder.friend_name.setText(name);
        holder.friend_profile.setImageResource(R.drawable.ic_account);

        holder.message_layout.setOnClickListener(view -> {
            //Intent intent=new Intent(context,InsideMessage.class);
            Intent intent=new Intent(context,a_FriendProfile.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("receiver_id",name);
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
        TextView friend_name;
        de.hdodenhof.circleimageview.CircleImageView friend_profile;
        CardView message_layout;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            friend_name= itemView.findViewById(R.id.friend_name_chat);
            friend_profile= itemView.findViewById(R.id.friend_profile_chat);
            message_layout= itemView.findViewById(R.id.message_friend_id);

        }

    }

}

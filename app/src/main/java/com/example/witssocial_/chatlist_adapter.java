package com.example.witssocial_;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class chatlist_adapter extends FirebaseRecyclerAdapter<likers, chatlist_adapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     */
    Context context;
    String came_from;
    public chatlist_adapter(@NonNull FirebaseRecyclerOptions<likers> options, Context context,String came_from) {
        super(options);
        this.context=context;
        this.came_from=came_from;
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull likers model) {
        //diplay coorect text on button depending on the activity it came from
        //if came from Archive_Users set button text to "undo"
        //else set button to archive
        if(came_from.equals("Archive_Users")){
            holder.archive_user.setText("Undo");
        }
        //sets name,email,profile pic of each users
        FirebaseDatabase.getInstance().getReference().child("Wits Social Database1").child("Users ID").child(model.getLikerID().replace("@","").replace(".",""))
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                likers likers=snapshot.getValue(com.example.witssocial_.likers.class);

                                FirebaseDatabase.getInstance().getReference().child("Wits Social Database1").child("Users").child(likers.getLikerID())
                                        .addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                user_class model=snapshot.getValue(com.example.witssocial_.user_class.class);
                                                String email= model.getEmail();
                                                String username=model.getUsername();
                                                String description= model.getDescription();
                                                holder.friend_name.setText(email);
                                                holder.friend_username.setText(username);
                                                Glide.with(holder.friend_profile.getContext()).load(model.getImage()).placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.ic_baseline_person_24).into(holder.friend_profile);

                                                holder.message_layout.setOnClickListener(view -> {
                                                    Intent intent=new Intent(context,InsideMessage.class);intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);intent.putExtra("receiver_id",email);intent.putExtra("receiver_username",username);intent.putExtra("receiver_description",description);intent.putExtra("receiver_profile_pic",model.getImage());intent.putExtra("came_from",came_from);
                                                    context.startActivity(intent);
                                                });

                                                //archive and save users to list of archived users
                                                //or remove user from list of archived users
                                                holder.archive_user.setOnClickListener(view -> {
                                                    //add user to list of archived
                                                    if(came_from.equals("Archive_Users")) {
                                                        FirebaseDatabase.getInstance().getReference().child("Wits Social Database1").child("List of friends").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail()).replace("@", "").replace(".", "")).child(email.replace("@", "").replace(".", "")).setValue(new likers(email));
                                                        FirebaseDatabase.getInstance().getReference().child("Wits Social Database1").child("Archived Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(email.replace("@", "").replace(".", "")).removeValue();
                                                    }
                                                    //remove user from list of archived users
                                                    else{
                                                        FirebaseDatabase.getInstance().getReference().child("Wits Social Database1").child("List of friends").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail()).replace("@","").replace(".","")).child(email.replace("@","").replace(".","")).removeValue();
                                                        FirebaseDatabase.getInstance().getReference().child("Wits Social Database1").child("Archived Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(email.replace("@","").replace(".","")).setValue(new likers(email));
                                                    }

                                                });

                                           }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {}
                                        });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {}
                        });




    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.messageappearance2,parent,false);
        return new myViewHolder(view);
    }

    static class myViewHolder extends RecyclerView.ViewHolder{
        TextView friend_name,friend_username;
        de.hdodenhof.circleimageview.CircleImageView friend_profile;
        RelativeLayout message_layout;
        AppCompatButton archive_user;
        AppCompatImageView delete_whole_chat;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            friend_name= itemView.findViewById(R.id.email);
            friend_username= itemView.findViewById(R.id.username);
            friend_profile= itemView.findViewById(R.id.userpropic);
            message_layout= itemView.findViewById(R.id.message_friend_id);
            archive_user=itemView.findViewById(R.id.archive_user);
            delete_whole_chat=itemView.findViewById(R.id.delete_whole_chat);
        }

    }


}


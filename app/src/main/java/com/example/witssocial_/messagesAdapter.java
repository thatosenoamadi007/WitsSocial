package com.example.witssocial_;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

public class messagesAdapter extends FirebaseRecyclerAdapter<user_class, messagesAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     */
    Context context;
    String BusySearching;
    public messagesAdapter(@NonNull FirebaseRecyclerOptions<user_class> options, Context context,String BusySearching) {
        super(options);
        this.context=context;
        this.BusySearching=BusySearching;
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull user_class model) {

        //sets the values of each item in the list of messages recycler view
        FirebaseDatabase.getInstance().getReference().child("Wits Social Database1").child("Users ID").child(model.getEmail().replace("@","").replace(".",""))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        likers likers=snapshot.getValue(com.example.witssocial_.likers.class);
                        FirebaseDatabase.getInstance().getReference().child("Wits Social Database1").child("Users").child(likers.getLikerID())
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        user_class model2=snapshot.getValue(com.example.witssocial_.user_class.class);
                                        String email= model2.getEmail();
                                        String username=model2.getUsername();
                                        String description= model2.getDescription();
                                        holder.friend_name.setText(email);
                                        holder.friend_username.setText(username);
                                        Glide.with(holder.friend_profile.getContext()).load(model2.getImage()).placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.ic_baseline_person_24).into(holder.friend_profile);

                                        //navigsation to insidemessage when item is clicked
                                        holder.message_layout.setOnClickListener(view -> {
                                            Intent intent=new Intent(context,a_FriendProfile.class);intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);intent.putExtra("receiver_id",email);intent.putExtra("receiver_username",username);intent.putExtra("receiver_description",description);intent.putExtra("receiver_profile_pic",model2.getImage());
                                            context.startActivity(intent);
                                        });
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {}
                                });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;
        if (BusySearching=="false") {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.messageapperance,parent,false);
        }
        else {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.messageappearance3,parent,false);
        }

        //return new myViewHolder(view);
        return new myViewHolder(view);
    }

    static class myViewHolder extends RecyclerView.ViewHolder{
        TextView friend_name,friend_username;
        de.hdodenhof.circleimageview.CircleImageView friend_profile;
        CardView message_layout;
        AppCompatImageView delete_recently_searched_user;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            friend_name= itemView.findViewById(R.id.email);
            friend_username= itemView.findViewById(R.id.username);
            friend_profile= itemView.findViewById(R.id.userpropic);
            message_layout= itemView.findViewById(R.id.message_friend_id);
            delete_recently_searched_user=itemView.findViewById(R.id.delete_recently_searched_user);

        }

    }

    /*private void RemoverecentlySearched(String branch1,String branch2) {
        String my_email="";
        try{my_email= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();} catch (Exception e){my_email=branch1;}
        FirebaseDatabase.getInstance().getReference().child("Wits Social Database1").child("Search History").child(my_email.replace("@","").replace(".","")).child(branch2.replace("@","").replace(".","")).removeValue();
    }*/

}


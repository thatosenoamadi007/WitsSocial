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
     * @param options
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

        holder.message_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,InsideMessage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("receiver_id",name);
                context.startActivity(intent);
            }
        });


    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.messageapperance,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView friend_name,friend_message;
        de.hdodenhof.circleimageview.CircleImageView friend_profile;
        CardView message_layout;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            friend_name=(TextView) itemView.findViewById(R.id.friend_name_chat);
            //friend_message=(TextView) itemView.findViewById(R.id.friend_message_chat);
            friend_profile=(de.hdodenhof.circleimageview.CircleImageView)itemView.findViewById(R.id.friend_profile_chat);
            message_layout=(CardView) itemView.findViewById(R.id.message_friend_id);

        }

    }

    String capitalizeWord(String str){
        String words[]=str.split("\\s");
        String capitalizeWord="";
        for(String w:words){
            String first=w.substring(0,1);
            String afterfirst=w.substring(1);
            capitalizeWord+=first.toUpperCase()+afterfirst+" ";
        }
        return capitalizeWord.trim();
    }
}

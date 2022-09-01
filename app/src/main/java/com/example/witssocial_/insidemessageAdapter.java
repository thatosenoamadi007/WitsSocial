package com.example.witssocial_;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class insidemessageAdapter extends FirebaseRecyclerAdapter<messageObject, insidemessageAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Context context;
    public insidemessageAdapter(@NonNull FirebaseRecyclerOptions<messageObject> options, Context context) {
        super(options);
        this.context=context;
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull messageObject model) {
        holder.message_content.setText(model.getContent());

        //set time and date of the message to be displayed
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String date1= dateFormat.format(date);
        if(model.getTimestamp()!=null){
            LocalDate currentDate
                    = LocalDate.parse(date1.substring(0,10).replaceAll("/","-"));
            if(date1.substring(0,10).equals(model.getTimestamp().substring(0,10))){
                String time=model.getTimestamp().substring(11,16);
                holder.time.setText("Today at "+time);
            }
            else if(currentDate.minusDays(1).toString().equals(model.getTimestamp().substring(0,10).replaceAll("/","-"))){
                String time=model.getTimestamp().substring(11,16);
                holder.time.setText("Yesterday at "+time);
            }else{
                String day=model.getTimestamp().substring(8,10);
                String year=model.getTimestamp().substring(0,4);
                String month=currentDate.getMonth().toString().substring(0,3);
                String time=model.getTimestamp().substring(11,16);
                holder.time.setText(day+" "+month+" "+year+" "+time);
            }
        }

        //check who is the sender and the recipient, and arranges the layout
        if(model.getSender_id().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString())){
            Drawable d = context.getResources().getDrawable(R.drawable.message_container_bg2);
            holder.change_color.setBackground(d);
            holder.change_gravity.setGravity(Gravity.END);

        }else{
            Drawable d = context.getResources().getDrawable(R.drawable.message_contaner_bg);
            holder.change_color.setBackground(d);
            holder.change_gravity.setGravity(Gravity.START);
        }



    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_container,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView message_content,time;
        RelativeLayout change_gravity,change_color;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            message_content=(TextView) itemView.findViewById(R.id.display_message);
            change_gravity=(RelativeLayout) itemView.findViewById(R.id.change_gravity);
            change_color=(RelativeLayout) itemView.findViewById(R.id.change_color);
            time=(TextView) itemView.findViewById(R.id.time);
        }

    }
}

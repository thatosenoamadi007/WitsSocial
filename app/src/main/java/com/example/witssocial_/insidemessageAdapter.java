package com.example.witssocial_;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class insidemessageAdapter extends FirebaseRecyclerAdapter<messageObject, insidemessageAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
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
        //check the format of the message
        if(model.getFormat().equals("image")){
            holder.attachment.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
            if(model.getContent().isEmpty()){
                holder.message_content.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
            }
            //set the image
            Glide.with(holder.message_image.getContext())
                    .load(model.getAttachment_url())
                    .transform(new RoundedCorners(16))
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_baseline_person_24)
                    .into(holder.message_image);
            //download or view image on chrome
            holder.message_image.setOnClickListener(view -> {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setType("*/*");
                intent.setData(Uri.parse(model.getAttachment_url()));
                context.startActivity(intent);
            });
        }else if(model.getFormat().equals("text")){
            //collapse everything since the format is text only
            holder.message_image.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
            holder.attachment.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
            holder.message_content.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        }
        //if message is of type document,image or video change layout
        else{
            String format_name=model.getFormat();
            if(format_name.equals("documents")){
                format_name="document";
            }
            holder.attachment_name.setText(format_name);
            //download or view the attachment
            holder.download_attachment.setOnClickListener(view -> {
                    Intent intent=new Intent(Intent.ACTION_VIEW);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setType("*/*");
                    intent.setData(Uri.parse(model.getAttachment_url()));
                    context.startActivity(intent);
            });
            //collapse the image view since format of image is png,jpeg,etc.
            holder.message_image.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
            //if text is empty collapse the text layout also
            if(model.getContent().isEmpty()){
                holder.message_content.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
            }
        }
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
        if(model.getSender_id().equals(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail())){
            @SuppressLint("UseCompatLoadingForDrawables") Drawable d = context.getResources().getDrawable(R.drawable.message_container_bg2);
            holder.change_color.setBackground(d);
            holder.change_gravity.setGravity(Gravity.END);

        }else{
            @SuppressLint("UseCompatLoadingForDrawables") Drawable d = context.getResources().getDrawable(R.drawable.message_contaner_bg);
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

    static class myViewHolder extends RecyclerView.ViewHolder{
        TextView message_content,time,attachment_name;
       // RelativeLayout change_gravity,change_color;
       RelativeLayout change_gravity;
       CardView attachment;
       LinearLayout change_color;
       ImageView message_image,download_attachment;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            message_content= itemView.findViewById(R.id.display_message);
            change_gravity= itemView.findViewById(R.id.change_gravity);
            change_color= itemView.findViewById(R.id.change_color);
            time= itemView.findViewById(R.id.time);
            message_image=itemView.findViewById(R.id.message_image);
            download_attachment=itemView.findViewById(R.id.download_attachment);
            attachment_name=itemView.findViewById(R.id.attachment_name);
            attachment=itemView.findViewById(R.id.attachment);
        }

    }
}

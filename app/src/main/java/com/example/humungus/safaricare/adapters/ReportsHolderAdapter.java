package com.example.humungus.safaricare.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.humungus.safaricare.R;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by kadan on 3/2/18.
 */

public class ReportsHolderAdapter  extends RecyclerView.ViewHolder{

    private CircleImageView circleImageView;
    private TextView UsernameTV;
    private TextView dateTV;
    private TextView tweetTV;


    public ReportsHolderAdapter(View itemView) {
        super(itemView);
        circleImageView =(CircleImageView)itemView.findViewById(R.id.thumbnail);
        UsernameTV =(TextView) itemView.findViewById(R.id.username);
        dateTV =(TextView) itemView.findViewById(R.id.date);
        tweetTV =(TextView) itemView.findViewById(R.id.tweets);
    }

    public void setCircleImageView(String img, Context context) {
        StorageReference ref = FirebaseStorage.getInstance().getReference().child("profilepics").child(img);
//        Glide.with(context).using(new FirebaseImageLoader()).load(ref).into(circleImageView);
        Glide.with(context)
//                        .using(new FirebaseImageLoader())
                         .load(ref)
                        .into(circleImageView);

    }

    public void setUsernameTV(String username) {
        UsernameTV.setText(username);
    }

    public void setDate(String date) {
       dateTV.setText(date);
    }

    public void setTweet(String tweet) {
        tweetTV.setText(tweet);
    }
}

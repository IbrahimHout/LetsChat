package com.ibrah.hout.letschat.Intities;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ibrah.hout.letschat.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by trest on 12/23/2017.
 */

public class UserViewHolder extends RecyclerView.ViewHolder {
    TextView userName;
    CircleImageView userImage;
    public UserViewHolder(View itemView) {
        super(itemView);
        userName = itemView.findViewById(R.id.user_display_name_tv);
        userImage = itemView.findViewById(R.id.user_display_imageview);

    }

    public TextView getUserName() {
        return userName;
    }

    public void setUserName(TextView userName) {
        this.userName = userName;
    }

    public CircleImageView getUserImage() {
        return userImage;
    }

    public void setUserImage(CircleImageView userImage) {
        this.userImage = userImage;
    }
}

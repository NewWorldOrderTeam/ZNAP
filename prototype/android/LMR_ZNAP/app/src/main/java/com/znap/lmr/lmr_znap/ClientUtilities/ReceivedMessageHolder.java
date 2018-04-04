package com.znap.lmr.lmr_znap.ClientUtilities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sendbird.android.UserMessage;
import com.znap.lmr.lmr_znap.R;

/**
 * Created by Andy Blyzniuk on 01.04.2018.
 */

class ReceivedMessageHolder extends RecyclerView.ViewHolder {
    TextView messageText, timeText, nameText;
    ImageView profileImage;
    private Context mContext;

    ReceivedMessageHolder(View itemView) {
        super(itemView);
        messageText = (TextView) itemView.findViewById(R.id.text_message_body);
        timeText = (TextView) itemView.findViewById(R.id.text_message_time);
        nameText = (TextView) itemView.findViewById(R.id.text_message_name);
        profileImage = (ImageView) itemView.findViewById(R.id.image_message_profile);
    }

    void bind(UserMessage message) {
        messageText.setText(message.getMessage());
        // Format the stored timestamp into a readable String using method.
        timeText.setText(DateUtils.formatDateTime(mContext, message.getCreatedAt(), 0));
        nameText.setText(message.getSender().getNickname());
        // Insert the profile image from the URL into the ImageView.
        //DateUtils.displayRoundImageFromUrl(mContext, message.getSender().getProfileUrl(), profileImage);
    }
}



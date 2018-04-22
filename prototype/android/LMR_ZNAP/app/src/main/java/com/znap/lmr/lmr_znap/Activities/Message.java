package com.znap.lmr.lmr_znap.Activities;

/**
 * Created by Andy Blyzniuk on 22.04.2018.
 */

public class Message {
    private String text;
    private MemberData data;
    private boolean belongsToCurrentUser;

    public Message(String text, MemberData data, boolean belongsToCurrentUser) {
        this.text = text;
        this.data = data;
        this.belongsToCurrentUser = belongsToCurrentUser;
    }

    public String getText() {
        return text;
    }

    public MemberData getData() {
        return data;
    }

    public boolean isBelongsToCurrentUser() {

        return belongsToCurrentUser;

    }
}

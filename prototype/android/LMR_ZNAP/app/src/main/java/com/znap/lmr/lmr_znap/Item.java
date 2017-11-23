package com.znap.lmr.lmr_znap;

/**
 * Created by Andy Blyzniuk on 21.11.2017.
 */

public class Item {
    public final int id;
    public final String first_name;
    public final String last_name;
    public final String middle_name;

    public Item(int id, String first_name, String last_name, String middle_name) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.middle_name = middle_name;

    }


}

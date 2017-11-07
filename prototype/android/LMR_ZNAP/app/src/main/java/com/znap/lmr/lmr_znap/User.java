package com.znap.lmr.lmr_znap;

import com.google.gson.annotations.Expose;

/**
 * Created by Andy Blyzniuk on 01.11.2017.
 */

public class User {
    @Expose
    private String email;
    @Expose
    private String password;
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private String middleName;
    @Expose
    private String telephoneNumber;
}


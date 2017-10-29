package com.project.andyblyzniuk.lmr_app;

/**
 * Created by Andy Blyzniuk on 29.10.2017.
 */

import com.google.gson.annotations.Expose;

public class User {
    @Expose
    private String email;
    @Expose
    private String password;
    @Expose
    private String firstName;
    @Expose
    private String lastName;
}


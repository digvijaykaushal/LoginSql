package com.example.dv.loginsql;

import android.widget.EditText;

/**
 * Created by dv on 02-Jan-16.
 */
public class Contact {
    String name,email,username,password;
    public Contact(String name, String email, String username, String password)
    {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
    }
    public Contact(String username, String password)
    {
        this.username = username;
        this.password = password;
    }
}

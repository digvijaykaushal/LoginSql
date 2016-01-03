package com.example.dv.loginsql;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dv on 02-Jan-16.
 */
public class LocalDatabase {
    public static final String SP_NAME = "UserDetails";
    SharedPreferences localDatabase;

    public LocalDatabase(Context context)
    {
        localDatabase = context.getSharedPreferences(SP_NAME,0);
    }
    public void storeData(Contact contact)
    {
        SharedPreferences.Editor spEditor = localDatabase.edit();
        spEditor.putString("Name",contact.name);
        spEditor.putString("Email",contact.email);
        spEditor.putString("Username",contact.username);
        spEditor.putString("Password",contact.password);
        spEditor.commit();
    }

    public Contact getLoggedInUser()
    {
        String name = localDatabase.getString("Name","");
        String email = localDatabase.getString("Email","");
        String username = localDatabase.getString("Username","");
        String password = localDatabase.getString("Password","");
        Contact stotedContact = new Contact(name,email,username,password);
        return stotedContact;
    }
    public void setUserLoggedIn(boolean loggedIn)
    {
        SharedPreferences.Editor spEditor = localDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }
    public boolean getUserLoggedIn()
    {
        if(localDatabase.getBoolean("loggedIn",false))
            return true;
        else
            return false;
    }
    public void clearData()
    {
        SharedPreferences.Editor spEditor = localDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }
}

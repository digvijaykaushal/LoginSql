package com.example.dv.loginsql;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by dv on 02-Jan-16.
 */
public class Display extends ActionBarActivity {
    private TextView Email,Password,Username,Name;
LocalDatabase localDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_info);
        Name = (TextView)findViewById(R.id.tvname);
        Email = (TextView)findViewById(R.id.tvemail);
        Username = (TextView)findViewById(R.id.tvuname);
        Password = (TextView)findViewById(R.id.tvpass);
        localDatabase = new LocalDatabase(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(authenticate()==true)
        {
            displayContactDetails();
        }
        else
        {
            Intent intent = new Intent(Display.this,LoginActivity.class);
            startActivity(intent);
        }
    }

    private void displayContactDetails() {
        Contact contact = localDatabase.getLoggedInUser();
        Name.setText(contact.name);
        Email.setText(contact.email);
        Username.setText(contact.username);
        Password.setText(contact.password);
    }

    private boolean authenticate() {

        return localDatabase.getUserLoggedIn();
    }

    public void onLogoutClick(View view)
    {
        localDatabase.clearData();
        localDatabase.setUserLoggedIn(false);
        Intent intent = new Intent(Display.this,LoginActivity.class);
        startActivity(intent);
    }
}

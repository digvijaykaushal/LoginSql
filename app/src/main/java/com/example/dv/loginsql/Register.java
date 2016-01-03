package com.example.dv.loginsql;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by dv on 01-Jan-16.
 */
public class Register extends ActionBarActivity {
  private EditText Email,Password,Username,Name,ConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Name = (EditText)findViewById(R.id.name);
        Email = (EditText)findViewById(R.id.email);
        Username = (EditText)findViewById(R.id.uname);
        Password = (EditText)findViewById(R.id.pass);
        ConfirmPassword = (EditText)findViewById(R.id.cpass);

    }
    public void onLoginClick(View view)
    {
        Intent intent = new Intent(Register.this, LoginActivity.class);
        startActivity(intent);
    }

    public void onButtonClick(View view)
    {
        //Toast.makeText(Register.this, "Please ", Toast.LENGTH_LONG).show();
       String name = Name.getText().toString();
        String email = Email.getText().toString();
        String uname = Username.getText().toString();
        String pass = Password.getText().toString();
        String cpass = ConfirmPassword.getText().toString();
        Contact contact;
        if(name.equals("") || email.equals("") || uname.equals("") || pass.equals("") || cpass.equals(""))
            Toast.makeText(Register.this, "Please enter all details ", Toast.LENGTH_SHORT).show();
        else if(pass.equals(cpass)==false)
            Toast.makeText(Register.this, "Password Mismatched", Toast.LENGTH_SHORT).show();
        else
        {
            contact = new Contact(name,email,uname,pass);
            ServerRequest serverRequest = new ServerRequest(this);
            serverRequest.storeDataBackground(contact, new GetUserCallback() {
                @Override
                public void done(Contact returnContact) {
                    Intent intent = new Intent(Register.this,LoginActivity.class);
                    startActivity(intent);

                }
            });
        }
    }
}

package com.example.dv.loginsql;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends ActionBarActivity{
private EditText username,Pass;
    LocalDatabase localDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText)findViewById(R.id.loginpass);
        Pass = (EditText)findViewById(R.id.password);
        localDatabase = new LocalDatabase(this);
    }

    public void onLoginButtonClick(View view)
    {
String name = username.getText().toString();
        String password = Pass.getText().toString();
        if(name.equals("") || password.equals(""))
            Toast.makeText(LoginActivity.this,"Enter all fields",Toast.LENGTH_LONG).show();
        else
        {
            Contact contact = new Contact(name, password);
            authenticate(contact);
        }
    }

    private void authenticate(Contact contact) {
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.fetchDataBackground(contact, new GetUserCallback() {
            @Override
            public void done(Contact returnContact) {
                if(returnContact==null)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage("Username or password Mismatched");
                    builder.setPositiveButton("OK",null);
                    builder.show();
                }
                else
                {
                    localDatabase.storeData(returnContact);
                    localDatabase.setUserLoggedIn(true);
                    Intent intent = new Intent(LoginActivity.this,Display.class);
                    startActivity(intent);
                }
            }
        });
    }


    public void onRegisterClick(View view)
    {
        Intent intent = new Intent(LoginActivity.this, Register.class);
        startActivity(intent);
    }

}


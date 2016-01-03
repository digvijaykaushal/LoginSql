package com.example.dv.loginsql;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
//import org.apache.commons.Httpclient.NameValuePair
//import org.apache.http.NameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import java.util.ArrayList;

import static org.apache.http.params.HttpConnectionParams.*;

/**
 * Created by dv on 02-Jan-16.
 */
public class ServerRequest {
    ProgressDialog progressDialog;
            public static final int CONNECTION_TIMEOUT = 15000;
            public static final String SERVER_ADDRESS = "http://dvproject.netau.net/";

    public ServerRequest(Context context)
    {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please wait ...");
    }

    public void storeDataBackground(Contact contact, GetUserCallback callback)
    {
        progressDialog.show();
        new StoreDataAsyncTask(contact,callback).execute();
    }

    public void fetchDataBackground(Contact contact, GetUserCallback callback)
    {
        progressDialog.show();
        new FetchDataAsyncTask(contact,callback).execute();
    }

    public class StoreDataAsyncTask extends AsyncTask<Void, Void, Void>
    {
         Contact contact;
         GetUserCallback callback;
        public StoreDataAsyncTask(Contact contact, GetUserCallback callback)
        {
            this.contact = contact;
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ContentValues values=new ContentValues();
            values.put("Name", contact.name);
            values.put("Email", contact.email);
            values.put("Username", contact.username);
            values.put("Password", contact.password);
            HttpParams httpRequestParam;
            httpRequestParam = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParam, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParam, CONNECTION_TIMEOUT);

            HttpClient client = new HttpClient(httpRequestParam);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "register.php");

            try {
                post.setEntity(new UrlEncodedFormEntity(values));
                client.execute(post);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            callback.done(null);
            super.onPostExecute(aVoid);
        }
    }
    public class FetchDataAsyncTask extends AsyncTask<Void,Void,Contact>
    {
        Contact contact;
        GetUserCallback callback;
        public FetchDataAsyncTask(Contact contact, GetUserCallback callback)
        {
            this.contact = contact;
            this.callback = callback;
        }

        @Override
        protected Contact doInBackground(Void... voids) {
            ContentValues values=new ContentValues();
            values.put("Username", contact.username);
            values.put("Password", contact.password);

            HttpParams httpRequestParam;
            httpRequestParam = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParam, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParam, CONNECTION_TIMEOUT);

            HttpClient client = new HttpClient(httpRequestParam);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "fetch_data.php");
            Contact returnedContact = null;
            try {
                post.setEntity(new UrlEncodedFormEntity(values));
                HttpResponse httpResponse = client.execute(post);
                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject jsonObject = new JSONObject(result);
                if(jsonObject.length()==0)
                {
                    returnedContact=null;
                }
                else
                {
                   String name,email;
                    name=null;
                    email=null;
                    if(jsonObject.has("name"))
                    {
                        name = jsonObject.getString("name");
                    }
                    if(jsonObject.has("email"))
                    {
                        email = jsonObject.getString("email");
                    }
                    returnedContact = new Contact(name,email,contact.username,contact.password);

                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return returnedContact;
        }

        @Override
        protected void onPostExecute(Contact returnContact) {
            progressDialog.dismiss();
            callback.done(returnContact);
            super.onPostExecute(returnContact);
        }
    }

}

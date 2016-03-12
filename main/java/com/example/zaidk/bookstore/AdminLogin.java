package com.example.zaidk.bookstore;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zaidkurchied on 08/03/2016.
 */
public class AdminLogin extends Activity {
    private EditText editTextUserName;
    private EditText editTextPassword;
    String username;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login);



        editTextUserName = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        Button button = (Button) findViewById(R.id.btnLogin);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  invokeLogin();
                username = editTextUserName.getText().toString();
                password = editTextPassword.getText().toString();

                Context context = new Context(new OperationValidateEmail());
                System.out.println("do they match ? = " + context.executeStrategy(username, password));

                if (context.executeStrategy(username, password).equals("true")) {
                    login(username, password);
                    SingleLoggedInAdmin object = SingleLoggedInAdmin.getInstance();
                    //show the message
                    object.showMessage(username);

                }
                else{
                    Toast.makeText(getApplicationContext(), "Invalid email  or Password", Toast.LENGTH_LONG).show();

                }
            }
        });

    }

    private void login(final String username, String password) {

        class LoginAsync extends AsyncTask< String, Void, String > {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(AdminLogin.this, "Please wait", "Loading...");
            }

            @Override
            protected String doInBackground(String...params) {
                String uname = params[0];
                String pass = params[1];

                InputStream is = null;
                List< NameValuePair > nameValuePairs = new ArrayList< NameValuePair >();
                nameValuePairs.add(new BasicNameValuePair("userName", uname));
                nameValuePairs.add(new BasicNameValuePair("password", pass));
                String result = null;

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://10.0.2.2/info/login.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                String s = result.trim();
                loadingDialog.dismiss();
                if (s.equalsIgnoreCase("success")) {

                    Intent intent = new Intent(AdminLogin.this, AdminSide.class);
                    finish();
                    startActivity(intent);
                } else {
                Toast.makeText(getApplicationContext(), "Invalid User Name or Password", Toast.LENGTH_LONG).show();
                }
            }
        }

        LoginAsync la = new LoginAsync();
        la.execute(username, password);

    }



}

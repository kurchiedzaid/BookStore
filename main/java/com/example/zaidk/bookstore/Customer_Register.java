package com.example.zaidk.bookstore;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

/**
 * Created by zaidkurchied on 08/03/2016.
 */
public class Customer_Register extends Activity {
    RadioGroup group;
    String name;
    String email;
    String password;
    String address;
    String country;
    String Tel;
    RadioButton radioButton;
    String payment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_register);

        final EditText editNameText = (EditText) findViewById(R.id.name);
        final EditText editPasswordText = (EditText) findViewById(R.id.password);
        final EditText editEmailText = (EditText)findViewById(R.id.email);
        final EditText editAddressText = (EditText)findViewById(R.id.id);
        final EditText editCountryText = (EditText)findViewById(R.id.country);
        final EditText editTelText = (EditText) findViewById(R.id.Tel);
        final EditText editRePassword = (EditText) findViewById(R.id.password2);

        final Context context = new Context(new OperationValidateEmail());
        final Context contextPassword = new Context(new OperationValidatePassword());

        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serverURL = "http://10.0.2.2/Codes/AddBusiness.php";
                name = editNameText.getText().toString();
                email = editEmailText.getText().toString();
                password = editPasswordText.getText().toString();
                address = editAddressText.getText().toString();
                country = editCountryText.getText().toString();

                String password2 = editRePassword.getText().toString();

                group = (RadioGroup) findViewById(R.id.radioSex);
                int selectedId = group.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioButton = (RadioButton) findViewById(selectedId);
                payment= (String) radioButton.getText();

                Tel = editTelText.getText().toString();
                if(context.executeStrategy(email, password).equals("true") && contextPassword.executeStrategy(password2, password).equals("true")){
                    Toast.makeText(Customer_Register.this, "you have successfully registered ", Toast.LENGTH_LONG).show();
                    // Use AsyncTask execute Method To Prevent ANR Problem
                    new SummaryAsyncTask().execute((Void) null);
                } else {
                    Toast.makeText(Customer_Register.this, "Invalid email or missing text", Toast.LENGTH_LONG).show();
                }


            }
        });


    }

    class SummaryAsyncTask extends AsyncTask< Void, Void, Boolean > {

        private void postData(String name, String country, String email, String address, String password, String Tel, String payment) {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://10.0.2.2/info/customersReg.php");

            try {
                ArrayList< NameValuePair > nameValuePairs = new ArrayList < NameValuePair > (4);
                nameValuePairs.add(new BasicNameValuePair("name", name));
                nameValuePairs.add(new BasicNameValuePair("country", country));
                nameValuePairs.add(new BasicNameValuePair("email", email));
                nameValuePairs.add(new BasicNameValuePair("address", address));
                nameValuePairs.add(new BasicNameValuePair("password", password));
                nameValuePairs.add(new BasicNameValuePair("phone", Tel));
                nameValuePairs.add(new BasicNameValuePair("payment", payment));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
            } catch (Exception e) {
                Log.e("log_tag", "Error:  " + e.toString());
            }
        }

        @Override
        protected Boolean doInBackground(Void...params) {
            postData(name, country, email, address, password, Tel, payment);

//                Intent myIntent = new Intent(Customer_Register().this, BusinessLogin.class);
//                startActivity(myIntent);
            return null;

        }
    }

}

package com.example.zaidk.bookstore;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zaidkurchied on 20/03/2016.
 */
public class CustDetails  extends Activity{
    String myJSON;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_ID = "id";
    private static final String TAG_USERNAME= "name";
    private static final String TAG_EMAIL= "email";
    private static final String TAG_ADDRESS= "address";
    private static final String TAG_PHONE= "phone";
    private static final String TAG_PAYMENT= "payment";

    ImageButton sort;

    public String email="zaid";
    JSONArray books = null;
    ArrayList<HashMap<String, String>> booksList;
    TextView txt;
    ListView list;
    String phone;
    String type;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cust_details);
        //   txt = (TextView) findViewById(R.id.log);
        loadActivity();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //Get the only object available

        setTitle(null);

        final Intent intent = getIntent();
        username = intent.getStringExtra("user");
        System.out.print(username);
    }


    private void loadActivity() {
        // Do all of your work here
        list = (ListView) findViewById(R.id.listView);
        booksList = new ArrayList<HashMap<String, String>>();
        getData();


    }


    protected void showList() {

        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            books = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < books.length(); i++) {
                JSONObject c = books.getJSONObject(i);
                String id = c.getString(TAG_ID);
                String name = c.getString(TAG_USERNAME);
                String email = c.getString(TAG_EMAIL);
                String address = c.getString(TAG_ADDRESS);
                String phone = c.getString(TAG_PHONE);
                String payment = c.getString(TAG_PAYMENT);



if(username.equalsIgnoreCase(email)) {
    HashMap<String, String> persons = new HashMap<String, String>();
    persons.put(TAG_ID, id);
    persons.put(TAG_USERNAME, name);
    persons.put(TAG_EMAIL, email);
    persons.put(TAG_ADDRESS, address);
    persons.put(TAG_PHONE, phone);
    persons.put(TAG_PAYMENT, payment);


    //    persons.put(TAG_DATE,d);


    booksList.add(persons);
}

            }
            ListAdapter adapter = new SimpleAdapter(
                    CustDetails.this, booksList, R.layout.customer_list_item,
                    new String[]{
                            TAG_USERNAME,
                            TAG_ADDRESS,
                            TAG_PHONE,
                            TAG_PAYMENT,
                    },
                    new int[]{
                            R.id.title, R.id.Price, R.id.type,R.id.pay
                    }
            );

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {


                }
            });

            list.setAdapter(adapter);


            // list.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void getData() {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("http://10.0.2.2/info/allCust.php");

                // Depends on your web service
                httppost.setHeader("Content-type", "application/json");

                InputStream inputStream = null;
                String result = null;
                try {
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    inputStream = entity.getContent();
                    // json is UTF-8 by default
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (Exception e) {
                    // Oops
                } finally {
                    try {
                        if (inputStream != null) inputStream.close();
                    } catch (Exception squish) {
                    }
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();

    }




    ////////////////////Remove method//////////////////////////////



    static boolean active = false;

    @Override
    public void onStart() {
        super.onStart();
        // loadActivity();
        active = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onRestart() {

        super.onRestart();
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(CustDetails.this,AdminSide.class);
        startActivity(intent);
        finish();
    }

}
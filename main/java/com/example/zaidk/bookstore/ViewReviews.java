package com.example.zaidk.bookstore;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

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
 * Created by zaidkurchied on 19/03/2016.
 */
public class ViewReviews extends AppCompatActivity {

    private static final String TAG_RESULTS = "result";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "feedbackName";
    private static final String TAG_DESCRIPTION = "feedbackDescription";
    private static final String TAG_RATINGS = "ratings";

    private static final String TAG_R = "";

    float rating;
    Dialog rankDialog;

    public String email;
    JSONArray peoples = null;
    ArrayList< HashMap< String, String >> reviewList;
    TextView txt;
    ListView list;
    String phone;
    String myJSON;
    RatingBar ratingBar;
    String username;
    String feedbackDescription;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reviews_frag);
        getSupportActionBar().setTitle("Reviews");

        list = (ListView) findViewById(R.id.listView);
        reviewList = new ArrayList < HashMap < String, String >> ();

        final Intent intent = getIntent();

        username = intent.getStringExtra("user");
        getData();


    }


    protected void showList() {

        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);
                String id = c.getString(TAG_ID);
                String name = c.getString(TAG_NAME);
                feedbackDescription = c.getString(TAG_DESCRIPTION);
                String ratings = c.getString(TAG_RATINGS);

                HashMap < String, String > persons = new HashMap < String, String > ();
                persons.put(TAG_ID, id);
                persons.put(TAG_NAME, name);
                persons.put(TAG_DESCRIPTION, feedbackDescription);
                persons.put(TAG_RATINGS, ratings);


                //    persons.put(TAG_DATE,d);


                reviewList.add(persons);


            }

            ListAdapter adapter = new SimpleAdapter(
                    this, reviewList, R.layout.ratings_list_item,
                    new String[] {
                            TAG_DESCRIPTION,
                            TAG_NAME,
                            TAG_RATINGS
                    },
                    new int[] {
                            R.id.name, R.id.id, R.id.address
                    }
            );


            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView < ? > parent, View v, int position, long id) {

                    Map< String, String > map = reviewList.get(position);
                    String link = map.get("id");
                    String lName = map.get("feedbackName");
                    String lDescription = map.get("feedbackDescription");
                    String ratings = map.get("ratings");
                    float rating = Float.parseFloat(ratings);



                }
            });

            list.setAdapter(adapter);

            // list.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getData() {
        class GetDataJSON extends AsyncTask< String, Void, String > {

            @Override
            protected String doInBackground(String...params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("http://10.0.2.2/info/getFeedbacks.php");

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
                    } catch (Exception squish) {}
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


    public void onBackPressed() {

        Intent intent = new Intent(ViewReviews.this,UserSide.class);
        intent.putExtra("activity",  "userSide");
        intent.putExtra("user", username);
        startActivity(intent);
        finish();    }



}

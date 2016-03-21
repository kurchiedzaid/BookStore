package com.example.zaidk.bookstore;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
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
 * Created by zaidkurchied on 19/03/2016.
 */
public class AddReview extends AppCompatActivity {
    String feedbackName;
    String feedbackDescription;
    String ratings;
    String id = null;
    String name;

    String email;
    String type;
    private RatingBar ratingBar;
    String views;
    String username;
    String title;
    String author;
    String price;
    String idd;
    String stock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_review);
        final Intent intent = getIntent();
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        username = intent.getStringExtra("user");


        addListenerOnRatingBar();
        final EditText editNameText = (EditText) findViewById(R.id.feedbackName);
        final EditText editDescriptionText = (EditText) findViewById(R.id.feedbackDesc);
        editNameText.setText(intent.getStringExtra("name"));
        editNameText.setKeyListener(null);

        Button button = (Button) findViewById(R.id.add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serverURL = "http://10.0.2.2/Codes/AddReview.php";
                feedbackName = editNameText.getText().toString();
                ratings = String.valueOf(ratingBar.getRating());
                feedbackDescription = editDescriptionText.getText().toString();

                Intent i = getIntent();

                if (feedbackName.trim().length() != 0 && feedbackDescription.trim().length() != 0 && feedbackDescription.trim().length() > 20 && ratings != null) {

                    new SummaryAsyncTask().execute((Void) null);
                    onBackPressed();
                    Toast.makeText(getApplicationContext(), "Feedback success", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getApplicationContext(), "please fill all fields", Toast.LENGTH_LONG).show();
                }

                //     onBackPressed();

            }
        });


    }


    public void addListenerOnRatingBar() {

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                ratings = String.valueOf(rating);

            }
        });
    }

    class SummaryAsyncTask extends AsyncTask<Void, Void, Boolean> {

        private void postData(String feedbackName, String feedbackDescription, String ratings) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://10.0.2.2/info/AddReview.php");

            try {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
                nameValuePairs.add(new BasicNameValuePair("feedbackName", feedbackName));
                nameValuePairs.add(new BasicNameValuePair("feedbackDescription", feedbackDescription));
                nameValuePairs.add(new BasicNameValuePair("ratings", ratings));


                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);

            } catch (Exception e) {
                Log.e("log_tag", "Error:  " + e.toString());
            }
        }


        @Override
        protected Boolean doInBackground(Void... params) {
            postData(feedbackName, feedbackDescription, ratings);


            return null;

        }
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(AddReview.this,UserSide.class);
        intent.putExtra("activity",  "userSide");
        intent.putExtra("user", username);
        startActivity(intent);
        finish();

    }
}
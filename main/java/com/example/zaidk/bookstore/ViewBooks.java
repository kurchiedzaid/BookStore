package com.example.zaidk.bookstore;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
 * Created by zaidkurchied on 10/03/2016.
 */
public class ViewBooks extends Activity {
    String myJSON;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "title";
    private static final String TAG_AUTHOR = "author";
        private static final String TAG_PRICE = "price";
    private static final String TAG_STOCK = "stock";
    private static final String TAG_DESCRIPTION = "description";

ImageButton sort;

    public String email;
    JSONArray offers = null;
    ArrayList<HashMap<String, String>> offersList;
    TextView txt;
    ListView list;
    String phone;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_books);
        //   txt = (TextView) findViewById(R.id.log);
        loadActivity();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        setTitle(null);

        //  setSupportActionBar(toolbar);


    }


    private void loadActivity() {
        // Do all of your work here
        list = (ListView) findViewById(R.id.listView);
        offersList = new ArrayList<HashMap<String, String>>();
        getData(); //get business email



        sort = (ImageButton) findViewById(R.id.sort);
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(ViewBooks.this, sort);
                //Insflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.menu_main, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(
                                ViewBooks.this,
                                "You Clicked : " + item.getTitle(),
                                Toast.LENGTH_SHORT
                        ).show();

                        if (item.getTitle().equals("Recently added")) {
                            Intent intent = new Intent(ViewBooks.this, ViewBooks.class);
                            intent.putExtra("type", type);
                            getWindow().setWindowAnimations(0);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }


                        if (item.getTitle().equals("Popular")) {
                            Intent intent = new Intent(ViewBooks.this, ViewBooks.class);
                            intent.putExtra("type", type);
                            getWindow().setWindowAnimations(0);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }

                        if (item.getTitle().equals("All")) {
                            Intent intent = new Intent(ViewBooks.this, ViewBooks.class);
                            intent.putExtra("type", type);
                            getWindow().setWindowAnimations(0);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }

                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        }); //closing the setOnClickListener method



    }


    protected void showList() {

        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            offers = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < offers.length(); i++) {
                JSONObject c = offers.getJSONObject(i);
                String id = c.getString(TAG_ID);
                String name = c.getString(TAG_NAME);
                String author = c.getString(TAG_AUTHOR);
                String description = c.getString(TAG_DESCRIPTION);
                String price = c.getString(TAG_PRICE);
                String stock = c.getString(TAG_STOCK);





                    HashMap<String, String> persons = new HashMap<String, String>();
                    persons.put(TAG_ID, id);
                    persons.put(TAG_NAME, name);
                    persons.put(TAG_AUTHOR, author);
                    persons.put(TAG_DESCRIPTION, description);
                    persons.put(TAG_PRICE, price);
                    persons.put(TAG_STOCK, stock);


                    //    persons.put(TAG_DATE,d);


                    offersList.add(persons);


                }
                    ListAdapter adapter = new SimpleAdapter(
                            ViewBooks.this, offersList, R.layout.book_list_item,
                            new String[]{
                                    TAG_DESCRIPTION,
                                    TAG_NAME,
                                    TAG_AUTHOR,
                                    TAG_ID,
                                    TAG_PRICE,
                            },
                            new int[]{
                                    R.id.author, R.id.title, R.id.type
                            }
                    );

                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                            Map<String, String> map = offersList.get(position);
                            String link = map.get("id");
                            String lName = map.get("name");
                            String lLocation = map.get("author");
                            String lDescription = map.get("description");
                            String businessEmail = map.get("stock");
                            String businessPhone = map.get("price");


                            String businessId = map.get("restaurant_id");

                            Toast.makeText(getApplicationContext(),
                                    " " + lName + " Clicked", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ViewBooks.this, AddBook.class);
                            intent.putExtra("link", link);
                            intent.putExtra("name", lName);
                            intent.putExtra("offerLocation", lLocation);
                            intent.putExtra("offerDescription", lDescription);
                            intent.putExtra("business_email", businessEmail);
                            intent.putExtra("restaurant_id", businessId);
                            intent.putExtra("businessPhone", businessPhone);

                            finish();
                            startActivity(intent);


                        }
                    });

                    list.setAdapter(adapter);


            // list.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //  @Override
    //  protected void onResume() {
    //  super.onResume();

    //  Intent intent = new Intent(BusinessSide.this , BusinessSide.class);
    //  startActivity(intent);
    //  finish();

    //  }
    public void getData() {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("http://10.0.2.2/info/showBooks.php");

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
        // loadActivity();

    }

    @Override
    protected void onRestart() {
        //   loadActivity();
        //  Intent intent = new Intent(BusinessSide.this, BusinessSide.class);
        // BusinessSide.this.startActivity(intent);
        // finish();
        super.onRestart();
    }

    @Override
    public void onBackPressed() {
//        Intent intent = new Intent(BusinessSide.this, BusinessDash.class);
//        intent.putExtra("email", email);
//        startActivity(intent);
        finish();
    }

}
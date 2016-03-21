package com.example.zaidk.bookstore;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
 * Created by zaidkurchied on 12/03/2016.
 */
public class UserSide extends Activity {
    String myJSON;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "title";
    private static final String TAG_AUTHOR = "author";
    private static final String TAG_PRICE = "price";
    private static final String TAG_STOCK = "stock";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_TYPE= "type";

    ImageButton sort;

    public String email="zaid";
    JSONArray books = null;
    ArrayList<HashMap<String, String>> bookList;
    TextView txt;
    ListView list;
    String phone;
    String type;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_side_activ);
        //   txt = (TextView) findViewById(R.id.log);
        loadActivity();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //Get the only object available

        setTitle(null);

        final Intent intent = getIntent();

        username = intent.getStringExtra("user");
        //  setSupportActionBar(toolbar);

        final EditText search = (EditText) toolbar.findViewById(R.id.editSearch);
        ImageButton buttonSearch = (ImageButton) toolbar.findViewById(R.id.search);
        ImageButton cart = (ImageButton) toolbar.findViewById(R.id.cart);


        //spinner();
        buttonSearch.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        Log.v("EditText", search.getText().toString());
                        String value = search.getText().toString();

                        GrammerCorrection vt=new GrammerCorrection();

                        String postfix = vt.conversion(value);
//
                        System.out.println("Infix:   " + postfix);
                        System.out.println("UserName: " + username);
                        Intent intent = new Intent(UserSide.this, UserSideSearch.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("value", postfix);
                        intent.putExtra("user",username);
                        startActivity(intent);
                    }
                });


        //spinner();
        cart.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {

                        System.out.println("UserName: " + username);
                        Intent intent = new Intent(UserSide.this, Cart.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("user", username);
                        startActivity(intent);
                    }
                });
    }


    private void loadActivity() {
        // Do all of your work here
        list = (ListView) findViewById(R.id.listView);
        bookList = new ArrayList<HashMap<String, String>>();
        getData();



        sort = (ImageButton) findViewById(R.id.sort);
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(UserSide.this, sort);
                //Insflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.menu_main, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(
                                UserSide.this,
                                "You Clicked : " + item.getTitle(),
                                Toast.LENGTH_SHORT
                        ).show();

                        if (item.getTitle().equals("Recently added")) {
                            Intent intent = new Intent(UserSide.this, UserSideSearch.class);
                            intent.putExtra("type", "recent");
                            intent.putExtra("activity", "userSide");
                            intent.putExtra("user",username);
                            getWindow().setWindowAnimations(0);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }


                        if (item.getTitle().equals("Fiction")) {
                            Intent intent = new Intent(UserSide.this, UserSideSearch.class);
                            intent.putExtra("type", "fiction");
                            intent.putExtra("activity",  "userSide");
                            intent.putExtra("user",username);

                            getWindow().setWindowAnimations(0);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }

                        if (item.getTitle().equals("History")) {
                            Intent intent = new Intent(UserSide.this, UserSideSearch.class);
                            intent.putExtra("type", "history");
                            intent.putExtra("activity",  "userSide");
                            intent.putExtra("user",username);

                            getWindow().setWindowAnimations(0);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                        if (item.getTitle().equals("Romance")) {
                            Intent intent = new Intent(UserSide.this, UserSideSearch.class);
                            intent.putExtra("type", "romance");
                            intent.putExtra("activity",  "userSide");
                            intent.putExtra("user",username);

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
            books = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < books.length(); i++) {
                JSONObject c = books.getJSONObject(i);
                String id = c.getString(TAG_ID);
                String name = c.getString(TAG_NAME);
                String author = c.getString(TAG_AUTHOR);
                String description = c.getString(TAG_DESCRIPTION);
                String price = c.getString(TAG_PRICE);
                String stock = c.getString(TAG_STOCK);
                String type = c.getString(TAG_TYPE);




                HashMap<String, String> persons = new HashMap<String, String>();
                persons.put(TAG_ID, id);
                persons.put(TAG_NAME, name);
                persons.put(TAG_AUTHOR, author);
                persons.put(TAG_DESCRIPTION, description);
                persons.put(TAG_PRICE, price);
                persons.put(TAG_STOCK, stock);
                persons.put(TAG_TYPE, type);


                //    persons.put(TAG_DATE,d);


                bookList.add(persons);


            }
            ListAdapter adapter = new SimpleAdapter(
                    UserSide.this, bookList, R.layout.book_list_item,
                    new String[]{
                            TAG_DESCRIPTION,
                            TAG_NAME,
                            TAG_PRICE,
                            TAG_AUTHOR,
                    },
                    new int[]{
                            R.id.Author, R.id.title, R.id.type,R.id.Price
                    }
            );

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                    Map<String, String> map = bookList.get(position);
                    String link = map.get("id");
                    String title = map.get("title");
                    String author = map.get("author");
                    String description = map.get("description");
                    String stock = map.get("stock");
                    String price = map.get("price");
                    String type = map.get("type");



                    Toast.makeText(getApplicationContext(),
                            " " + title + " Clicked", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UserSide.this, Book_Details_customer.class);
                    intent.putExtra("id", link);
                    intent.putExtra("name", title);
                    intent.putExtra("author", author);
                    intent.putExtra("description", description);
                    intent.putExtra("stock", stock);
                    intent.putExtra("price", price);
                    intent.putExtra("type", type);
                    intent.putExtra("user",username);


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

        super.onRestart();
    }

    private void doExit() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                UserSide.this);

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = getIntent();

                Intent intent = new Intent(UserSide.this,MainActivity.class);

                startActivity(intent);
                finish();
            }
        });

        alertDialog.setNegativeButton("No", null);

        alertDialog.setMessage("Are you sure you want to log out?");
        alertDialog.show();


    }
    @Override
    public void onBackPressed() {
        doExit();
    }

}
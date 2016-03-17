package com.example.zaidk.bookstore;

/**
 * Created by zaidkurchied on 15/03/2016.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
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
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by zaidkurchied on 12/03/2016.
 */
public class Cart extends Activity {
    String myJSON;
    LinkedList< String > linkedlist = new LinkedList < String > ();
    LinkedList< String > linkedlist2 = new LinkedList < String > ();
    LinkedList< Integer > linkedlist3 = new LinkedList < Integer> ();

    private static final String TAG_RESULTSBookmark = "result";

    private static final String TAG_RESULTS = "result";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "title";
    private static final String TAG_AUTHOR = "author";
    private static final String TAG_PRICE = "price";
    private static final String TAG_STOCK = "stock";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_BOOK_ID= "BookId";
    private static final String TAG_USER= "userName";
    private static final String TAG_TYPE= "type";
    String id;
    ImageButton sort;
    String stock;
    public String email="zaid";
    JSONArray offers = null;
    ArrayList<HashMap<String, String>> bookList;
    TextView txt;
    ListView list;
    String phone;
    String type;
    String username;
    TextView total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        //   txt = (TextView) findViewById(R.id.log);
        loadActivity();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //Get the only object available

        setTitle(null);

        final Intent intent = getIntent();
        total=(TextView) findViewById(R.id.total);
        username = intent.getStringExtra("user");

    }


    private void loadActivity() {
        // Do all of your work here
        list = (ListView) findViewById(R.id.listView);
        bookList = new ArrayList<HashMap<String, String>>();
        getCart();

        doTransaction(null,null);

    }

public void doTransaction(final String stock, final String id){
    Button pay = (Button) findViewById(R.id.pay);

    //spinner();
    pay.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View view) {
                    int y = Integer.parseInt(stock);
                    BookFunctions a = new BookFunctions();

                    int lv= y-1;
                    String stockS = Integer.toString(lv);
if(lv>0) {
    //  BookFunctions a = new BookFunctions();
    a.updateStock(stockS, id);

    a.removeCart();
}
                    else{
    Toast.makeText(getApplicationContext(), "Sorry this item is out of stock", Toast.LENGTH_LONG).show();

}
                }
            });
}
    protected void showList() {

        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            offers = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < offers.length(); i++) {
                JSONObject c = offers.getJSONObject(i);
                 id = c.getString(TAG_ID);
                String name = c.getString(TAG_NAME);
                String author = c.getString(TAG_AUTHOR);
                String description = c.getString(TAG_DESCRIPTION);
                String price = c.getString(TAG_PRICE);
                stock = c.getString(TAG_STOCK);
                String type = c.getString(TAG_TYPE);

                BookFunctions a = new BookFunctions();
                if (linkedlist.contains(username) && linkedlist2.contains(id)) {

                    HashMap<String, String> persons = new HashMap<String, String>();
                    persons.put(TAG_ID, id);
                    persons.put(TAG_NAME, name);
                    persons.put(TAG_AUTHOR, author);
                    persons.put(TAG_DESCRIPTION, description);
                    persons.put(TAG_PRICE, price);
                    persons.put(TAG_STOCK, stock);
                    persons.put(TAG_TYPE, type);
doTransaction(stock,id);
                    bookList.add(persons);
                }

            }
            ListAdapter adapter = new SimpleAdapter(
                    Cart.this, bookList, R.layout.book_list_item,
                    new String[]{
                            TAG_DESCRIPTION,
                            TAG_NAME,
                            TAG_PRICE,
                            TAG_AUTHOR,
                    },
                    new int[]{
                            R.id.author, R.id.title, R.id.type,R.id.author
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


                    String businessId = map.get("restaurant_id");

                    Toast.makeText(getApplicationContext(),
                            " " + title + " Clicked", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Cart.this, Book_Details_customer.class);
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

    protected void showCart() {

        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            offers = jsonObj.getJSONArray(TAG_RESULTSBookmark);

            for (int i = 0; i < offers.length(); i++) {
                JSONObject c = offers.getJSONObject(i);
                String id = c.getString(TAG_BOOK_ID);
                String user = c.getString(TAG_USER);
                String price = c.getString(TAG_PRICE);
                linkedlist3.add(Integer.valueOf(price));

                linkedlist.add(id);
                linkedlist2.add(user);


            }
            int sum = 0;

            for (int ii: linkedlist3) {
                sum += ii;
            }
            Log.d("sum", String.valueOf(sum));
            total.setText("Total of"+""+sum);
            getData();






            // list.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getCart() {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("http://10.0.2.2/info/showCart.php");

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
showCart();
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
        Intent intent = new Intent(Cart.this, UserSide.class);
        intent.putExtra("type", "recent");
        intent.putExtra("activity", "userSide");
        intent.putExtra("user", username);
        finish();
    }

}
package com.example.zaidk.bookstore;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
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
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zaidkurchied on 08/03/2016.
 * template
 */
public class BookFunctions extends Book {
    String title;
    String author;
    String price;
    String stock;
    String desc;
    String type;
    String id;
    String userName;
    String myJSON;
    String s = null;

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
    JSONArray offers = null;
    ArrayList<HashMap<String, String>> offersList;
    TextView txt;
    ListView list;

    public void addBook(String namelv, String authorlv, String pricelv,String stocklv,String desclv, String typelv) {

        title=namelv;
        author=authorlv;
        price=pricelv;
        stock=stocklv;
        desc=desclv;
        type=typelv;

        new SummaryAsyncTask().execute((Void) null);

        System.out.println("book is being added...");

        }
        public void updateStock(String lvStock, String lvID) {
            System.out.println("stock is updated");

            stock=lvStock;
            id=lvID;
         new SummaryAsyncTaskUpdate().execute((Void) null);

        }
        public void AddToCart(String bookId, String userId,String pricelv) {
            id=bookId;
            userName=userId;
            price=pricelv;
            System.out.println("alert if book has been sold");
            getData();
        }

    public void removeCart() {
        new transaction().execute((Void) null);

      remove();
    }

    //remove offer when invokeRemvoe is clicked using that offer id
    private void remove() {

        class LoginAsync extends AsyncTask < String, Void, String > {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String...params) {


                InputStream is = null;
                List< NameValuePair > nameValuePairs = new ArrayList < NameValuePair > ();
                String result = null;

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://10.0.2.2/info/removeCart.php");
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

            }
        }

        LoginAsync la = new LoginAsync();
        la.execute();

    }


    class SummaryAsyncTask extends AsyncTask<Void, Void, Boolean> {

        //  private void postData(String offerName,String offerType,String offerLocation,String offerDescription,String businessId,String businessEmail,String business_phone,String views) {
        private void postData(String title,String author, String price, String stock, String desc,String type) {
            System.out.println("book is being added inside" +price+author+title);

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://10.0.2.2/info/addBook.php");

            try {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
                nameValuePairs.add(new BasicNameValuePair("title",title));
                nameValuePairs.add(new BasicNameValuePair("price",price));
                nameValuePairs.add(new BasicNameValuePair("stock",stock));
                nameValuePairs.add(new BasicNameValuePair("author",author));
                nameValuePairs.add(new BasicNameValuePair("description",desc));
                nameValuePairs.add(new BasicNameValuePair("type",type));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);

            } catch (Exception e) {
                Log.e("log_tag", "Error:  " + e.toString());
            }
        }



        @Override
        protected Boolean doInBackground(Void... params) {
            postData(title,author,price,stock,desc,type);

            System.out.println("book is being added..." + price + author + title + type);

            return null;

        }
    }



    class SummaryAsyncTaskUpdate extends AsyncTask<Void, Void, Boolean> {

        private void postData(String stock,String id) {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://10.0.2.2/info/updateStock.php");

            try {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
                nameValuePairs.add(new BasicNameValuePair("stock",stock));
                nameValuePairs.add(new BasicNameValuePair("id",id));






                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);

            } catch (Exception e) {
                Log.e("log_tag", "Error:  " + e.toString());
            }
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            postData(stock, id);



            return null;

        }


    }

    class addToCart extends AsyncTask<Void, Void, Boolean> {

        //  private void postData(String offerName,String offerType,String offerLocation,String offerDescription,String businessId,String businessEmail,String business_phone,String views) {
        private void postData(String userName,String bookId,String price) {
            System.out.println("book is being added inside" +price+author+title);

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://10.0.2.2/info/addToCart.php");

            try {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
                nameValuePairs.add(new BasicNameValuePair("userName",userName));
                nameValuePairs.add(new BasicNameValuePair("BookId",bookId));
                nameValuePairs.add(new BasicNameValuePair("price",price));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);

            } catch (Exception e) {
                Log.e("log_tag", "Error:  " + e.toString());
            }
        }



        @Override
        protected Boolean doInBackground(Void... params) {
            postData(userName,id,price);

            System.out.println("post data" + userName + id + price);

            return null;

        }
    }


    class transaction extends AsyncTask<Void, Void, Boolean> {

        //  private void postData(String offerName,String offerType,String offerLocation,String offerDescription,String businessId,String businessEmail,String business_phone,String views) {
        private void postData(String userName,String bookId,String price) {
            System.out.println("book is being added inside" +price+author+title);

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://10.0.2.2/info/transaction.php");

            try {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
                nameValuePairs.add(new BasicNameValuePair("userName",userName));
                nameValuePairs.add(new BasicNameValuePair("BookId",bookId));
                nameValuePairs.add(new BasicNameValuePair("price",price));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);

            } catch (Exception e) {
                Log.e("log_tag", "Error:  " + e.toString());
            }
        }



        @Override
        protected Boolean doInBackground(Void... params) {
            postData(userName,id,price);

            System.out.println("post data" + userName+id+price);

            return null;

        }
    }

    protected void showList() {

        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            offers = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < offers.length(); i++) {
                JSONObject c = offers.getJSONObject(i);
                String idd = c.getString(TAG_ID);
                String name = c.getString(TAG_NAME);
                String author = c.getString(TAG_AUTHOR);
                String description = c.getString(TAG_DESCRIPTION);
                String pricelv = c.getString(TAG_PRICE);
                String stock = c.getString(TAG_STOCK);
                String type = c.getString(TAG_TYPE);
if(id.equals(idd)) {
    //   price=pricelv+1;
    int foo = Integer.parseInt(pricelv);
    int resultInt = foo + 1;
    price = Integer.toString(resultInt);

}
            }
            new addToCart().execute((Void) null);


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





}


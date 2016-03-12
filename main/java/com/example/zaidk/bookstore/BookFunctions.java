package com.example.zaidk.bookstore;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

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
        public void alert() {
            System.out.println("alert if book has been sold");
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


    }


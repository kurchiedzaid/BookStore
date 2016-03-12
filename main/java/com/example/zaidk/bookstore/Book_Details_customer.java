package com.example.zaidk.bookstore;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by zaidkurchied on 12/03/2016.
 */
public class Book_Details_customer extends Activity {
    String title;
    String author;
    String price;
    String stock;
    String Description;
    String type;
    String idd;
    private TextView txtTitle;
    private TextView txtAuthor;
    private TextView txtPrice;
    private TextView txtStock;
    private TextView txtDesc;
    private TextView txtType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_desc_customer);
        txtTitle = (TextView) findViewById(R.id.title);
        txtAuthor = (TextView) findViewById(R.id.author);
        txtDesc = (TextView) findViewById(R.id.desc);
        txtPrice = (TextView) findViewById(R.id.price);
        txtType = (TextView) findViewById(R.id.type);
        txtStock = (TextView) findViewById(R.id.stock);

        final Intent intent = getIntent();

        title = intent.getStringExtra("name");
        Description = intent.getStringExtra("description");
        author = intent.getStringExtra("author");
        price = intent.getStringExtra("price");
        type = intent.getStringExtra("type");
        idd = intent.getStringExtra("id");
        stock = intent.getStringExtra("stock");
        stock = "100";
        txtTitle.setText(title);
        txtAuthor.setText(author);
        txtPrice.setText(price);
        txtType.setText(type);
        txtStock.setText(stock);
        txtDesc.setText(Description);


    }



    @Override
    public void onBackPressed() {
        Intent i = getIntent();

        Intent intent = new Intent(Book_Details_customer.this,UserSide.class);

        startActivity(intent);
        finish();
    }

}

package com.example.zaidk.bookstore;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
    String username;

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

        username = intent.getStringExtra("user");
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
        Log.d("usern", username);
        Log.d("id", idd);


        Button view = (Button) findViewById(R.id.button);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BookFunctions a = new BookFunctions();
                a.AddToCart(username, idd,price);
            }
        });

    }



    @Override
    public void onBackPressed() {
        Intent i = getIntent();
        Intent intent = new Intent(Book_Details_customer.this, UserSide.class);
        intent.putExtra("type", "recent");
        intent.putExtra("activity", "userSide");
        intent.putExtra("user", username);

        startActivity(intent);
        finish();
    }

}

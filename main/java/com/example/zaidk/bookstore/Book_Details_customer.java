package com.example.zaidk.bookstore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
        txtAuthor = (TextView) findViewById(R.id.Price);
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
                a.AddToCart(username, idd, price);
                Toast.makeText(
                       Book_Details_customer.this,
                        "Added to cart: " + "",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        Button feedback = (Button) findViewById(R.id.feedback);

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Book_Details_customer.this, AddReview.class);
                intent.putExtra("name", title);
                intent.putExtra("user", username);

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });
        Button viewReviews = (Button) findViewById(R.id.viewR);

        viewReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Book_Details_customer.this, ViewReviews.class);
                intent.putExtra("name", title);
                intent.putExtra("user", username);

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

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

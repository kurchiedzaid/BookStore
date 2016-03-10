package com.example.zaidk.bookstore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by zaidkurchied on 08/03/2016.
 */
public class AddBook extends Activity {
    String title;
    String author;
    String stock;
    String price;
    String desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_book);


        final EditText editTitle = (EditText) findViewById(R.id.title);
        final EditText editAuthor = (EditText) findViewById(R.id.author);
        final EditText editPrice = (EditText) findViewById(R.id.price);
        final EditText editStock= (EditText) findViewById(R.id.stock);
        final EditText editdesc= (EditText) findViewById(R.id.desc);

        //Add offer when button is clicked  and send  business id and business email to business side
        Button button = (Button) findViewById(R.id.btnOffer);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serverURL = "http://10.0.2.2/Codes/AddOffer.php";
                title = editTitle.getText().toString();
                author = editAuthor.getText().toString();
                price = editPrice.getText().toString();
                stock = editStock.getText().toString();
                desc = editdesc.getText().toString();


                if (title.trim().length()!=0 &&author.trim().length()!=0   && stock!=null ){

                    BookFunctions a = new BookFunctions();
                    a.addBook(title,author,price,stock,desc);
                    // Use AsyncTask execute Method To Prevent ANR Problem
                }else{
                    Toast.makeText(getApplicationContext(), "please fill in all fields", Toast.LENGTH_LONG).show();
                }

                //     onBackPressed();

            }
        });
        //example

    }
}

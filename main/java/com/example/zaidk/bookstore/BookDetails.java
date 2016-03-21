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
 * Created by zaidkurchied on 11/03/2016.
 */
public class BookDetails extends Activity {
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
        setContentView(R.layout.book_desc);
        txtTitle = (TextView) findViewById(R.id.title);
        txtAuthor = (TextView) findViewById(R.id.Price);
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

        ImageButton view = (ImageButton) findViewById(R.id.update);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            showInputDialog();
            }
        });
    }


    protected void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(BookDetails.this);
        View promptView = layoutInflater.inflate(R.layout.update_stock, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BookDetails.this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    stock= String.valueOf(editText.getText());

                        BookFunctions a = new BookFunctions();
                        a.updateStock(stock, idd);
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        Intent i = getIntent();

        Intent intent = new Intent(BookDetails.this,AdminSide.class);

        startActivity(intent);
        finish();
    }

}

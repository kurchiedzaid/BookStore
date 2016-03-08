package com.example.zaidk.bookstore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by zaidkurchied on 08/03/2016.
 */
public class AdminSide extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_side);

       Button button = (Button) findViewById(R.id.add);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(AdminSide.this, AddBook.class);
//                finish();
//                startActivity(intent);
                BookFunctions a = new BookFunctions();
                a.addBook("testbitch");
            }
        });

    }
}

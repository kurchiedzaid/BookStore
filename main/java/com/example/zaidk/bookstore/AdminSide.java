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
        Admin a= new Admin();
        a.getName();
        System.out.print("admin logged in"+a);
       Button button = (Button) findViewById(R.id.add);

        Button view = (Button) findViewById(R.id.view);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(AdminSide.this, AddBook.class);
               intent.putExtra("activity",  "admin");

               finish();
               startActivity(intent);

            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminSide.this, ViewBooks.class);
                intent.putExtra("activity",  "userSide");

                finish();
                    startActivity(intent);

}
});

        }


    @Override
    public void onBackPressed() {
//        Intent i = getIntent();
//
//        Intent intent = new Intent(AddBook.this,AdminSide.class);
//
//        startActivity(intent);
        finish();
    }

}

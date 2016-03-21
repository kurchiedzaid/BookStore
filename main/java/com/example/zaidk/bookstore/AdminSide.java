package com.example.zaidk.bookstore;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
        System.out.print("admin logged in" + a);
       Button button = (Button) findViewById(R.id.add);

        Button view = (Button) findViewById(R.id.view);
        Button trans = (Button) findViewById(R.id.trans);

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
        trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminSide.this, Transactions.class);
                intent.putExtra("activity",  "userSide");

                finish();
                startActivity(intent);

            }
        });

        }

    private void doExit() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                AdminSide.this);

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(AdminSide.this, MainActivity.class);

                startActivity(intent);
                finish();
            }
        });

        alertDialog.setNegativeButton("No", null);

        alertDialog.setMessage("Are you sure you want to log out?");
        alertDialog.show();


    }
    @Override
    public void onBackPressed() {

        doExit();
    }

}

package com.example.zaidk.bookstore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by zaidkurchied on 08/03/2016.
 */
public class AddBook extends AppCompatActivity {
    String title;
    String author;
    String stock;
    String price;
    String desc;
    private RadioGroup radioTypeGroup;
    private RadioButton radioTypeButton;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_book);
        getSupportActionBar().setTitle("Add Book");


        final EditText editTitle = (EditText) findViewById(R.id.title);
        final EditText editAuthor = (EditText) findViewById(R.id.Author);
        final EditText editPrice = (EditText) findViewById(R.id.Price);
        final EditText editStock = (EditText) findViewById(R.id.stock);
        final EditText editdesc = (EditText) findViewById(R.id.desc);
        Button button = (Button) findViewById(R.id.add);

// This will get the radiogroup
        RadioGroup rGroup = (RadioGroup) findViewById(R.id.radioType);
// This will get the radiobutton in the radiogroup that is checked
        RadioButton checkedRadioButton = (RadioButton) rGroup.findViewById(rGroup.getCheckedRadioButtonId());


        // This overrides the radiogroup onCheckListener
        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup rGroup, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton) rGroup.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    Toast.makeText(getApplicationContext(), checkedRadioButton.getText(),
                            Toast.LENGTH_SHORT).show();

                    if (checkedRadioButton.getText().equals("romance")) {
                        String ty = "romance";
                        type = ty;
                        Toast.makeText(getApplicationContext(), "Romance",
                                Toast.LENGTH_SHORT).show();

                    }
                    if (checkedRadioButton.getText().equals("history")) {
                        String ty = "history";
                        type = ty;
                        Toast.makeText(getApplicationContext(), "History",
                                Toast.LENGTH_SHORT).show();


                    }
                    if (checkedRadioButton.getText().equals("fiction")) {
                        String ty = "fiction";
                        type = ty;
                        Toast.makeText(getApplicationContext(), "Fiction",
                                Toast.LENGTH_SHORT).show();

                    }

                }
            }
        });


        // });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = editTitle.getText().toString();
                author = editAuthor.getText().toString();
                price = editPrice.getText().toString();
                stock = editStock.getText().toString();
                desc = editdesc.getText().toString();


                if (title.trim().length() != 0 && author.trim().length() != 0 && stock != null) {

                    BookFunctions a = new BookFunctions();
                    a.addBook(title, author, price, stock, desc, type);

                    Intent intent = new Intent(AddBook.this, AdminSide.class);

                    startActivity(intent);
                    finish();
                    // Use AsyncTask execute Method To Prevent ANR Problem
                } else {
                    Toast.makeText(getApplicationContext(), "please fill in all fields", Toast.LENGTH_LONG).show();
                }

                //     onBackPressed();

            }
        });
        //example
    }



    @Override
    public void onBackPressed() {
        Intent i = getIntent();

        Intent intent = new Intent(AddBook.this,AdminSide.class);

        startActivity(intent);
        finish();
    }



}

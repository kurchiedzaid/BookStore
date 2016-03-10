package com.example.zaidk.bookstore;

/**
 * Created by zaidkurchied on 08/03/2016.
 */
public abstract class Book {

        public final void performAction(){
            addBook(null,null,null,null,null);
            updateStock();
            alert();
        }
        public abstract void addBook(String name,String author, String price,String stock,String desc);
        public abstract void updateStock();
        public abstract void alert();

    }


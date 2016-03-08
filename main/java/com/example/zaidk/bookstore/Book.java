package com.example.zaidk.bookstore;

/**
 * Created by zaidkurchied on 08/03/2016.
 */
public abstract class Book {

        public final void performAction(){
            addBook(null);
            updateStock();
            alert();
        }
        public abstract void addBook(String name);
        public abstract void updateStock();
        public abstract void alert();

    }


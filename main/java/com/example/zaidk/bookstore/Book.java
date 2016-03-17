package com.example.zaidk.bookstore;

/**
 * Created by zaidkurchied on 08/03/2016.
 */
public abstract class Book {

        public final void performAction(){
            addBook(null,null,null,null,null,null);
            updateStock(null,null);
            AddToCart(null,null,null);
        }
        public abstract void addBook(String name,String author, String price,String stock,String desc, String type);
        public abstract void updateStock(String stock, String id);
        public abstract void AddToCart( String bookId, String userId,String price);
        public abstract void removeCart();

    }


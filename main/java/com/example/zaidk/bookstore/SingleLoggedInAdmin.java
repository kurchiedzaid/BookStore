package com.example.zaidk.bookstore;

/**
 * Created by zaidkurchied on 11/03/2016.
 */
public class SingleLoggedInAdmin {

    private static SingleLoggedInAdmin instance = new SingleLoggedInAdmin();


    private SingleLoggedInAdmin(){}

    public static SingleLoggedInAdmin getInstance(){
        return instance;
    }

    public void showMessage(String name){
        System.out.println("Hello World!"+name);
        Admin a= new Admin();
        a.setName(name);
    }
}
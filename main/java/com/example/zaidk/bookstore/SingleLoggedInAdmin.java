package com.example.zaidk.bookstore;

/**
 * Created by zaidkurchied on 11/03/2016.
 */
public class SingleLoggedInAdmin {

    //create an object of SingleObject
    private static SingleLoggedInAdmin instance = new SingleLoggedInAdmin();

    //make the constructor private so that this class cannot be
    //instantiated
    private SingleLoggedInAdmin(){}

    //Get the only object available
    public static SingleLoggedInAdmin getInstance(){
        return instance;
    }

    public void showMessage(String name){
        System.out.println("Hello World!"+name);
        Admin a= new Admin();
        a.setName(name);
    }
}
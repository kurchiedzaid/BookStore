package com.example.zaidk.bookstore;

/**
 * Created by zaidkurchied on 08/03/2016.
 */
public class Context {
    private Strategy strategy;

    public Context(Strategy strategy){
        this.strategy = strategy;
    }

    public String executeStrategy(String num1, String num2){
        return strategy.doOperation(num1, num2);

    }
    public String executeStrategy2(String num1, String num2){
        return strategy.doOperationCheckString(num1, num2);

    }

}
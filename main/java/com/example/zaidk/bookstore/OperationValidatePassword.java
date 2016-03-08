package com.example.zaidk.bookstore;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zaidkurchied on 08/03/2016.
 */
public class OperationValidatePassword implements Strategy {
    String answer;
    String username;
    @Override
        public String doOperation(String num1, String num2) {
          username=num1;
            if(num1.equals(num2))
            {
                    answer="true";
            }
        else{
                answer="false";
            }
                return answer;
        }


}

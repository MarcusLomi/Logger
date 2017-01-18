package com.example.marcus.logger.Model;

import java.util.Calendar;

/**
 * Created by Marcus on 1/18/2017.
 */

public class Date {

    private Calendar cal;
    private String date;

    public Date(Calendar cal){
        this.cal = cal;
    }

    public String toString(){
        return this.cal.toString();
    }

}

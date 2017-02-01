package com.example.marcus.logger.Model;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by Marcus on 1/18/2017.
 */

public class Date implements Serializable {

    private static final long serialVersionUID = 1678758574943628287L;
    private Calendar cal;
    private String date;
    private int day;
    private int month;
    private int year;

    public Date(Calendar cal){
        this.cal = cal;
    }

    public String toString(){
        String result="";
        String[] months= {"Jan ", "Feb ", "Mar ", "Apr ", "May ",
                            "Jun ", "Jul ", "Aug ", "Sep ", "Oct ","Nov ","Dec "};
        result=months[this.month]+this.day+", "+this.year;
        return result;
    }
    public Date(int day, int month, int year){
        this.day=day;
        this.month=month;
        this.year=year;
    }

}

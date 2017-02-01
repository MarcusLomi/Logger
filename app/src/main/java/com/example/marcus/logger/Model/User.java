package com.example.marcus.logger.Model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Marcus on 1/18/2017.
 */

public class User {

    private String name;
    private ArrayList<Date> dates;

    public User(String s){
        this.name=s;
    }

    public ArrayList getDates(){
        return this.dates;
    }

    public boolean addDate(Calendar c){
        if(dates.contains(c)){
            return false;
        }
        else{
            Date d = new Date(c);
            this.dates.add(d);
        }
        return true;
    }

    public boolean addDate(Date d){
        if(dates.contains(d)) {
            return false;
        }
        else{
            this.dates.add(d);
        }
        return true;
    }

    public boolean removeDate(Calendar c){
        if(this.dates.contains(c)){
            this.dates.remove(c);
            return true;
        }
        return false;
    }

    public boolean removeDate(int index){
        if(this.dates.size()<index){
            this.dates.remove(index);
            return true;
        }
        return false;
    }

    public String[] getDatesStringArray(){
        String[] result = new String[this.dates.size()];
        int i = 0;
        for(Date d: this.dates){
            result[i]=d.toString();
            i++;
        }
        return result;
    }


}

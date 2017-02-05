package com.example.marcus.logger.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Marcus on 1/18/2017.
 */

public class User implements Serializable{

    private static final long serialVersionUID = 4138758574948434587L;
    private String name;
    private ArrayList<Date> dates;

    public User(String s){
        this.dates = new ArrayList<Date>();
        this.name=s;
    }

    public ArrayList getDates(){
        return this.dates;
    }

    public Date[] getDateArray(){
        Date[] result = new Date[this.dates.size()];
        int i =0;
        for(Date d: this.dates){
            result[i]=d;
            i++;
        }
        return result;
    }

    public String getName(){
        return this.name;
    }

    public boolean addDate(Date d){
        this.dates.add(d);

        return true;
    }

    public void deleteDate(Date d){
        this.dates.remove(d);
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

    public String[] getTimesStringArray(){
        String[] result = new String[this.dates.size()];
        int i = 0;
        for(Date d: this.dates){
            result[i]=d.getTime();
            i++;
        }
        return result;
    }

}

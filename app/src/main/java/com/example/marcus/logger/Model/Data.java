package com.example.marcus.logger.Model;

/**
 * Created by Marcus on 2/1/2017.
 */

import android.content.Context;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;



public class Data {

    private static Data _instance;
    private User user;

    private final String FILENAME = "user.ser";
    private Context context;

    private Data(Context context) {
        this.context = context;
        load();
    }

    private void load() {
        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            user = (User) ois.readObject();
            if (user == null) {
                user = new User("root");
                save();
            }
            ois.close();
        } catch (FileNotFoundException | EOFException e) {
            user = new User("root");
            save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            FileOutputStream dfos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            PrintWriter pw = new PrintWriter(dfos);
            pw.close(); // Empty out file
            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User getUser() {
        return user;
    }

    public static void init(Context context) {
        _instance = new Data(context);
    }

    public static Data getInstance() {
        return _instance;
    }
}


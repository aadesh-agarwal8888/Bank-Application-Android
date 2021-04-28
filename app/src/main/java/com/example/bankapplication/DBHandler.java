package com.example.bankapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.bankapplication.ui.User;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "customerDatabase";
    private static final String TABLE_CUSTOMER = "customer";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_BALANCE = "balance";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table customer(id integer primary key, name text, phone_number text, username text, password text, balance integer);");

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_PH_NO, user.getContact());
        values.put(KEY_USERNAME, user.getUsername());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put("balance", user.getBalance());

        db.insert(TABLE_CUSTOMER, null, values);

        db.close();
    }

    public User getUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from customer where username='" + username +"';";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
        } else {
            return  null;
        }

        User user = new User(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5));
        db.close();
        return user;
    }

    public boolean addBalance(String username, int amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select balance from customer where username='" + username + "';";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
        }
        int prevAmount = cursor.getInt(0);


        int totalAmount = prevAmount + amount;

        query = "update customer set balance=" + totalAmount + " where username='" + username + "';";
        db.execSQL(query);
        return true;
    }

    public boolean deductBalance(String username, int amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select balance from customer where username='" + username + "';";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
        }
        int prevAmount = cursor.getInt(0);

        if(prevAmount < amount) {
            return false;
        }
        int totalAmount = prevAmount - amount;

        query = "update customer set balance=" + totalAmount + " where username='" + username + "';";
        db.execSQL(query);

        return true;
    }
}

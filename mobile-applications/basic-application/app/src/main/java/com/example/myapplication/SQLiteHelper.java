package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "shoppingDB.db";
    public static final int VERSION = 1;

    public static final String TABLE_PRODUCTS = "products";

    public static final String TABLE_PRODUCTS_ID = "id";
    public static final String TABLE_PRODUCTS_NAME = "name";
    public static final String TABLE_PRODUCTS_QUANTITY = "quantity";
    public static final String TABLE_PRODUCTS_PRICE = "price";
    public static final String TABLE_PRODUCTS_BOUGHT = "bought";

    public static final String CREATE_TABLE_PRODUCTS = "CREATE TABLE " + TABLE_PRODUCTS + "(" +
            "'" + TABLE_PRODUCTS_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT," +
            "'" + TABLE_PRODUCTS_NAME + "' varchar(250) NOT NULL," +
            "'" + TABLE_PRODUCTS_QUANTITY + "' REAL NOT NULL," +
            "'" + TABLE_PRODUCTS_PRICE + "' REAL," +
            "'" + TABLE_PRODUCTS_BOUGHT + "' NUMERIC DEFAULT 0)";


    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PRODUCTS);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    public void insertProduct(Product product) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(TABLE_PRODUCTS_NAME, product.name);
        cv.put(TABLE_PRODUCTS_QUANTITY, product.quantity);
        cv.put(TABLE_PRODUCTS_PRICE, product.price);

        db.insert(TABLE_PRODUCTS, null, cv);

        db.close();
    }
}

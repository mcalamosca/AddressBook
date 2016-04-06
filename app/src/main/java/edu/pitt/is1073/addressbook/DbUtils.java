package edu.pitt.is1073.addressbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileNotFoundException;

/**
 * Created by Micah Calamosca on 4/3/2016.
 */
public class DbUtils extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "addressBook";
    public static final String TABLE_NAME = "mycontacts";
    public static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;
    private Cursor cursor;


    public static final String KEY_ROW_ID = "id";
    public static final String KEY_LASTNAME = "last_name";
    public static final String KEY_FIRSTNAME = "first_name";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_ADDRESS2 = "address2";
    public static final String KEY_CITY = "city";
    public static final String KEY_STATE = "state";
    public static final String KEY_ZIP = "zip";
    public static final String KEY_COUNTRY = "country";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_EMAIL = "email";


    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + "("+
            KEY_ROW_ID+" int(36) ,"+
            KEY_LASTNAME+"  varchar(100) not null,"+
            KEY_FIRSTNAME+"  varchar(100) not null,"+
            KEY_ADDRESS+"  varchar(100) not null,"+
            KEY_ADDRESS2+"  varchar(100) not null,"+
            KEY_CITY+"  varchar(100) not null,"+
            KEY_STATE+"  varchar(100) not null,"+
            KEY_ZIP+"  varchar(100) not null,"+
            KEY_COUNTRY+"  varchar(100) not null,"+
            KEY_PHONE+"  varchar(100),"+
            KEY_EMAIL+"  varchar(255) not null,"+
            "PRIMARY KEY (`"+KEY_ROW_ID+"`));";

    public DbUtils(Context context) throws FileNotFoundException {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        //db = context.openOrCreateDatabase(DATABASE_NAME, 0, null);

    }

    public void createRow(String tableName, ContentValues vals){
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(tableName, null, vals);
    }

    public void updateRow(String tableName, ContentValues vals){

    }

    public void deleteRow(long rowID){
        db.delete(TABLE_NAME, KEY_ROW_ID+"="+rowID,null);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //leave alone
    }

    public Cursor getResultSet(String sql){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(sql,null);
    }
}

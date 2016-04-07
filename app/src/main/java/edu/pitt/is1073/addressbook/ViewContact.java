package edu.pitt.is1073.addressbook;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ViewContact extends Activity {
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

    private TextView txtLastName;
    private TextView txtFirstName;
    private TextView txtAddress;
    private TextView txtAddress2;
    private TextView txtCity;
    private TextView txtState;
    private TextView txtZip;
    private TextView txtCountry;
    private TextView txtPhone;
    private TextView txtEmail;

    Contact contact = null;
    private String contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);


        txtLastName = (TextView) findViewById(R.id.txtLastName);
        txtFirstName = (TextView) findViewById(R.id.txtFirstName);
        txtAddress = (TextView) findViewById(R.id.txtAddress);
        txtAddress2 = (TextView) findViewById(R.id.txtAddress2);
        txtCity = (TextView) findViewById(R.id.txtCity);
        txtState = (TextView) findViewById(R.id.txtState);
        txtZip = (TextView) findViewById(R.id.txtZip);
        txtCountry = (TextView) findViewById(R.id.txtCountry);
        txtPhone = (TextView) findViewById(R.id.txtPhone);
        txtEmail = (TextView) findViewById(R.id.txtEmail);

        Intent intent = getIntent();

        //use intent to pass string to load user info if selected from listView
        if(intent.getBooleanExtra("loadContact",false)){
            //Assign contact id from Address List
            contactId = intent.getStringExtra("contactId");
            System.out.println(contactId);
            loadUserInfo();
        }
    }

    public void editContact(View v){
        Intent intent = new Intent(ViewContact.this,EditContact.class);
        intent.putExtra("contactId", contactId);
        intent.putExtra("loadContact", true);
        ViewContact.this.startActivity(intent);
    }

    public void returnHome(View v){
        Intent intent = new Intent(ViewContact.this,AddressList.class);

        ViewContact.this.startActivity(intent);
    }

    //Load user info from databse using userId
    protected void loadUserInfo(){
        //System.out.println(contactId);

        SqliteUtilities db = new SqliteUtilities(this);
        String sql = "SELECT id, last_name, first_name, address, address2, city," +
                " state, zip, country, phone, email " +
                "FROM mycontacts WHERE id =\"" + contactId + "\";";
        Cursor cursor = db.getResultSet(sql);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            contact = new Contact(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                    cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10));
        }
        txtLastName.setText(contact.getLastName());
        txtFirstName.setText(contact.getFirstName());
        txtAddress.setText(contact.getAddress());
        txtAddress2.setText(contact.getAddress2());
        txtCity.setText(contact.getCity());
        txtState.setText(contact.getState());
        txtZip.setText(contact.getZip());
        txtCountry.setText(contact.getCountry());
        txtPhone.setText(contact.getPhone());
        txtEmail.setText(contact.getEmail());

        db.close();
    }

}

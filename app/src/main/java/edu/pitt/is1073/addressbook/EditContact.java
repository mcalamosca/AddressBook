package edu.pitt.is1073.addressbook;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.UUID;

public class EditContact extends AppCompatActivity {
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

    private EditText editLastName;
    private EditText editFirstName;
    private EditText editAddress;
    private EditText editAddress2;
    private EditText editCity;
    private EditText editState;
    private EditText editZip;
    private EditText editCountry;
    private EditText editPhone;
    private EditText editEmail;

    private String contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);


        editLastName = (EditText) findViewById(R.id.txtLastName);
        editFirstName = (EditText) findViewById(R.id.txtFirstName);
        editAddress = (EditText) findViewById(R.id.txtAddress);
        editAddress2 = (EditText) findViewById(R.id.txtAddress2);
        editCity = (EditText) findViewById(R.id.txtCity);
        editState = (EditText) findViewById(R.id.txtState);
        editZip = (EditText) findViewById(R.id.txtZip);
        editCountry = (EditText) findViewById(R.id.txtCountry);
        editPhone = (EditText) findViewById(R.id.txtPhone);
        editEmail = (EditText) findViewById(R.id.txtEmail);

        editPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());


        Intent intent = getIntent();

        //use intent to pass string to load user info if selected from listView
        if(intent.getBooleanExtra("loadContact",false)){
            //Assign contact id from Address List
            contactId = intent.getStringExtra("contactId");
            System.out.println(contactId);
            loadUserInfo();
        }
    }

    public void submitContact(View v){
        saveUserInfo();
        Intent intent = new Intent(EditContact.this,AddressList.class);

        EditContact.this.startActivity(intent);
    }

    public void returnHome(View v){
        Intent intent = new Intent(EditContact.this,AddressList.class);

        EditContact.this.startActivity(intent);
    }

    public void deleteUser(View v){
        SqliteUtilities db = new SqliteUtilities(this);
        db.deleteRecord("mycontacts","id = ?", new String[]{contactId});
        db.close();
        Intent intent = new Intent(EditContact.this,AddressList.class);

        EditContact.this.startActivity(intent);
    }

    private void saveUserInfo(){
        ContentValues initialValues = new ContentValues();

        //Get strings from text fields
        String lastName = (String) editLastName.getText().toString();
        String firstName = (String) editFirstName.getText().toString();
        String address = (String) editAddress.getText().toString();
        String address2 = (String) editAddress2.getText().toString();
        String city = (String) editCity.getText().toString();
        String state = (String) editState.getText().toString();
        String zip = (String) editZip.getText().toString();
        String country = (String) editCountry.getText().toString();
        String phone = (String) editPhone.getText().toString();
        String email = (String) editEmail.getText().toString();

        //System.out.println(lastName + " " + firstName);

        //Put strings in ContentValues
        //userUUID.replaceAll("-", "");

        initialValues.put(KEY_FIRSTNAME,firstName);
        initialValues.put(KEY_LASTNAME,lastName);
        initialValues.put(KEY_ADDRESS,address);
        initialValues.put(KEY_ADDRESS2,address2);
        initialValues.put(KEY_CITY,city);
        initialValues.put(KEY_STATE,state);
        initialValues.put(KEY_ZIP,zip);
        initialValues.put(KEY_COUNTRY,country);
        initialValues.put(KEY_PHONE,phone);
        initialValues.put(KEY_EMAIL,email);

        SqliteUtilities db = new SqliteUtilities(this);
        db.updateRecord("mycontacts",initialValues,"id = ?",new String[]{contactId});
        db.close();

}

    //Load user info from databse using userId
    protected void loadUserInfo(){
        //System.out.println(contactId);
        Contact contact = null;

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
        editLastName.setText(contact.getLastName());
        editFirstName.setText(contact.getFirstName());
        editAddress.setText(contact.getAddress());
        editAddress2.setText(contact.getAddress2());
        editCity.setText(contact.getCity());
        editState.setText(contact.getState());
        editZip.setText(contact.getZip());
        editCountry.setText(contact.getCountry());
        editPhone.setText(contact.getPhone());
        editEmail.setText(contact.getEmail());

        db.close();
    }
}

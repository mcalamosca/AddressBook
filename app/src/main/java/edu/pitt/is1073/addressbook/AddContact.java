package edu.pitt.is1073.addressbook;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddContact extends AppCompatActivity {
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

    private Button btnSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        editLastName = (EditText) findViewById(R.id.txtLastName);
        editFirstName = (EditText) findViewById(R.id.txtFirstName);
        editAddress = (EditText) findViewById(R.id.txtAddress);
        editAddress2 = (EditText) findViewById(R.id.txtAddress2);
        editCity = (EditText) findViewById(R.id.txtCity);
        editState = (EditText) findViewById(R.id.txtState);
        editZip = (EditText) findViewById(R.id.txtZip);
        editCountry = (EditText) findViewById(R.id.txtCountry);
        editPhone = (EditText) findViewById(R.id.txtPhone);
        editEmail = (EditText) findViewById(R.id.txtAddress);

    }

    public void buttonOnClick(View v){
        saveUserInfo();
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


        /*
        KEY_LASTNAME
        KEY_FIRSTNAME
        KEY_ADDRESS
        KEY_ADDRESS2
        KEY_CITY
        KEY_STATE
        KEY_ZIP
        KEY_COUNTRY
        KEY_PHONE
        KEY_EMAIL
        */
    }
}

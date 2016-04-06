package edu.pitt.is1073.addressbook;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.UUID;

public class AddressList extends AppCompatActivity {

    private ListView lstContactList;
    private ArrayList<Contact> contactList;
    private ArrayList<String> contactNames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);

        initControls();
    }

    public void launchAddContact(View view){
        Intent intent = new Intent(AddressList.this,AddContact.class);

        AddressList.this.startActivity(intent);
    }

    private void initControls(){
        //
        lstContactList = (ListView) findViewById(R.id.lstContactList);

        contactList = new ArrayList<Contact>();
        contactNames = new ArrayList<>();

        String sql = "SELECT id, last_name, first_name, address, address2, city, state, zip, country, phone, email FROM mycontacts ORDER BY last_name";
        SqliteUtilities db = new SqliteUtilities(this);
        Cursor cursor = db.getResultSet(sql);
        while(cursor.moveToNext()){
            Contact contact = new Contact(cursor.getString(0), cursor.getString(2), cursor.getString(1),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                    cursor.getString(7), cursor.getString(8), cursor.getString(9),cursor.getString(10));
            contactList.add(contact);
            contactNames.add(contact.toString());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,contactNames);
        lstContactList.setAdapter(adapter);

        lstContactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Create contact according to position on listView
                Contact selectedContact = contactList.get(position);
                String selectedContactID = selectedContact.getId();

                //Get contact ID and send to EditContact
                Intent intent = new Intent(AddressList.this, EditContact.class);
                intent.putExtra("contactId", selectedContactID);
                intent.putExtra("loadContact", true);
                AddressList.this.startActivity(intent);


                Toast.makeText(getApplicationContext(), selectedContactID, Toast.LENGTH_LONG).show();
            }
        });
    }
    public void resetDatabase(View view){
        SqliteUtilities db = new SqliteUtilities(this);
        db.deleteTable();

        lstContactList.invalidateViews();
    }
}

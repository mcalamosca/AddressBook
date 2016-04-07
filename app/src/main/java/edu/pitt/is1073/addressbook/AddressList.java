package edu.pitt.is1073.addressbook;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;

public class AddressList extends AppCompatActivity {

    private ListView lstContactList;
    private ArrayList<Contact> contactList;
    private ArrayList<String> contactNames;
    private EditText txtSearch;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);

        initControls();
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    //reset list
                    initControls();
                } else {
                    //perform search
                    searchList(s.toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void searchList(String textToSearch){
        for(Iterator<String> it = contactNames.iterator(); it.hasNext();){
            String name = it.next();
            if(!name.contains(textToSearch)){
                it.remove();
            }
        }
        adapter.notifyDataSetChanged();

    }

    public void launchAddContact(View view){
        Intent intent = new Intent(AddressList.this,AddContact.class);

        AddressList.this.startActivity(intent);
    }

    private void initControls(){
        //Populate list with contacts in database
        lstContactList = (ListView) findViewById(R.id.lstContactList);
        txtSearch = (EditText) findViewById(R.id.txtSearch);
        contactList = new ArrayList<Contact>();
        contactNames = new ArrayList<String>();

        String sql = "SELECT id, last_name, first_name, address, address2, city, state, zip, country, phone, email FROM mycontacts ORDER BY last_name";
        SqliteUtilities db = new SqliteUtilities(this);
        Cursor cursor = db.getResultSet(sql);
        while(cursor.moveToNext()){
            Contact contact = new Contact(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                    cursor.getString(7), cursor.getString(8), cursor.getString(9),cursor.getString(10));
            contactList.add(contact);
            contactNames.add(contact.toString());
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,contactNames);
        lstContactList.setAdapter(adapter);

        lstContactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Create contact according to position on listView
                Contact selectedContact = contactList.get(position);
                String selectedContactID = selectedContact.getId();

                //Get contact ID and send to EditContact
                Intent intent = new Intent(AddressList.this, ViewContact.class);
                intent.putExtra("contactId", selectedContactID);
                intent.putExtra("loadContact", true);
                AddressList.this.startActivity(intent);


                //Toast.makeText(getApplicationContext(), selectedContactID, Toast.LENGTH_LONG).show();
            }
        });
    }
    public void resetDatabase(View view){
        final SqliteUtilities db = new SqliteUtilities(this);
        //Confirm delete user alert
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Are you sure?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        db.deleteTable();

                        finish();
                        startActivity(getIntent());
                        db.close();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert1 = builder1.create();
        alert1.show();
    }
}

package edu.pitt.is1073.addressbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddressList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);

    }

    public void launchAddContact(View view){
        Intent intent = new Intent(AddressList.this,AddContact.class);

        AddressList.this.startActivity(intent);
    }
}

package com.example.jerrywang.settleme;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class AddDebt extends ActionBarActivity {
    static final int REQUEST_SELECT_CONTACT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_debt);
        Intent intent = getIntent();

        ArrayList<String> nameCollection = new ArrayList<String>();
        ContentResolver cr = getContentResolver();

        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext())
        {
            String nameFromContacts = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            Log.d("nameFromContacts", nameFromContacts);
            nameCollection.add(nameFromContacts);
        }
        cursor.close();
        String[] names = new String[nameCollection.size()];
        nameCollection.toArray(names);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, names);
        AutoCompleteTextView nameField = (AutoCompleteTextView) findViewById(R.id.name_text);
        nameField.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_debt, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_accept:
                Intent intent = new Intent();

                AutoCompleteTextView nameField = (AutoCompleteTextView) findViewById(R.id.name_text);
                EditText amountField = (EditText) findViewById(R.id.amount_text);


                if (nameField.getText().toString().equals("") && amountField.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please specify a name and amount owed",
                            Toast.LENGTH_SHORT).show();
                }
                else if (nameField.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please specify a name",
                            Toast.LENGTH_SHORT).show();
                }
                else if (amountField.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please specify an amount owed",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    String name = nameField.getText().toString();
                    String amount = amountField.getText().toString();

                    intent.putExtra("name", name);
                    intent.putExtra("amount", amount);
                    setResult(100, intent);
                    finish();
                    return true;
                }

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addFromContacts(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_SELECT_CONTACT);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SELECT_CONTACT && resultCode == RESULT_OK) {
            Uri contactUri = data.getData();
            String contactName = null;
            Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);

            if (cursor.moveToFirst()) {

                // DISPLAY_NAME = The display name for the contact.
                // HAS_PHONE_NUMBER =   An indicator of whether this contact has at least one phone number.

                contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            }
            cursor.close();
            AutoCompleteTextView nameField = (AutoCompleteTextView) findViewById(R.id.name_text);
            nameField.setText(contactName);
        }
    }
}

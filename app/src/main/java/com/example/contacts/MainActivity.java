package com.example.contacts;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textViewContacts;
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonLoadContacts = findViewById(R.id.button);
        textViewContacts = findViewById(R.id.textView);

        buttonLoadContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadContacts();
            }
        });
    }

    private void loadContacts() {
        StringBuilder stringBuilder = new StringBuilder();

        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY);
            int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

            while (cursor.moveToNext()) {
                String name = nameIndex != -1 ? cursor.getString(nameIndex) : "No Name";
                String phoneNumber = phoneIndex != -1 ? cursor.getString(phoneIndex) : "No Phone Number";

                stringBuilder.append("Name: ").append(name).append("\n").append("Phone: ").append(phoneNumber).append("\n\n");
                count = count+1;
            }
            cursor.close();
            textViewContacts.setText(stringBuilder.toString());
            Log.i("Content Provider Demo",stringBuilder.toString());
        } else {
            Toast.makeText(this, "No contacts found", Toast.LENGTH_SHORT).show();
        }

        System.out.println("Total Count of Contacts: "+count);
    }

}
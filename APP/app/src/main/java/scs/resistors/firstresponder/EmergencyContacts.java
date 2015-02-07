package scs.resistors.firstresponder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class EmergencyContacts extends ActionBarActivity {

    private static final int CONTACT_PICKER_RESULT = 1001;
    private int currentIndex = 0;
    private int itemsDeleted = 3;
    private int itemsAdded = 0;
    private String[] nameArray = new String[3];
    private String[] phoneArray = new String[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contacts);

        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        TextView output1 = (TextView) findViewById(R.id.txt_output1);
        TextView output2 = (TextView) findViewById(R.id.txt_output2);
        TextView output3 = (TextView) findViewById(R.id.txt_output3);

        if (settings.contains("currentIndex")) {
            currentIndex = settings.getInt("currentIndex", currentIndex);
        }

        if (settings.contains("itemsAdded")) {
            itemsAdded = settings.getInt("itemsAdded", itemsAdded);
        }

        if (settings.contains("itemsDeleted")) {
            itemsDeleted = settings.getInt("itemsDeleted", itemsDeleted);
        }

        if (settings.contains("contact1name") && settings.contains("contact1phone")) {
            output1.setText(settings.getString("contact1name", "Name") +
                    "          Phone #: " +
                    settings.getString("contact1phone", ""));
            nameArray[0] = settings.getString("contact1name", "Name");
            phoneArray[0] = settings.getString("contact1phone", "");
        }
        if (settings.contains("contact2name") && settings.contains("contact2phone")) {
            output2.setText(settings.getString("contact2name", "Name") +
                    "          Phone #: " +
                    settings.getString("contact2phone", ""));
            nameArray[1] = settings.getString("contact2name", "Name");
            phoneArray[1] = settings.getString("contact2phone", "");
        }
        if (settings.contains("contact3name") && settings.contains("contact3phone")) {
            output3.setText(settings.getString("contact3name", "Name") +
                    "          Phone #: " +
                    settings.getString("contact3phone", ""));
            nameArray[2] = settings.getString("contact3name", "Name");
            phoneArray[2] = settings.getString("contact3phone", "");
        }

        Button deleteButton = (Button) findViewById(R.id.btn_delete_contact);
        if (itemsDeleted == 3)
            deleteButton.setVisibility(View.INVISIBLE);
        else
            deleteButton.setVisibility(View.VISIBLE);

        Button addButton = (Button) findViewById(R.id.btn_add_contacts);
        if (itemsAdded == 3) {
            addButton.setVisibility(View.INVISIBLE);
        }
        else
            addButton.setVisibility(View.VISIBLE);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_emergency_contacts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CONTACT_PICKER_RESULT:
                    Cursor cursor = null;
                    Cursor phoneCursor = null;
                    String name = "";
                    String phone = "";
                    try {
                        Uri result = data.getData();
                        Log.v("DEBUG_TAG", "Got a contact result: "
                                + result.toString());

                        // get the contact id from the Uri
                        String id = result.getLastPathSegment();

                        String whereName = ContactsContract.Data.MIMETYPE + " = ? AND " + ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID + " = ?";
                        String[] whereNameParams = new String[]{ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE, id};

                        cursor = getContentResolver().query(ContactsContract.Data.CONTENT_URI,
                                null, whereName, whereNameParams,
                                ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME);

                        phoneCursor = getContentResolver().query(Phone.CONTENT_URI,
                                null, Phone.CONTACT_ID + "=?", new String[]{id},
                                null);

                        int nameIdx = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

                        // let's just get the first email
                        if (cursor.moveToFirst()) {
                            name = cursor.getString(nameIdx);
                            Log.v("DEBUG_TAG", "Got name: " + name);
                        } else {
                            Log.w("DEBUG_TAG", "No results");
                        }

                        int phoneIdx = phoneCursor.getColumnIndex(Phone.DATA);

                        if (phoneCursor.moveToFirst()) {
                            phone = phoneCursor.getString(phoneIdx);
                            Log.v("DEBUG_TAG", "Got Phone: " + phone);
                        } else {
                            Log.w("DEBUG_TAG", "No results");
                        }
                    } catch (Exception e) {
                        Log.e("DEBUG_TAG", "Failed to get data", e);
                    } finally {
                        if (cursor != null) {
                            cursor.close();
                        }
                        if (phoneCursor != null) {
                            phoneCursor.close();
                        }
                        TextView output = (TextView) findViewById(R.id.txt_output1);
                        itemsDeleted--;
                        switch (currentIndex) {
                            case 0:
                                output = (TextView) findViewById(R.id.txt_output1);
                                currentIndex++;
                                itemsAdded++;
                                break;
                            case 1:
                                output = (TextView) findViewById(R.id.txt_output2);
                                currentIndex++;
                                itemsAdded++;
                                break;
                            case 2:
                                output = (TextView) findViewById(R.id.txt_output3);
                                currentIndex = 0;
                                itemsAdded++;
                                break;
                            default:
                                break;
                        }
                        output.setText(name + "          Phone #: " + phone);
                        int pastIndex = currentIndex - 1;
                        if (currentIndex == 0) {
                            pastIndex = 2;
                        }
                        nameArray[pastIndex] = name;
                        phoneArray[pastIndex] = phone;

                        if (itemsAdded == 3) {
                            Button addButton = (Button) findViewById(R.id.btn_add_contacts);
                            addButton.setVisibility(View.INVISIBLE);
                        }
                        if (itemsAdded > 0) {
                            Button deleteButton = (Button) findViewById(R.id.btn_delete_contact);
                            deleteButton.setVisibility(View.VISIBLE);
                        }
                        if (name.length() == 0) {
                            Toast.makeText(this, "No name found for contact.",
                                    Toast.LENGTH_LONG).show();
                        }

                    }

                    break;
            }

        } else {
            Log.w("DEBUG_TAG", "Warning: activity result not ok");
        }
    }

    public void doLaunchContactPicker(View view) {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                Contacts.CONTENT_URI);
        startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
    }

    public void deleteContact(View view) {

        itemsAdded--;
        TextView output = (TextView) findViewById(R.id.txt_output1);
        if (currentIndex == 0)
            currentIndex = 2;
        else
            currentIndex--;
        switch (currentIndex) {
            case 0:
                output = (TextView) findViewById(R.id.txt_output1);
                break;
            case 1:
                output = (TextView) findViewById(R.id.txt_output2);
                break;
            case 2:
                output = (TextView) findViewById(R.id.txt_output3);
                break;
            default:
                break;
        }

        if (itemsAdded == 3) {
            Button addButton = (Button) findViewById(R.id.btn_add_contacts);
            addButton.setVisibility(View.INVISIBLE);
        }

        if (output.getText() != "None") {
            output.setText("None");
            nameArray[currentIndex] = null;
            phoneArray[currentIndex] = null;
            itemsDeleted++;
        }

        if (itemsDeleted > 0) {
            Button addButton = (Button) findViewById(R.id.btn_add_contacts);
            addButton.setVisibility(View.VISIBLE);
        }

        Button deleteButton = (Button) findViewById(R.id.btn_delete_contact);
        if (itemsDeleted == 3) {
            deleteButton.setVisibility(View.INVISIBLE);
        } else
            deleteButton.setVisibility(View.VISIBLE);

    }

    public void done(View view){
        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        if (nameArray[0] != "None") {
            editor.putString("contact1name", nameArray[0]);
            editor.putString("contact1phone", phoneArray[0]);
        }
        else {
            editor.putString("contact1name", null);
            editor.putString("contact1phone", null);
        }

        if (nameArray[1] != "None") {
            editor.putString("contact2name", nameArray[1]);
            editor.putString("contact2phone", phoneArray[1]);
        }
        else {
            editor.putString("contact2name", null);
            editor.putString("contact2phone", null);
        }

        if (nameArray[2] != "None") {
            editor.putString("contact3name", nameArray[2]);
            editor.putString("contact3phone", phoneArray[2]);
        }
        else {
            editor.putString("contact3name", null);
            editor.putString("contact3phone", null);
        }

        editor.putInt("currentIndex", currentIndex);
        editor.putInt("itemsAdded", itemsAdded);
        editor.putInt("itemsDeleted", itemsDeleted);

        editor.apply();

        Toast.makeText(this, "Contacts Saved.",
                Toast.LENGTH_LONG).show();
    }


}

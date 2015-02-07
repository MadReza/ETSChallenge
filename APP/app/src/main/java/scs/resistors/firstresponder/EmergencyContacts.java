package scs.resistors.firstresponder;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;


public class EmergencyContacts extends ActionBarActivity {

    public TextView outputText;
    private static final int CONTACT_PICKER_RESULT = 1001;
    private int currentIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contacts);

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

                        // query for everything email

//                        cursor = getContentResolver().query(Identity.CONTENT_URI,
//                                null,Identity.CONTACT_ID, new String[] { id },
//                                null);

                        //String whereName = ContactsContract.Data.MIMETYPE + " = ?";
                        //String[] whereNameParams = new String[] { ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE };

                        String whereName = ContactsContract.Data.MIMETYPE + " = ? AND " + ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID + " = ?";
                        String[] whereNameParams = new String[] { ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE, id };

                        cursor = getContentResolver().query(ContactsContract.Data.CONTENT_URI,
                                null, whereName, whereNameParams,
                                ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME);

                        phoneCursor = getContentResolver().query(Phone.CONTENT_URI,
                                null, Phone.CONTACT_ID + "=?", new String[] { id },
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

                        switch (currentIndex) {
                            case 1:
                                output = (TextView) findViewById(R.id.txt_output1);
                                currentIndex++;
                                break;
                            case 2:
                                output = (TextView) findViewById(R.id.txt_output2);
                                currentIndex++;
                                break;
                            case 3:
                                output = (TextView) findViewById(R.id.txt_output3);
                                currentIndex = 1;
                                break;
                            default:
                                currentIndex = 1;
                                break;
                        }
                        output.setText(name + "          Phone #: " + phone);
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

}

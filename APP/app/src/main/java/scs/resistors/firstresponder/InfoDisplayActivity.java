package scs.resistors.firstresponder;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InfoDisplayActivity extends Activity
{

    public final static String ITEM_TITLE = "title";
    public final static String ITEM_CAPTION = "caption";

    // Section Contents
    private ArrayList<String> contacts = new ArrayList<String>();
    private ArrayList<String> infos = new ArrayList<String>();

    // Adapter for ListView Contents
    private SeparatedListAdapter adapter;

    public Map<String, ?> createItem(String title, String caption)
    {
        Map<String, String> item = new HashMap<String, String>();
        item.put(ITEM_TITLE, title);
        item.put(ITEM_CAPTION, caption);
        return item;
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        // Add contacts from shared prefs
        SharedPreferences settings = getSharedPreferences(EmergencyContacts.SETTINGS_CONTACT, MODE_PRIVATE);

        contacts.clear();
        for(int i = 1; i <= 3; i++)
            getContact(settings, "contact"+i+"name","contact"+i+"phone");
        ((ListView) this.findViewById(R.id.infoList)).invalidateViews();
    }

    private PowerManager.WakeLock wakeLock = null;
    @Override
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);

        // Sets the View Layer
        setContentView(R.layout.activity_info_display);

        // WAKE LOCK
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK,"HealthToolbox");
        wakeLock.acquire();

        // Add contacts from shared prefs
        SharedPreferences settings = getSharedPreferences(EmergencyContacts.SETTINGS_CONTACT, MODE_PRIVATE);

        contacts.clear();
        for(int i = 1; i <= 3; i++)
            getContact(settings, "contact"+i+"name","contact"+i+"phone");

        SharedPreferences settings2 = getSharedPreferences(PersonalizeMe.SETTINGS_INFORMATION, MODE_PRIVATE);
        String infoStr = settings2.getString("information", null);
        if(infoStr == null)
            infoStr = "No additional info.";
        //"My Allergies are peanuts, shrimp, and tomatoes.\nMedical Card: 41231241512312451\nHospital St-Jerome\nPokemon Insurance"
        infos.clear();
        infos.add(infoStr);
        // Create the ListView Adapter
        adapter = new SeparatedListAdapter(this);

        adapter.addSection("My Contacts", new ArrayAdapter<String>(this, R.layout.list_item, contacts));
        adapter.addSection("My Information", new ArrayAdapter<String>(this, R.layout.list_item, infos));


        // Get a reference to the ListView holder
        ListView infoList = (ListView) this.findViewById(R.id.infoList);

        // Set the adapter on the ListView holder
        infoList.setAdapter(adapter);

        // Listen for Click events
        infoList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long duration) {
                String item = (String) adapter.getItem(position);
                Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(wakeLock != null)
            wakeLock.release();
    }

    private void getContact(SharedPreferences settings, String nameStr, String phoneStr){
        String name = settings.getString(nameStr, null);
        String phone = settings.getString(phoneStr, null);

        if(name != null && phone != null){
            contacts.add(name + " " + phone);
        }

    }

}
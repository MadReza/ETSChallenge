package scs.resistors.firstresponder;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
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
    private final static String[] allergies = new String[]{"Life", "God", "Turtles"};
    private final static String[] problems = new String[]{"Siezures", "Diabetes I", "Asthma", "Cancer"};
    private final static String[] infos = new String[]{"Medical Card: 41231241512312451", "Hospital St-Jerome", "Pokemon Insurance"};

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
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);

        // Sets the View Layer
        setContentView(R.layout.activity_info_display);

        // Add contacts from shared prefs
        SharedPreferences settings = getPreferences(MODE_PRIVATE);

        for(int i = 1; i <= 3; i++)
            getContact(settings, "contact"+i+"name","contact"+i+"phone");

        // Create the ListView Adapter
        adapter = new SeparatedListAdapter(this);

        adapter.addSection("My Allergies", new ArrayAdapter<String>(this, R.layout.list_item, allergies));
        adapter.addSection("My Common Issues", new ArrayAdapter<String>(this, R.layout.list_item, problems));
        adapter.addSection("My Contacts", new ArrayAdapter<String>(this, R.layout.list_item, contacts));
        adapter.addSection("My Info", new ArrayAdapter<String>(this, R.layout.list_item, infos));


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

    private void getContact(SharedPreferences settings, String nameStr, String phoneStr){
        String name = settings.getString(nameStr, null);
        String phone = settings.getString(phoneStr, null);
        if(name != null && phone != null){
            contacts.add(name + " " + phone);
        }
    }

}
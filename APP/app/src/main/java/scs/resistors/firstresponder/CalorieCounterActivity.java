package scs.resistors.firstresponder;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


public class CalorieCounterActivity extends ActionBarActivity {

    private static HashMap<String, Double> activitiesMap = new HashMap<String,Double>();
    private static String[] ACTIVITIES_NAMES = {"Running","Wrestling","Walking","Swimming","Bicycle","Roller-blade"};
    private static double[] ACTIVITIES_CALS_PER_MIN= {9.38, 7.03, 2.86, 8.22, 4.7, 14.06};

    private ArrayList<String> listViewContents = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_counter);

        for(int i = 0; i < ACTIVITIES_NAMES.length;i++)
            activitiesMap.put(ACTIVITIES_NAMES[i], ACTIVITIES_CALS_PER_MIN[i]);
        final Spinner spinnerActivities = (Spinner) findViewById(R.id.spinnerCalorieActivities);

        ListView listView = (ListView) findViewById(R.id.listViewCalorieActivities);
        listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listViewContents));

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item ,ACTIVITIES_NAMES);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerActivities.setAdapter(spinnerAdapter);

        Button addButton = (Button) findViewById(R.id.buttonCalorieSubmit);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText numOfMin = (EditText) findViewById(R.id.editTextCalorieTime);
                int minutes = Integer.parseInt(numOfMin.getText().toString());
                String value = spinnerActivities.getSelectedItem().toString() + "*" + minutes;
                listViewContents.add(value);
                recalculateTotalCaloriesBurnt();
                ListView listView = (ListView) findViewById(R.id.listViewCalorieActivities);
                listView.invalidateViews();
            }
        });
        recalculateTotalCaloriesBurnt();
    }

    private void recalculateTotalCaloriesBurnt(){
        double totalCalories = 0;
        ListView listView = (ListView) findViewById(R.id.listViewCalorieActivities);
        int count = listView.getAdapter().getCount();
        for(int i = 0; i < count; i++){
            Object o = listView.getAdapter().getItem(i);
            if(o instanceof String){
                String temp = (String)o;
                String key = temp.substring(0, temp.indexOf("*"));
                int minutes = Integer.parseInt(temp.substring(temp.indexOf("*") + 1));
                double value = activitiesMap.get(key);
                totalCalories += (value * minutes);
            }
        }
        // put new value in text view
        TextView calorieTextView = (TextView) findViewById(R.id.textViewCalorieTotal);
        calorieTextView.setText(""+totalCalories);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calorie_counter, menu);
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
}

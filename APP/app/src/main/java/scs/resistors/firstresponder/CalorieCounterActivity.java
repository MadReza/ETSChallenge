package scs.resistors.firstresponder;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
    private ArrayList<String> listViewContentsNames = new ArrayList<String>();
    private ArrayList<Integer> listViewContentsMinutes = new ArrayList<Integer>();

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
                String name = spinnerActivities.getSelectedItem().toString();
                int minutes = Integer.parseInt(numOfMin.getText().toString());
                listViewContentsNames.add(name);
                listViewContentsMinutes.add(minutes);

                listViewContents.add(minutes+ " minutes of " + name);

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
        for(int i = 0; i < listViewContentsMinutes.size(); i++)
                totalCalories += (activitiesMap.get(listViewContentsNames.get(i)) * listViewContentsMinutes.get(i));
        // put new value in text view
        TextView calorieTextView = (TextView) findViewById(R.id.textViewCalorieTotal);
        calorieTextView.setText(String.format("%.2f",totalCalories));
    }
}

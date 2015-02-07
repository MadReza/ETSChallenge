package scs.resistors.firstresponder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PersonalizeMe extends ActionBarActivity {
    public static final String SETTINGS_INFORMATION = "SETTINGS_INFORMATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalize_me);
        SharedPreferences settings = getSharedPreferences(PersonalizeMe.SETTINGS_INFORMATION, MODE_PRIVATE);
        EditText editText = (EditText) findViewById(R.id.editTextPersonalize);
        editText.setText(settings.getString("information","No information available."));

        Button submitButton = (Button) findViewById(R.id.buttonPersonalize);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = getSharedPreferences(PersonalizeMe.SETTINGS_INFORMATION, MODE_PRIVATE);
                String str = ((EditText) findViewById(R.id.editTextPersonalize)).getText().toString();
                SharedPreferences.Editor edit = settings.edit();
                edit.putString("information",str);
                edit.apply();
                Toast.makeText(PersonalizeMe.this, "Information Saved.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent (getApplicationContext(),
                            MainActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_personalize_me, menu);
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

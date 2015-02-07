package scs.resistors.firstresponder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

public class FirstResponder extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_responder);

        Button personalizeButton = (Button) findViewById(R.id.personalizeButton);
        personalizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(), PersonalizeMe.class);
                startActivity(intent);
            }
        });
        Button erContactsButton = (Button) findViewById(R.id.erContactsButton);
        erContactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(), EmergencyContacts.class);
                startActivity(intent);
            }
        });
    }
}

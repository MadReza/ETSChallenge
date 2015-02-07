package scs.resistors.firstresponder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.telephony.SmsManager;

public class FirstResponder extends ActionBarActivity {

    static int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_responder);
        counter = 0;

        final Button personalizeButton = (Button) findViewById(R.id.personalizeButton);
        personalizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(), PersonalizeMe.class);
                startActivity(intent);
            }
        });

        final Button erContactsButton = (Button) findViewById(R.id.erContactsButton);
        erContactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(), EmergencyContacts.class);
                startActivity(intent);
            }
        });

        final ImageView panicImageView = (ImageView) findViewById(R.id.panic);
        final TextView cancelTextView = (TextView) findViewById(R.id.cancelTextView);
        cancelTextView.setVisibility(View.INVISIBLE);

        panicImageView.setClickable(true);
        erContactsButton.setClickable(true);
        personalizeButton.setClickable(true);

        panicImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelTextView.setVisibility(View.VISIBLE);
                panicImageView.setClickable(false);
                erContactsButton.setClickable(false);
                personalizeButton.setClickable(false);

                // Trigger sound shit
                new CountDownTimer(10000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        cancelTextView.setText
                                ("seconds before ER action starts: "
                                        + millisUntilFinished / 1000
                                                + "\n Press back button to avoid it.");
                    }
                    public void onFinish() {
                        // WORK HAPPESNS HERE
                        SharedPreferences prefs = getSharedPreferences(EmergencyContacts.SETTINGS_CONTACT, MODE_PRIVATE);
                        String phoneNo1 = prefs.getString("contact1phone", null);
                        String phoneNo2 = prefs.getString("contact2phone", null);
                        String phoneNo3 = prefs.getString("contact3phone", null);

                        String msg = getResources().getString(R.string.SmsMessage);

                        SmsManager smsManager = SmsManager.getDefault();
                        if (null != phoneNo1)
                            smsManager.sendTextMessage(phoneNo1, null, msg, null, null);
                        if (null != phoneNo2)
                            smsManager.sendTextMessage(phoneNo2, null, msg, null, null);
                        if (null != phoneNo3)
                            smsManager.sendTextMessage(phoneNo3, null, msg, null, null);

                        // end up going to displayActivity
                        Intent intent = new Intent (getApplicationContext(),
                                InfoDisplayActivity.class);
                        startActivity(intent);
                    }
                }.start();
            }
        });
    }
}

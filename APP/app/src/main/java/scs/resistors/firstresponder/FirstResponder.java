package scs.resistors.firstresponder;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FirstResponder extends ActionBarActivity {

    static int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_responder);
        counter = 0;

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

        final ImageView panicImageView = (ImageView) findViewById(R.id.panic);
        final TextView cancelTextView = (TextView) findViewById(R.id.cancelTextView);
        cancelTextView.setVisibility(View.INVISIBLE);
        panicImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelTextView.setVisibility(View.VISIBLE);
                panicImageView.setClickable(false);
                new CountDownTimer(30000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        cancelTextView.setText
                                ("seconds before ER action starts: "
                                        + millisUntilFinished / 1000
                                                + "\n Press back button to avoid it.");
                    }
                    public void onFinish() {
                        // WORK HAPPESNS HERE
                    }
                }.start();
            }
        });
    }
}

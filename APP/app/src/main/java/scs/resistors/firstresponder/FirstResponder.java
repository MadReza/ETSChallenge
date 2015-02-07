package scs.resistors.firstresponder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Timer;

public class FirstResponder extends ActionBarActivity {

    double latitude;
    double longitude;
    private LocationManager locationManager;
    Uri myUri = Uri.EMPTY;
    MediaPlayer mediaPlayer;
    boolean playerReady = false;

    LocationListener locationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onLocationChanged(Location location) {
            if(location != null){
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_responder);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                0,
                0,
                locationListener);

        myUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sirencut);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                playerReady = true;
            }
        });
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(getApplicationContext(), myUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.prepareAsync();

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

                if (playerReady) {
                    playerReady = false;
                    mediaPlayer.start();
                    Timer timer = new Timer();
                    timer.schedule(new java.util.TimerTask() {

                        @Override
                        public void run() {
                            mediaPlayer.pause();
                            mediaPlayer.seekTo(0);
                            playerReady = true;
                        }
                    }, 5000);
                }

                new CountDownTimer(10000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        cancelTextView.setText
                                ("seconds before ER action starts: "
                                        + millisUntilFinished / 1000
                                        + "\n Press back button to avoid it.");
                    }

                    public void onFinish() {
                        SharedPreferences prefs = getSharedPreferences(EmergencyContacts.SETTINGS_CONTACT, MODE_PRIVATE);
                        String phoneNo1 = prefs.getString("contact1phone", null);
                        String phoneNo2 = prefs.getString("contact2phone", null);
                        String phoneNo3 = prefs.getString("contact3phone", null);

                        String msg = getResources().getString(R.string.SmsMessage);

                        SmsManager smsManager = SmsManager.getDefault();
                        if (null != smsManager) {
                            if (null != phoneNo1) {
                                if (latitude != 0 || longitude != 0) {
                                    msg += "https://www.google.com.au/maps/preview/@"
                                            + latitude + "," + longitude + "," + "8z";
                                }
                                smsManager.sendTextMessage(phoneNo1, null, msg, null, null);
                            }
                            if (null != phoneNo2) {
                                if (latitude != 0 || longitude != 0) {
                                    msg += "https://www.google.com.au/maps/preview/@"
                                            + latitude + "," + longitude + "," + "8z";
                                }
                                smsManager.sendTextMessage(phoneNo2, null, msg, null, null);
                            }
                            if (null != phoneNo3) {
                                if (latitude != 0 || longitude != 0) {
                                    msg += "https://www.google.com.au/maps/preview/@"
                                            + latitude + "," + longitude + "," + "8z";
                                }
                                smsManager.sendTextMessage(phoneNo3, null, msg, null, null);
                            }
                        }
                        // end up going to displayActivity
                        Intent intent = new Intent(getApplicationContext(),
                                InfoDisplayActivity.class);
                        startActivity(intent);
                    }
                }.start();
            }
        });
    }
}

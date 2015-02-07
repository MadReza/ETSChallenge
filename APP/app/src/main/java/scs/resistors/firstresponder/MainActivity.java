package scs.resistors.firstresponder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    final static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // GET ImageView-elements
        ImageView didYouKnowImageView = (ImageView) findViewById(R.id.didYouKnowImageView);
        ImageView alarmImageView = (ImageView) findViewById(R.id.firstResponderImageView);
        ImageView toolBoxImageView = (ImageView) findViewById(R.id.toolBoxImageView);
        ImageView caloriesImageView = (ImageView) findViewById(R.id.caloriesImageViews);
        ImageView FAQImageView = (ImageView) findViewById(R.id.faqImageView);

        didYouKnowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DidYouKnowActivity.class);
                startActivity(intent);
            }
        });
        alarmImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FirstResponder.class);
                startActivity(intent);
            }
        });
        toolBoxImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(
                        MainActivity.this);
                alert.setTitle("About");
                alert.setMessage("Health Toolbox.\nVersion 0.0.1");
                alert.setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = alert.show();
                TextView messageView = (TextView) dialog.findViewById(android.R.id.message);
                messageView.setGravity(Gravity.CENTER);
            }
        });
        caloriesImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CalorieCounterActivity.class);
                startActivity(intent);
            }
        });
        FAQImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getApplicationContext(), FAQActivity.class);
                //startActivity(intent);
            }
        });
    }
}
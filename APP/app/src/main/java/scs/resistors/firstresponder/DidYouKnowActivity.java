package scs.resistors.firstresponder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import scs.resistors.firstresponder.funfacts.ColorWheel;
import scs.resistors.firstresponder.funfacts.FactBook;

public class DidYouKnowActivity extends ActionBarActivity {

    private FactBook mFactBook = new FactBook();
    private ColorWheel mColorWheel = new ColorWheel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_did_you_know);

        final TextView factLabel = (TextView) findViewById(R.id.funFactText);
        final Button showFactButton = (Button) findViewById(R.id.showFactButton);
        final RelativeLayout relativeLayout =
                (RelativeLayout) findViewById(R.id.relativeLayout);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                factLabel.setText(mFactBook.getFact());

                int color = mColorWheel.getColor();
                relativeLayout.setBackgroundColor(color);
            }
        };
        showFactButton.setOnClickListener(listener);

        SharedPreferences sharedpreferences = getPreferences(MODE_WORLD_READABLE);
        Editor editor = sharedpreferences.edit();
        editor.putInt(this.getString(R.string.wiki_pref), 0); // By default not registered
        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_didyaknow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.wiki_register) {

            AlertDialog.Builder alert = new AlertDialog.Builder(
                    DidYouKnowActivity.this);
            alert.setTitle("Healthy Tips");
            SharedPreferences sharedpreferences = getPreferences(MODE_WORLD_READABLE);
            if (0 == sharedpreferences.getInt(getResources().getString(R.string.prefs), 0)) {
                alert.setMessage("Would you like to get daily healthy tips as a notification? Just register");
                alert.setPositiveButton("REGISTER", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedpreferences = getPreferences(MODE_WORLD_READABLE);
                        Editor editor = getPreferences(MODE_WORLD_READABLE).edit();
                        editor.putInt(getResources().getString(R.string.wiki_pref), 1);
                        editor.commit();
                    }
                });
            } else if (1 == sharedpreferences.getInt(getResources().getString(R.string.prefs), 1)) {
                alert.setMessage("You are registered to get daily healthy tips! Wanna unregister?");
                alert.setPositiveButton("UNREGISTER", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedpreferences = getPreferences(MODE_WORLD_READABLE);
                        Editor editor = getPreferences(MODE_WORLD_READABLE).edit();
                        editor.putInt(getResources().getString(R.string.wiki_pref), 0);
                        editor.commit();
                    }
                });
            }
            alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.show();
        }

        return super.onOptionsItemSelected(item);
    }
}

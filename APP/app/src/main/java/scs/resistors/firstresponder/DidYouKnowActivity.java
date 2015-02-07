package scs.resistors.firstresponder;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
    }
    // TODO: remove the shitty bar at the top
}

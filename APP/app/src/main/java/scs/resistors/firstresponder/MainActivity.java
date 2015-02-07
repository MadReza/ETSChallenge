package scs.resistors.firstresponder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends ActionBarActivity {

    final static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // GET ImageView-elements
        ImageView didYouKnowImageView = (ImageView) findViewById(R.id.didYouKnowImageView);
        didYouKnowImageView.setDrawingCacheEnabled(true);
        ImageView alarmImageView = (ImageView) findViewById(R.id.alarmImageView);
        alarmImageView.setDrawingCacheEnabled(true);
        ImageView toolBoxImageView = (ImageView) findViewById(R.id.toolBoxImageView);
        alarmImageView.setDrawingCacheEnabled(true);
        ImageView caloriesImageView = (ImageView) findViewById(R.id.caloriesImageViews);
        caloriesImageView.setDrawingCacheEnabled(true);
        ImageView FAQImageView = (ImageView) findViewById(R.id.faqImageView);
        alarmImageView.setDrawingCacheEnabled(true);

        // DID YA KNOW ACTIVITY BUTTON
        didYouKnowImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, MotionEvent event) {
                Bitmap bmp = Bitmap.createBitmap(v.getDrawingCache());
                ImageView startRunButtonImageView = (ImageView) v;
                int color = 0;
                try {
                    color = bmp.getPixel((int) event.getX(), (int) event.getY());
                } catch(Exception e) {
                    android.util.Log.e(TAG,"getting the Bitmap" +
                            " Pixel touched for startRunButton threw an exception");
                }
                if(color == Color.TRANSPARENT) return false;
                else {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_UP:
                            // load darker image
                            // startRunButtonImageView.setImageResource(R.drawable.startrun_pressed);
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Intent intent = new Intent(getApplicationContext(), DidYouKnowActivity.class);
                            startActivity(intent);
                            break;
                        default:
                            break;
                    }
                }
                return true;
            }
        });

        // First Responder ACTIVITY BUTTON
        alarmImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, MotionEvent event) {
                Bitmap bmp = Bitmap.createBitmap(v.getDrawingCache());
                ImageView viewHistoryButtonImageView = (ImageView) v;
                int color = 0;
                try {
                    color = bmp.getPixel((int) event.getX(), (int) event.getY());
                } catch (Exception e) {
                    android.util.Log.e(TAG, "getting the Bitmap" +
                            " Pixel touched for viewHistoryButton threw an exception");
                }
                if (color == Color.TRANSPARENT) return false;
                else {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_UP:
                            // load the dark ImageView
                            //viewHistoryButtonImageView.setImageResource(R.drawable.viewhistory_pressed);
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Intent intent = new Intent(getApplicationContext(), FirstResponder.class);
                            startActivity(intent);
                            break;
                        default:
                            break;
                    }
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        } else if (id == R.id.action_personalize_me) {

        } else if (id == R.id.action_fun_facts) {
            Intent intent = new Intent (this, DidYouKnowActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_settings) {

        } else if (id == R.id.action_faq) {

        }

        return super.onOptionsItemSelected(item);
    }
}
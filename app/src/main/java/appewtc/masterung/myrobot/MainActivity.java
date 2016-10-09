package appewtc.masterung.myrobot;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private TextView textView;
    private SeekBar seekBar;
    private int anInt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind Widget
        textView = (TextView) findViewById(R.id.textView);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        //SeekBar Controller
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                anInt = i * 10;
                textView.setText(Integer.toString(anInt));


            }   // onProgress

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                upLoadIntegerToDweet(anInt);

            }
        });


    }   // Main Method

    private void upLoadIntegerToDweet(int anInt) {

        UpLoadValue upLoadValue = new UpLoadValue(MainActivity.this);
        upLoadValue.execute(anInt);

    }   // upLoad

    private class UpLoadValue extends AsyncTask<Integer, Void, String> {

        //Explicit
        private Context context;
        private static final String urlSTRING = "https://dweet.io/dweet/for/SuperMaster?servo1=";

        public UpLoadValue(Context context) {
            this.context = context;
        }   // Constructor

        @Override
        protected String doInBackground(Integer... integers) {

            try {

                String urlDweet = urlSTRING + Integer.toString(integers[0]);

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(urlDweet).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                Log.d("RobotV2", "e doInBack ==> " + e.toString());
                return null;
            }


        }   // doInBack

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("RobotV2", "Result JSON ==> " + s);

        }   // onPost

    }   // UpLoadValue


}   // Main Class

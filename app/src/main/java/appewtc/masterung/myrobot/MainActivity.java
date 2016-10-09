package appewtc.masterung.myrobot;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
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
    private RadioGroup radioGroup;
    private String[] strings = new String[]{
            "servo0=",
            "servo1=",
            "servo2=",
            "servo3="};
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind Widget
        textView = (TextView) findViewById(R.id.textView);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        radioGroup = (RadioGroup) findViewById(R.id.ragServo);

        //Radio Controller
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {
                    case R.id.radioButton:
                        index = 0;
                        break;
                    case R.id.radioButton2:
                        index = 1;
                        break;
                    case R.id.radioButton3:
                        index = 2;
                        break;
                    case R.id.radioButton4:
                        index = 3;
                        break;
                }

            }   // onChecked
        });

        //SeekBar Controller
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                seekBar.setMax(100);
                anInt = i;
                textView.setText(Integer.toString(anInt) + "%");


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

    public void clickReadDweet(View view) {
        startActivity(new Intent(MainActivity.this, ReadDweet.class));
    }

    private void upLoadIntegerToDweet(int anInt) {

        UpLoadValue upLoadValue = new UpLoadValue(MainActivity.this);
        upLoadValue.execute(anInt);

    }   // upLoad

    private class UpLoadValue extends AsyncTask<Integer, Void, String> {

        //Explicit
        private Context context;
        private static final String urlSTRING = "https://dweet.io/dweet/for/SuperMaster?";

        public UpLoadValue(Context context) {
            this.context = context;
        }   // Constructor

        @Override
        protected String doInBackground(Integer... integers) {

            try {

                String urlDweet = urlSTRING + strings[index] + Integer.toString(integers[0]);

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

package appewtc.masterung.myrobot;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

public class ReadDweet extends AppCompatActivity {

    //Explicit
    private static final String urlJSON = "https://dweet.io/get/latest/dweet/for/SuperMaster";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_dweet);

        textView = (TextView) findViewById(R.id.textView2);

        synData();

    }   // Main Method

    private class SynDweet extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(urlJSON).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("RobotV3", "JSON ==> " + s);

            try {

                JSONArray jsonArray = new JSONArray("[" + s + "]");
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                String result1 = jsonObject.getString("with");
                Log.d("RobotV3", "result1 ==> " + result1);

                JSONArray jsonArray1 = new JSONArray(result1);
                JSONObject jsonObject1 = jsonArray1.getJSONObject(0);
                String result2 = jsonObject1.getString("content");
                Log.d("RobotV3", "result2 ==> " + result2);


            } catch (Exception e) {
                e.printStackTrace();
            }




        }
    }   // SynDweet Class

    private void synData() {

        SynDweet synDweet = new SynDweet();
        synDweet.execute();

    }   // synData

}   // Main Class

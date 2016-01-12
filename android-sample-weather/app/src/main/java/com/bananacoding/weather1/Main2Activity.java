package com.bananacoding.weather1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bananacoding.weather1.helper.RSSParser2;
import com.bananacoding.weather1.model.RSSWeather2;

public class Main2Activity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private static String weather_url;
    RSSParser2 parser1 = new RSSParser2();
    RSSWeather2 weather2;
    TextView textView;

    public static final String EXTRA_MESSAGE = "abc" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("123", "ClickOk");

                //Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                EditText txt_lat = (EditText) findViewById(R.id.txt_lat);
                String message1 = txt_lat.getText().toString();
                //intent.putExtra(EXTRA_MESSAGE, message1);

                EditText txt_long = (EditText) findViewById(R.id.txt_long);
                String message2 = txt_long.getText().toString();
                //intent.putExtra(EXTRA_MESSAGE, message2);

                Log.d("Lat", message1);
                Log.d("Long", message2);

                //"http://weather.yahooapis.com/forecastrss?w=12756339&u=c"

                String weather1_url = ("http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20geo.placefinder%20where%20text=%22"+message1+","+message2+"%22%20and%20gflags=%22R%22");
                Log.d("Long", weather1_url);
                /**
                 * Calling a backgroung thread will loads recent data of a website
                 * @param rss url of website
                 * */
                //new loadRSSFeedItems().execute(weather_url);

                new loadRSSFeedItems().execute(weather1_url);
                //startActivity(intent);
            }
            class loadRSSFeedItems extends AsyncTask<String, String, String> {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    pDialog = new ProgressDialog(Main2Activity.this);
                    pDialog.setMessage("Loading weather...");
                    pDialog.setIndeterminate(false);
                    pDialog.setCancelable(false);
                    pDialog.show();
                }

                /**
                 * getting all recent data and showing them in text view.
                 */
                @Override
                protected String doInBackground(String... args) {
                    // rss link url
                    String query_url = args[0];

                    // weather object of rss.
                    weather2 = parser1.getRSSFeedWeather(query_url);

                    // updating UI from Background Thread
                    runOnUiThread(new Runnable() {
                        public void run() {
                            /**
                             * Updating parsed data into text view.
                             * */
                            Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                            String description1 = weather2.getwoeid();
                            intent.putExtra(EXTRA_MESSAGE, description1);

                            Log.d("Long", description1);

                            startActivity(intent);
                        }
                    });
                    return null;
                }

                /**
                 * After completing background task Dismiss the progress dialog
                 **/
                protected void onPostExecute(String args) {
                    // dismiss the dialog after getting all products
                    pDialog.dismiss();
                }
            }
        });

        }
    }

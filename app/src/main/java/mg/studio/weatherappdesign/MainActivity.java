package mg.studio.weatherappdesign;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    public void btnClick(View view) {
        new DownloadUpdate().execute();
    }
    public void btnClick_refresh(View view) {
        Date currDate = new Date(System.currentTimeMillis());
        String strDate = format.format(currDate);
        String strWeek = getWeek(strDate);
        ((TextView)findViewById(R.id.tv_date)).setText(strDate);
        ((TextView)findViewById(R.id.tv_week)).setText(strWeek);
        new DownloadUpdate().execute();
    }
    private String getWeek(String time) {

        String Week = "";
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(time));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            Week += "SUNDAY";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            Week += "MONDAY";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            Week += "TUESDAY";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            Week += "WEDNESDAY";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            Week += "THURSDAY";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            Week += "FRIDAY";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            Week += "SATURDAY";
        }
        return Week;
    }


    private class DownloadUpdate extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {
            String stringUrl = "http://mpianatra.com/Courses/info.txt";

            HttpURLConnection urlConnection = null;
            BufferedReader reader;

            try {
                URL url = new URL(stringUrl);

                // Create the request to get the information from the server, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Mainly needed for debugging
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                //The temperature
                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String temperature) {
            //Update the temperature displayed
            ((TextView) findViewById(R.id.temperature_of_the_day)).setText(temperature);
        }
    }
}

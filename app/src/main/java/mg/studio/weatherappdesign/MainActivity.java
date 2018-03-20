package mg.studio.weatherappdesign;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


public class MainActivity extends AppCompatActivity {

    private TextView tv_ciyt,tv_time,tv_wendu;
    private ImageView iv_type;

    private ImageView iv_type1,iv_type2,iv_type3,iv_type4;


    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message message){
            switch (message.what){
                case 1:
                    updateTodayWeather((TodayWeather) message.obj);
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //检查网络连接
        if (CheckNet.getNetState(this) == CheckNet.NET_NONE){
            Log.d("myWeather","网络连接失败");
            Toast.makeText(MainActivity.this,"网络连接失败",Toast.LENGTH_LONG).show();
        }else {
            Log.d("myWeather","网络连接成功");
            Toast.makeText(MainActivity.this,"网络连接成功",Toast.LENGTH_LONG).show();
            setDays();
            getWeatherDatafromNet("101010100");
        }
    }

    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    TodayWeather todayWeather = new TodayWeather();

    private void initView(){
        tv_ciyt = (TextView)findViewById(R.id.tv_location);
        tv_time = (TextView)findViewById(R.id.tv_updatetime);
        tv_wendu = (TextView)findViewById(R.id.temperature_of_the_day);
        iv_type = (ImageView)findViewById(R.id.img_weather_condition);

        iv_type1=(ImageView)findViewById(R.id.iv_date1);
        iv_type2=(ImageView)findViewById(R.id.iv_date2);
        iv_type3=(ImageView)findViewById(R.id.iv_date3);
        //iv_type4=(ImageView)findViewById(R.id.iv_date4);

    }

    /*public void btnClick(View view) {
        new DownloadUpdate().execute();
    }*/
    public void btnClick_refresh(View view) {
        //检查网络连接
        if (CheckNet.getNetState(this) == CheckNet.NET_NONE){
            Log.d("myWeather","网络连接失败");
            Toast.makeText(MainActivity.this,"网络连接失败",Toast.LENGTH_LONG).show();
        }else {
            Log.d("myWeather","网络连接成功");
            Toast.makeText(MainActivity.this,"网络连接成功",Toast.LENGTH_LONG).show();
            setDays();
            getWeatherDatafromNet("101040100");
            Toast.makeText(MainActivity.this,"更新成功",Toast.LENGTH_SHORT).show();
        }

        //new DownloadUpdate().execute();
    }

    private String getWeek(String time) {

        String Week = null;
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(time));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            Week = "SUNDAY";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            Week = "MONDAY";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            Week = "TUESDAY";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            Week = "WEDNESDAY";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            Week = "THURSDAY";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            Week = "FRIDAY";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            Week = "SATURDAY";
        }
        return Week;
    }
    private void setDays(){
        Date currDate = new Date(System.currentTimeMillis());
        String strDate = format.format(currDate);
        String strWeek = getWeek(strDate);
        ((TextView)findViewById(R.id.tv_date)).setText(strDate);
        ((TextView)findViewById(R.id.tv_week)).setText(strWeek);
        if (strWeek == "SUNDAY"){
            ((TextView)findViewById(R.id.tv_date1)).setText("MON");
            ((TextView)findViewById(R.id.tv_date2)).setText("TUE");
            ((TextView)findViewById(R.id.tv_date3)).setText("WED");
            ((TextView)findViewById(R.id.tv_date4)).setText("TUR");
        }else if (strWeek == "MONDAY"){
            ((TextView)findViewById(R.id.tv_date1)).setText("TUE");
            ((TextView)findViewById(R.id.tv_date2)).setText("WED");
            ((TextView)findViewById(R.id.tv_date3)).setText("TUR");
            ((TextView)findViewById(R.id.tv_date4)).setText("FRI");
        }else if (strWeek == "TUESDAY"){
            ((TextView)findViewById(R.id.tv_date1)).setText("WED");
            ((TextView)findViewById(R.id.tv_date2)).setText("TUR");
            ((TextView)findViewById(R.id.tv_date3)).setText("FRI");
            ((TextView)findViewById(R.id.tv_date4)).setText("SAT");
        }else if (strWeek == "WEDNESDAY"){
            ((TextView)findViewById(R.id.tv_date1)).setText("TUR");
            ((TextView)findViewById(R.id.tv_date2)).setText("FRI");
            ((TextView)findViewById(R.id.tv_date3)).setText("SAT");
            ((TextView)findViewById(R.id.tv_date4)).setText("SUN");
        }else if (strWeek == "THURSDAY"){
            ((TextView)findViewById(R.id.tv_date1)).setText("FRI");
            ((TextView)findViewById(R.id.tv_date2)).setText("SAT");
            ((TextView)findViewById(R.id.tv_date3)).setText("SUN");
            ((TextView)findViewById(R.id.tv_date4)).setText("MON");
        }else if (strWeek == "FRIDAY"){
            ((TextView)findViewById(R.id.tv_date1)).setText("SAT");
            ((TextView)findViewById(R.id.tv_date2)).setText("SUN");
            ((TextView)findViewById(R.id.tv_date3)).setText("MON");
            ((TextView)findViewById(R.id.tv_date4)).setText("TUE");
        }else if (strWeek == "SATURDAY"){
            ((TextView)findViewById(R.id.tv_date1)).setText("SUN");
            ((TextView)findViewById(R.id.tv_date2)).setText("MON");
            ((TextView)findViewById(R.id.tv_date3)).setText("TUE");
            ((TextView)findViewById(R.id.tv_date4)).setText("WED");
        }
    }

    private  TodayWeather parseXML(String xmlData){

        int typeCount = 0;

        try{
            XmlPullParserFactory factory  = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));

            int eventType = xmlPullParser.getEventType();
            Log.d("MyWeather","start parse xml");

            while (eventType!=xmlPullParser.END_DOCUMENT){
                switch(eventType){
                    //文档开始位置
                    case XmlPullParser.START_DOCUMENT:
                        Log.d("parse","start doc");
                        break;
                    //标签元素开始位置
                    case XmlPullParser.START_TAG:
                        if (xmlPullParser.getName().equals("resp")){
                            todayWeather = new TodayWeather();
                        }
                        if (todayWeather!=null){
                            if(xmlPullParser.getName().equals("city")){
                                eventType = xmlPullParser.next();
                                Log.d("city",xmlPullParser.getText());
                                todayWeather.setCity(xmlPullParser.getText());
                            }else if (xmlPullParser.getName().equals("updatetime")){
                                eventType = xmlPullParser.next();
                                Log.d("updatetime", xmlPullParser.getText());
                                todayWeather.setUpdatetime(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("wendu")){
                                eventType = xmlPullParser.next();
                                Log.d("wendu",xmlPullParser.getText());
                                todayWeather.setWendu(xmlPullParser.getText());
                            }else if (xmlPullParser.getName().equals("date")){
                                eventType = xmlPullParser.next();
                                Log.d("date",xmlPullParser.getText());
                                todayWeather.setDate(xmlPullParser.getText());
                            }else if (xmlPullParser.getName().equals("type") && typeCount ==0){
                                eventType = xmlPullParser.next();
                                Log.d("type",xmlPullParser.getText());
                                todayWeather.setType(xmlPullParser.getText());
                                typeCount++;
                            }else if (xmlPullParser.getName().equals("type") && typeCount ==1){
                                eventType = xmlPullParser.next();
                                Log.d("date1_type",xmlPullParser.getText());
                                todayWeather.setType1(xmlPullParser.getText());
                                typeCount++;
                            }else if (xmlPullParser.getName().equals("type") && typeCount ==2){
                                eventType = xmlPullParser.next();
                                Log.d("date2_type",xmlPullParser.getText());
                                todayWeather.setType2(xmlPullParser.getText());
                                typeCount++;
                            }else if (xmlPullParser.getName().equals("type") && typeCount ==3){
                                eventType = xmlPullParser.next();
                                Log.d("date3_type",xmlPullParser.getText());
                                todayWeather.setType3(xmlPullParser.getText());
                                typeCount++;
                            }else if (xmlPullParser.getName().equals("type") && typeCount ==4){
                                eventType = xmlPullParser.next();
                                Log.d("date4_type",xmlPullParser.getText());
                                todayWeather.setType3(xmlPullParser.getText());
                                typeCount++;
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return todayWeather;
    }
    private void getWeatherDatafromNet(String cityCode)
    {
        final String address = "http://wthrcdn.etouch.cn/WeatherApi?citykey="+cityCode;
        Log.d("Address:",address);
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection urlConnection = null;
                try {
                    URL url = new URL(address);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setConnectTimeout(8000);
                    urlConnection.setReadTimeout(8000);
                    InputStream in = urlConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuffer sb = new StringBuffer();
                    String str;
                    while((str=reader.readLine())!=null)
                    {
                        sb.append(str);
                        Log.d("date from url",str);
                    }
                    String response = sb.toString();
                    Log.d("response",response);
                    todayWeather = parseXML(response);
                    if (todayWeather!=null){
                        Message message = new Message();
                        message.what=1;
                        message.obj=todayWeather;
                        mHandler.sendMessage(message);
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void updateTodayWeather(TodayWeather todayWeather){
        tv_ciyt.setText(todayWeather.getCity());
        tv_wendu.setText(todayWeather.getWendu());
        tv_time.setText(todayWeather.getUpdatetime());
        switch (todayWeather.getType()){
            case "晴":
                iv_type.setImageResource(R.drawable.sunny_small);break;
            case "多云":
                iv_type.setImageResource(R.drawable.partly_sunny_small);break;
            case "小雨":
                iv_type.setImageResource(R.drawable.rainy_small);break;
            case "风":
                iv_type.setImageResource(R.drawable.windy_small);break;
        }

        switch (todayWeather.getType1()){
            case "晴":
                iv_type1.setImageResource(R.drawable.sunny_small);break;
            case "多云":
                iv_type1.setImageResource(R.drawable.partly_sunny_small);break;
            case "小雨":
                iv_type1.setImageResource(R.drawable.rainy_small);break;
            case "风":
                iv_type1.setImageResource(R.drawable.windy_small);break;
        }
        switch (todayWeather.getType2()){
            case "晴":
                iv_type2.setImageResource(R.drawable.sunny_small);break;
            case "多云":
                iv_type2.setImageResource(R.drawable.partly_sunny_small);break;
            case "小雨":
                iv_type2.setImageResource(R.drawable.rainy_small);break;
            case "风":
                iv_type2.setImageResource(R.drawable.windy_small);break;
        }
        switch (todayWeather.getType3()){
            case "晴":
                iv_type3.setImageResource(R.drawable.sunny_small);break;
            case "多云":
                iv_type3.setImageResource(R.drawable.partly_sunny_small);break;
            case "小雨":
                iv_type3.setImageResource(R.drawable.rainy_small);break;
            case "风":
                iv_type3.setImageResource(R.drawable.windy_small);break;
        }
        /*switch (todayWeather.getType4()){
            case "晴":
                iv_type4.setImageResource(R.drawable.sunny_small);break;
            case "多云":
                iv_type4.setImageResource(R.drawable.partly_sunny_small);break;
            case "小雨":
                iv_type4.setImageResource(R.drawable.rainy_small);break;
            case "风":
                iv_type4.setImageResource(R.drawable.windy_small);break;
        }*/
        //Toast.makeText(MainActivity.this,"更新成功",Toast.LENGTH_SHORT).show();
    }

  /*  private class DownloadUpdate extends AsyncTask<String, Void, String> {


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
    }*/
}

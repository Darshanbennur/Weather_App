package com.example.jason_prac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView weather;
    TextView overview,deepInfo,display;
    ImageView res;
    Button button;
    int[] arr = {R.drawable.ar,R.drawable.arr,R.drawable.arrr};
    int index;

    public class downloadWeather extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... urls) {
            if (Looper.myLooper()==null)
                Looper.prepare();
            String result = "";
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1){
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                return result;
            }
            catch (Exception e){
                e.printStackTrace();
                reset();
                return null;
            }
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jasonObject = new JSONObject(s);
                String weatherInfo = jasonObject.getString("main");

                JSONObject jasonObject_02 = new JSONObject(weatherInfo);
                String some = jasonObject_02.getString("temp");

                String message = "";
                String over = "";
                String deep = "";

                String moreMeaning = jasonObject.getString("weather");
                JSONArray arr = new JSONArray(moreMeaning);
                for (int i = 0; i < arr.length(); i++){
                    JSONObject obj = arr.getJSONObject(i);
                    String main = obj.getString("main");
                    String desc = obj.getString("description");
                    if (!main.equals("") && !desc.equals("")){
                       over += main;
                       deep += desc;
                    }
                    if (over.equals("Clouds")){
                        res.setImageResource(R.drawable.ar);
                    }
                    else if (over.equals("Rain")){
                        res.setImageResource(R.drawable.arr);
                    }
                    else
                        res.setImageResource(R.drawable.arrr);
                }

                String nm = some;
                double i = Double.parseDouble(nm);
                i -= 273.15;
                String tempe = String.format("%.2f",i);
                message += tempe +"°C";

                if (!(message.equals(""))){
                    weather.setText(message);
                    overview.setText(over);
                    deepInfo.setText(deep);
                    display.setText(editText.getText());
                    res.setVisibility(View.VISIBLE);
                }
                else
                    reset();
            }catch (Exception e){
                e.printStackTrace();
                reset();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        editText = findViewById(R.id.cityName);
        weather = findViewById(R.id.weather);
        overview = findViewById(R.id.overview);
        deepInfo = findViewById(R.id.deepInfo);
        res = findViewById(R.id.imageResult);
        display = findViewById(R.id.cityDisplay);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity();
            }
        });
    }

    public void searchWeather(View view){
        try{
            String cityName = editText.getText().toString();
            downloadWeather task = new downloadWeather();
            String encodedCity = URLEncoder.encode(cityName,"UTF-8");
            task.execute("https://api.openweathermap.org/data/2.5/weather?q=" + encodedCity + "&appid=db701ab10bb86fddce867d73eab87f61");
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(editText.getWindowToken(),0);
        }catch(Exception e){
            e.printStackTrace();
            reset();
        }
    }
    public void openActivity(){
        Intent intent = new Intent(this,privacyPolicy.class);
        startActivity(intent);
    }


    public void reset(){
        overview.setText("");
        deepInfo.setText("Try Again!!");
        weather.setText("Enter Valid City Name");
        display.setText("");
        res.setVisibility(View.INVISIBLE);
    }
}
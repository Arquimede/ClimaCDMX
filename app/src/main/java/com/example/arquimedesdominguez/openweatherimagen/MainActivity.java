package com.example.arquimedesdominguez.openweatherimagen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "openweatherimagen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView iv_weather = (ImageView) findViewById(R.id.iv_weather);
        final TextView tv_temp = (TextView) findViewById(R.id.tv_temp);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://localhost:8888/ejemplo/climadf";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            if (response.has("weather")) {

                                JSONArray weatherArray = response.getJSONArray("weather");
                                JSONObject weather = weatherArray.getJSONObject(0);

                                if (weather.has("icon")) {

                                    String icon = weather.getString("icon");
                                    int identificador = getResources().getIdentifier("imagen_" + icon, "drawable", getPackageName());
                                    iv_weather.setImageDrawable(getResources().getDrawable(identificador, null));
                                }

                            }
                            if (response.has("main")) {
                                JSONObject main = response.getJSONObject("main");
                                Double temp = main.getDouble("temp");
                                tv_temp.setText("" + temp + "\u00b0");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.getMessage());

                    }
                });
        queue.add(jsonObjectRequest);
    }
}

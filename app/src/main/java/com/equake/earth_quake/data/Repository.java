package com.equake.earth_quake.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.equake.earth_quake.controller.AppController;
import com.equake.earth_quake.model.Earthquake;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Repository {
    ArrayList<Earthquake> earthquakes=new ArrayList<>();
    String url="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&limit=25";
    public  List<Earthquake> getEarthquakes(final AnswerListAsyncResponse callBack){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("features");
                for (int i = 0; i < Objects.requireNonNull(jsonArray).length(); i++) {

                    JSONObject feature = jsonArray.getJSONObject(i);
                    JSONObject properties = feature.getJSONObject("properties");

                    Double mag = properties.getDouble("mag");
                    DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
                    String mage = magnitudeFormat.format(mag);

                    String string = properties.getString("place");
                    String[] parts = string.split(", ", 2);
                    String str = "no info";


                    long timeInMilliseconds = properties.getLong("time");
                    Date dateObject = new Date(timeInMilliseconds);

                    SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd, yyyy");
                    String dateToDisplay = dateFormatter.format(dateObject);

                    SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
                    String timeToDisplay = timeFormat.format(dateObject);


                    int a = parts.length;
                    if (a == 0) {
                        earthquakes.add(new Earthquake(mage, string, str, dateToDisplay, timeToDisplay));
                    }
                    if (a == 1) {
                        earthquakes.add(new Earthquake(mage, parts[0], str, dateToDisplay, timeToDisplay));
                    }
                    if (a == 2) {
                        earthquakes.add(new Earthquake(mage, parts[0], parts[1], dateToDisplay, timeToDisplay));
                    }
                    Log.d("devil", "earthquakes added");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (null != callBack) callBack.processFinished(earthquakes);
        }, error -> Log.d("devil", "error"));
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
        return earthquakes;
    }
}

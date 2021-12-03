package com.equake.earth_quake.adapter;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.equake.earth_quake.R;
import com.equake.earth_quake.model.Earthquake;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Earthquake> {

    public WordAdapter(Activity context, ArrayList<Earthquake> earthquakes) {
        super(context,0, earthquakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }


        Earthquake earthquake = getItem(position);


        TextView nameTextView = listItemView.findViewById(R.id.mag);

        nameTextView.setText(earthquake.getMagnitude());

        GradientDrawable magnitudeCircle = (GradientDrawable) nameTextView.getBackground();


        int magnitudeColor = getMagnitudeColor(Double.parseDouble((earthquake.getMagnitude())));


        magnitudeCircle.setColor(magnitudeColor);


        TextView numberTextView = listItemView.findViewById(R.id.place);

        numberTextView.setText(earthquake.getLocation());

        TextView loc = listItemView.findViewById(R.id.place2);
        loc.setText(earthquake.getLocation1());

         TextView timeTextView = listItemView.findViewById(R.id.time);

        timeTextView.setText(earthquake.getDate());

        TextView hours = listItemView.findViewById(R.id.hour);
        hours.setText(earthquake.getTime());

        Log.d("devil","listItemView done");
        return listItemView;
    }

    private int getMagnitudeColor(double magnitude){
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch(magnitudeFloor) {

            case 0:

            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;

            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;

            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;

            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;

            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;

            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;

            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;

            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;

            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}

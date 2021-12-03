package com.equake.earth_quake;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.toolbox.JsonObjectRequest;
import com.equake.earth_quake.adapter.WordAdapter;
import com.equake.earth_quake.controller.AppController;
import com.equake.earth_quake.data.Repository;
import com.equake.earth_quake.databinding.ActivityMainBinding;
import com.equake.earth_quake.model.Earthquake;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<Earthquake> earthquakes;
    ListView earthquakeListView;
    LottieAnimationView lottieAnimationView;
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
           binding= DataBindingUtil.setContentView(this,R.layout.activity_main);

//        ConstraintLayout constraintLayout = findViewById(R.id.mainlayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) binding.mainlayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

//        lottieAnimationView = findViewById(R.id.lottie);
        binding.lottie.playAnimation();
        binding.lottie.setProgress(0);


        earthquakes = new ArrayList<>();
//        earthquakeListView = findViewById(R.id.list);


        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();


        if (networkInfo != null && networkInfo.isConnected()) {
            fetchData();
        } else {

            Toast.makeText(this, "NO Internet", Toast.LENGTH_SHORT).show();
        }
//        swipeRefreshLayout = findViewById(R.id.srl);
        binding.srl.setOnRefreshListener(() -> {
            binding.list.setAdapter(null);
            binding.lottie.setVisibility(View.VISIBLE);
            binding.lottie.playAnimation();
            binding.lottie.setProgress(0);
            binding.lottie.setSpeed(2.0F);
            earthquakes.clear();
            AppController.getInstance().clr();
            fetchData();
            binding.srl.setRefreshing(false);
        });
    }

    private void fetchData() {

        new Repository().getEarthquakes(earthquakes1 -> {
            WordAdapter adapter = new WordAdapter(MainActivity.this, earthquakes1);
            Log.d("devil", "wordAdapter ok loop");
            binding.lottie.pauseAnimation();
            binding.lottie.setVisibility(View.GONE);
            binding.list.setAdapter(adapter);
        });
    }


}

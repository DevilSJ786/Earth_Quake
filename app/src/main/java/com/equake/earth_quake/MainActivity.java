package com.equake.earth_quake;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.equake.earth_quake.adapter.WordAdapter;
import com.equake.earth_quake.controller.AppController;
import com.equake.earth_quake.data.Repository;
import com.equake.earth_quake.databinding.ActivityMainBinding;
import com.equake.earth_quake.model.Earthquake;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Earthquake> earthquakes;
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        AnimationDrawable animationDrawable = (AnimationDrawable) binding.mainlayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();
        binding.lottie.playAnimation();
        binding.lottie.setProgress(0);


        earthquakes = new ArrayList<>();
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            fetchData();
        } else { Toast.makeText(this, "NO Internet", Toast.LENGTH_SHORT).show(); }

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

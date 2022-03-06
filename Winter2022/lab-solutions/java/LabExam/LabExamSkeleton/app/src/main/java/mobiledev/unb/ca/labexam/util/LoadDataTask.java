package mobiledev.unb.ca.labexam.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import mobiledev.unb.ca.labexam.MyAdapter;
import mobiledev.unb.ca.labexam.helper.SharedPreferencesHelper;
import mobiledev.unb.ca.labexam.model.GamesInfo;

public class LoadDataTask {
    private final AppCompatActivity activity;
    private final Context appContext;

    private RecyclerView recyclerView;
    private SharedPreferencesHelper prefsHelper;

    public LoadDataTask(AppCompatActivity activity) {
        this.activity = activity;
        appContext = activity.getApplicationContext();
    }

    public LoadDataTask setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        return this;
    }

    public LoadDataTask setSharedPreferencesHelper(SharedPreferencesHelper prefsHelper) {
        this.prefsHelper = prefsHelper;
        return this;
    }

    public void execute() {
        Executors.newSingleThreadExecutor().execute(() -> {
            Handler mainHandler = new Handler(Looper.getMainLooper());

            // TODO
            //  Load the data from the JSON assets file and return the list of cities
            JsonUtils jsonUtils = new JsonUtils(appContext);
            ArrayList<GamesInfo> gamesInfoList = jsonUtils.getHostCities();

            // TODO
            //  Use result to set the adapter for the RecyclerView
            mainHandler.post(() -> setupRecyclerView(gamesInfoList));
        });
    }

    private void setupRecyclerView(List<GamesInfo> gamesInfoList) {
        recyclerView.setAdapter(new MyAdapter(activity, gamesInfoList, prefsHelper));
    }
}
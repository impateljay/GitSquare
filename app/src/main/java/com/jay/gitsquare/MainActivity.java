package com.jay.gitsquare;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private DatabaseHandler db;
    private List<Contributor> contributors = new ArrayList<>();
    private RecyclerView recyclerView;
    private ContributorsAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Button filterContributionsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getString(R.string.activity_title));
        db = new DatabaseHandler(this);

        filterContributionsButton = (Button) findViewById(R.id.button_filter_contriburions);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new ContributorsAdapter(contributors);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        if (isNetworkAvailable()) {
            downloadContributors();
            mAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(MainActivity.this, R.string.no_internet, Toast.LENGTH_LONG).show();
        }

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isNetworkAvailable()) {
                    downloadContributors();
                } else {
                    Toast.makeText(MainActivity.this, R.string.no_internet, Toast.LENGTH_LONG).show();
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        filterContributionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(contributors, new Comparator<Contributor>() {
                    @Override
                    public int compare(Contributor o1, Contributor o2) {
                        return Integer.valueOf(o1.getContributions()).compareTo(o2.getContributions());
                    }
                });
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void downloadContributors() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<Contributor>> call = apiService.getContributors();
        call.enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Call<List<Contributor>>call, Response<List<Contributor>> response) {
                for (Contributor cn : response.body()) {
                    contributors.add(cn);
                    mAdapter.notifyDataSetChanged();
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Contributor>>call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error while downloading data!",Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

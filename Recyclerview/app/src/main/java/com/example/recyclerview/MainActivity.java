package com.example.recyclerview;

import android.os.Bundle;

import com.example.recyclerview.databaseCalls.APIClient;
import com.example.recyclerview.databaseCalls.JsonPlaceHolderApi;
import com.example.recyclerview.databaseCalls.TrendingApi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private JsonPlaceHolderApi jsonPlaceHolderApi;
    TextView textViewResult;
    RecyclerviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        jsonPlaceHolderApi = APIClient.getClient().create(JsonPlaceHolderApi.class);
        getTrendingApi();
    }

    private void getTrendingApi() {
        Call<List<TrendingApi>> call = jsonPlaceHolderApi.getTrendingApi();
        call.enqueue(new Callback<List<TrendingApi>>() {
            @Override
            public void onResponse(Call<List<TrendingApi>> call, Response<List<TrendingApi>> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }
                List<TrendingApi> trendingApiList = response.body();
                RecyclerView recyclerView = findViewById(R.id.products);
                recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));

                DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(),
                        DividerItemDecoration.VERTICAL);
                itemDecoration.setDrawable(getApplicationContext().getResources()
                        .getDrawable(R.drawable.divider_line));
                recyclerView.addItemDecoration(itemDecoration);

                adapter = new RecyclerviewAdapter(getApplicationContext(), trendingApiList);
                adapter.setClickListener();
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<TrendingApi>> call, Throwable t) {
                final View view = findViewById(R.id.error_screen_display);
                view.setVisibility(View.VISIBLE);
                Button button = view.findViewById(R.id.retry_button);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view.setVisibility(View.GONE);
                        getTrendingApi();
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sort_by_stars) {
            return true;
        } else if (id == R.id.sort_by_name) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

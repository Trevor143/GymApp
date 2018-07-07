package android.example.com.gymapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GymAdapter.OnItemClickListener {
    public static final String EXTRA_GYM = "gymName";
    public static final String EXTRA_MOBILE = "mobile";
    public static final String EXTRA_RATING = "rating";

    private RecyclerView mRecyclerView;
    private android.example.com.gymapp.GymAdapter mGymAdapter;
    private ArrayList<GymItem> mGymList;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mGymList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();
    }

    private void parseJSON() {
//        String url = "http://b62d1a04.ngrok.io/api/gyms/";
        String url = "http://gymappapi.000webhostapp.com/api/gyms/";


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject data = jsonArray.getJSONObject(i);

                                String gymName = data.getString("name");
                                String mobile = data.getString("mobile");
                                int rating = data.getInt("rating");

                                mGymList.add(new GymItem(gymName, rating, mobile));
                            }

                            mGymAdapter = new GymAdapter(MainActivity.this, mGymList);
                            mRecyclerView.setAdapter(mGymAdapter);
                            mGymAdapter.setOnItemClickListener(MainActivity.this);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, DetailActivity.class);
        GymItem clickedItem = mGymList.get(position);

        detailIntent.putExtra(EXTRA_GYM, clickedItem.getGymName());
        detailIntent.putExtra(EXTRA_MOBILE, clickedItem.getMobile());
        detailIntent.putExtra(EXTRA_RATING, clickedItem.getRating());

        startActivity(detailIntent);
    }
}
package android.example.com.gymapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import static android.example.com.gymapp.MainActivity.EXTRA_GYM;
import static android.example.com.gymapp.MainActivity.EXTRA_MOBILE;
import static android.example.com.gymapp.MainActivity.EXTRA_RATING;


public class DetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String gymName = intent.getStringExtra(EXTRA_GYM);
        String mobile = intent.getStringExtra(EXTRA_MOBILE);
        int rating = intent.getIntExtra(EXTRA_RATING, 0);

        TextView textViewGymName = findViewById(R.id.textview_gymNameDetail);
        TextView textViewRating = findViewById(R.id.textview_ratingDetail);
        TextView textViewMobile = findViewById(R.id.textview_mobileDetail);


        textViewGymName.setText("Gym Name: "+gymName);
        textViewRating.setText("Rating: " +rating);
        textViewMobile.setText("Contact: "+mobile);
    }
}

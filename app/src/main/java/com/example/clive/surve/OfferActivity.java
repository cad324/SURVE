package com.example.clive.surve;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

import com.pollfish.main.PollFish;

public class OfferActivity extends AppCompatActivity {

     /*private PollFish.ParamsBuilder paramsBuilder = new PollFish.ParamsBuilder("SECRET_KEY").build();*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PollFish.initWith(this, new PollFish.ParamsBuilder("SECRET_KEY").build());
        Toast.makeText(getApplicationContext(), "onResume called", Toast.LENGTH_SHORT).show();
    }
}

package com.example.clive.surve;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.kin.ecosystem.Kin;
import com.kin.ecosystem.exception.ClientException;

public class MarketPlace extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_place);
        try {
            Kin.launchMarketplace(MarketPlace.this);
            System.out.println("Public address : " + Kin.getPublicAddress());
        } catch (ClientException e) {
            //
            /*Intent backToDashboard = new Intent(this, Dashboard.class);
            startActivity(backToDashboard);*/
            Toast.makeText(getApplicationContext(), "Could not launch " +
                    "Marketplace", Toast.LENGTH_LONG).show();
        }

    }
}

package com.example.clive.surve;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kin.ecosystem.Kin;
import com.kin.ecosystem.base.Observer;
import com.kin.ecosystem.data.model.Balance;
import com.kin.ecosystem.exception.ClientException;


import java.math.BigDecimal;

public class Dashboard extends AppCompatActivity implements MyOffers.OnFragmentInteractionListener,
        Earn.OnFragmentInteractionListener, Spend.OnFragmentInteractionListener,
        Account.OnFragmentInteractionListener {

    private String balance_text;
    public BigDecimal real_balance;
    TextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.dashboard_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().show();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        titleText = findViewById(R.id.tool_title);

        try {
            Balance cachedBalance = Kin.getCachedBalance();
            BigDecimal balanceVal = cachedBalance.getAmount();
            balance_text = "Balance: "+balanceVal+" KIN";
            titleText.setText(balance_text);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        // Add balance observer
        Observer<Balance> balanceObserver = new Observer<Balance>() {
            @Override
            public void onChanged(Balance value) {
                balance_text = "Balance: "+value.getAmount()+" KIN";
                real_balance = value.getAmount();
                titleText.setText(balance_text);
            }
        };

        try {
            Kin.addBalanceObserver(balanceObserver);
            Toast.makeText(getApplicationContext(), "Balance observer added",
                    Toast.LENGTH_SHORT).show();
        } catch (ClientException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Failed to get account balance: " +
                            e.getMessage(),
                    Toast.LENGTH_LONG).show();
            titleText.setText(balance_text); //sets balance to last retrieved balance
        }
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}

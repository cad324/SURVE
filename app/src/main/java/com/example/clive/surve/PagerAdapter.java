package com.example.clive.surve;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.clive.surve.Account;
import com.example.clive.surve.Earn;
import com.example.clive.surve.MyOffers;
import com.example.clive.surve.Spend;

public class PagerAdapter extends FragmentPagerAdapter{

    private int tabCount;

    public PagerAdapter(FragmentManager fragMan, int numOfTabs) {
        super(fragMan);
        this.tabCount = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                MyOffers myOffersActivity = new MyOffers();
                return myOffersActivity;
            case 1:
                Earn earnActivity = new Earn();
                return earnActivity;
            case 2:
                Spend spendActivity = new Spend();
                return spendActivity;
            case 3:
                Account accountActivity = new Account();
                return accountActivity;
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}

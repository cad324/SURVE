package com.example.clive.surve;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kin.ecosystem.Environment;
import com.kin.ecosystem.Kin;
import com.kin.ecosystem.data.model.WhitelistData;
import com.kin.ecosystem.exception.BlockchainException;
import com.kin.ecosystem.exception.ClientException;

public class MainActivity extends AppCompatActivity {

    EditText phone_number;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
        progressBar = (ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility(View.INVISIBLE);
        phone_number = findViewById(R.id.phone);
        phone_number.setImeOptions(EditorInfo.IME_ACTION_DONE);


        final Button register_button = findViewById(R.id.register_button);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void registerUser() {
        String phoneNum = phone_number.getText().toString();
        //Toast.makeText(getApplicationContext(), phoneNum, Toast.LENGTH_SHORT).show();
        WhitelistData whitelistData = new WhitelistData(phoneNum, "test", "AyINT44OAKagkSav2vzMz");
        try {
            // As an example we are using PLAYGROUND environment
            Kin.start(getApplicationContext(), whitelistData,
                    Environment.getPlayground());
            Intent goToDashboard = new Intent(this, Dashboard.class);
            startActivity(goToDashboard);
        }
        catch (ClientException | BlockchainException e) {
            // Handle exception…
            Toast.makeText(getApplicationContext(), "Failed to register/login", Toast.LENGTH_LONG).show();
        }

    }
}

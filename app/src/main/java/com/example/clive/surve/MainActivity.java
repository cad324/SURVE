package com.example.clive.surve;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.kin.ecosystem.data.Callback;
import com.kin.ecosystem.data.model.WhitelistData;
import com.kin.ecosystem.exception.BlockchainException;
import com.kin.ecosystem.exception.ClientException;

import java.io.IOException;
import java.util.Observer;

import kin.core.KinAccount;
import kin.core.KinClient;
import kin.core.ResultCallback;
import kin.core.ServiceProvider;
import kin.core.exception.CreateAccountException;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    EditText phone_number;
    ProgressBar progressBar;
    ServiceProvider horizonProvider =
            new ServiceProvider("https://horizon-testnet.stellar.org", ServiceProvider.NETWORK_ID_TEST);
    public KinClient kinClient = new KinClient(getApplicationContext(), horizonProvider);
    public KinAccount account;
    private OkHttpClient okHttpClient = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
        progressBar = findViewById(R.id.progress);
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

        try {
            if (!kinClient.hasAccount()) {
                account = kinClient.addAccount();
            } else {
                goToDashboard();
            }
        } catch (CreateAccountException e) {
            e.printStackTrace();
        }

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

    /** Sets up the Kin ecosystem playground environment and brings the user to the main dashboard
     * activity
     */
    private void registerUser() {
        String phoneNum = phone_number.getText().toString();
        //Toast.makeText(getApplicationContext(), phoneNum, Toast.LENGTH_SHORT).show();
        WhitelistData whitelistData = new WhitelistData(phoneNum, "test", "AyINT44OAKagkSav2vzMz");
        try {
            Kin.start(getApplicationContext(), whitelistData,
                    Environment.getPlayground());
        }
        catch (ClientException | BlockchainException e) {
            // Handle exception…
            Toast.makeText(getApplicationContext(), "Failed to register/login", Toast.LENGTH_LONG).show();
        }

        //TODO: Run on separate thread
        Request request = new Request.Builder()
                .url("http://friendbot-kik.kininfrastructure.com/?addr=" + account.getPublicAddress())
                .get()
                .build();
        okHttpClient.newCall(request)
                .enqueue(new okhttp3.Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response)
                            throws IOException {
                        account.activate().run(new ResultCallback<Void>() {
                            @Override
                            public void onResult(Void result) {
                                Toast.makeText(getApplicationContext(), "Account is activated",
                                        Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Exception e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Account not activated",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }

    private void goToDashboard() {
        Intent goToDashboard = new Intent(this, Dashboard.class);
        startActivity(goToDashboard);
        finish();
    }
}

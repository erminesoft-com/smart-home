package com.kozhurov.project294.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.MenuItem;
import android.widget.Switch;

import com.kozhurov.project294.R;

public final class SettingsActivity extends GenericActivity {

    private TextInputEditText serverUrl;
    private Switch offlineMode, verifyPackage;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, SettingsActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initToolbar();
        initBackButton();

        serverUrl = (TextInputEditText) findViewById(R.id.settings_server_url);
        serverUrl.setText(sharedHelper.getServerUrl());

        offlineMode = (Switch) findViewById(R.id.settings_offline_mode);
        offlineMode.setChecked(sharedHelper.isOfflineMode());

        verifyPackage = (Switch) findViewById(R.id.settings_verify_package);
        verifyPackage.setChecked(sharedHelper.isVerifyPackage());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        sharedHelper.setServerUrl(serverUrl.getText().toString());
        sharedHelper.setOfflineMode(offlineMode.isChecked());
        sharedHelper.setVerifyPackage(verifyPackage.isChecked());
    }
}

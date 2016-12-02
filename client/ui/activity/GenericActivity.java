package com.kozhurov.project294.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.Toolbar;

import com.kozhurov.project294.R;
import com.kozhurov.project294.core.DeviceHelper;
import com.kozhurov.project294.core.MultimediaHelper;
import com.kozhurov.project294.core.ProjectApplication;
import com.kozhurov.project294.core.SharedHelper;
import com.kozhurov.project294.db.DbManager;
import com.kozhurov.project294.net.NetManager;

public abstract class GenericActivity extends Activity {

    protected static final String UUID_KEY = "uuid_key";
    protected DbManager dbManager;
    protected NetManager netManager;
    protected SharedHelper sharedHelper;
    protected DeviceHelper deviceHelper;
    protected MultimediaHelper multimediaHelper;
    protected Toolbar toolBar;
    private ProjectApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (ProjectApplication) getApplication();
        dbManager = application.getDbManager();
        netManager = application.getNetManager();
        sharedHelper = application.getSharedHelper();
        deviceHelper = application.getDeviceHelper();
        multimediaHelper = application.getMultimediaHelper();
    }

    protected void initToolbar() {
        toolBar = (Toolbar) findViewById(R.id.my_toolbar);
        setActionBar(toolBar);
    }

    protected void initBackButton() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(true);
    }

    protected boolean isInternetAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    protected boolean isServerAvailable() {
        return netManager.isConnected();
    }

    protected void showShortToast(int resId) {
        showShortToast(getString(resId));
    }

    protected void showShortToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}

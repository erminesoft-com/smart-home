package com.kozhurov.project294.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.kozhurov.project294.R;
import com.kozhurov.project294.core.MultimediaHelper;
import com.kozhurov.project294.model.GeneralModel;
import com.kozhurov.project294.ui.adapter.DeviceAdapter;
import com.kozhurov.project294.util.ConnectivityUtils;
import com.kozhurov.project294.util.DebugUtils;

import java.util.List;

public final class MainActivity extends GenericActivity {

    private ListView deviceList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView serverStatus;
    private BroadcastReceiver connectionListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();

        deviceList = (ListView) findViewById(R.id.device_list);
        deviceList.setEmptyView(findViewById(R.id.devices_empty_view));

        DeviceAdapter adapter = new DeviceAdapter(this, dbManager.getModels());
        deviceList.setAdapter(adapter);
        deviceList.setOnItemClickListener(new ItemClicker());

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.fragment_device);
        swipeRefreshLayout.setOnRefreshListener(new PullToRefresh());

        findViewById(R.id.speech_control_button).setOnClickListener(new Clicker());

        serverStatus = (TextView) findViewById(R.id.server_state);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (sharedHelper.isFirstStart()) {
            dbManager.saveModel(DebugUtils.generateFanModel());
            dbManager.saveModel(DebugUtils.generateConditionModel());
            sharedHelper.setIsFirstStart(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        String state = isServerAvailable() ? getString(R.string.connected) : getString(R.string.disconnected);
        serverStatus.setText(state);

        connectionListener = new ConnectionListener();
        IntentFilter intentFilter = ConnectivityUtils.buildIntentFilter();
        LocalBroadcastManager.getInstance(this).registerReceiver(connectionListener, intentFilter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                SettingsActivity.start(this);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK || data == null) {
            return;
        }

        switch (requestCode) {
            case MultimediaHelper.VOICE_REQUEST:
                List<String> extras = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                multimediaHelper.implementCommand(getResources(), extras, deviceHelper);
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(connectionListener);
        connectionListener = null;
    }

    private final class Clicker implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.speech_control_button:
                    multimediaHelper.requestAudioCommand(MainActivity.this);
                    break;
            }
        }
    }

    private final class ItemClicker implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            GeneralModel generalModel = (GeneralModel) adapterView.getItemAtPosition(i);
            launchProfileFragment(generalModel);
        }

        private void launchProfileFragment(GeneralModel generalModel) {

            switch (generalModel.getType()) {
                case GeneralModel.TYPE_CONDITIONER:
                    //launcher.launchConditionProfileFragment(generalModel.getUuid());
                    break;

                case GeneralModel.TYPE_ELEMENTARY_FAN:
                    ElementaryFanProfile.launch(MainActivity.this, generalModel.getUuid());
                    break;
            }
        }
    }

    private final class PullToRefresh implements SwipeRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            deviceList.setAdapter(new DeviceAdapter(MainActivity.this, dbManager.getModels()));
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private final class ConnectionListener extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            changeServerState(ConnectivityUtils.isConnected(intent));
        }

        private void changeServerState(boolean isConnected) {
            String state = isConnected ? getString(R.string.connected) : getString(R.string.disconnected);
            serverStatus.setText(state);
        }
    }
}

package com.kozhurov.project294.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.kozhurov.project294.R;
import com.kozhurov.project294.model.FanModel;
import com.kozhurov.project294.model.GeneralModel;

public final class ElementaryFanProfile extends GenericActivity {

    private GeneralModel fanModel;
    private TextInputEditText name, description;

    public static void launch(Activity activity, String uuid) {
        Bundle bundle = new Bundle();
        bundle.putString(UUID_KEY, uuid);

        Intent intent = new Intent(activity, ElementaryFanProfile.class);
        intent.putExtras(bundle);

        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_fan);

        initToolbar();
        initBackButton();

        String UUID = getIntent().getExtras().getString(UUID_KEY);
        fanModel = dbManager.getModelById(UUID);

        findViewById(R.id.efan_save).setOnClickListener(new Clicker());

        Switch mswitch = (Switch) findViewById(R.id.efan_switch);
        mswitch.setChecked(fanModel.getState() == GeneralModel.FAN_ON);
        mswitch.setOnCheckedChangeListener(new SwitchListener());

        name = (TextInputEditText) findViewById(R.id.efan_name);
        name.setText(fanModel.getName());

        description = (TextInputEditText) findViewById(R.id.efan_description);
        description.setText(fanModel.getDescription());

    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onMenuItemSelected(featureId, item);
    }

    private void saveAction() {

        fanModel.setName(name.getText().toString());
        fanModel.setDescription(description.getText().toString());

        deviceHelper.changeFanState((FanModel) fanModel);

        finish();
    }

    private void saveWithVerify() {

        if (!isServerAvailable()) {
            showShortToast(R.string.internet_error);
            return;
        }

        dbManager.setWaitTransport();

    }

    private final class SwitchListener implements Switch.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            fanModel.setState(b ? GeneralModel.FAN_ON : GeneralModel.FAN_OFF);
        }
    }

    private final class Clicker implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (sharedHelper.isVerifyPackage()) {

            } else {
                saveAction();
            }
        }
    }
}

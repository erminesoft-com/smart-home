package com.kozhurov.project294.ui.adapter.holder;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kozhurov.project294.R;
import com.kozhurov.project294.model.GeneralModel;

public final class FanHolder extends BaseHolder {

    private TextView stateTextView;

    @Override
    public View bindView(LayoutInflater inflater, ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.item_device_fan, viewGroup, false);
        pic = (ImageView) view.findViewById(R.id.id_device_ico);
        name = (TextView) view.findViewById(R.id.id_device_name);
        descr = (TextView) view.findViewById(R.id.id_device_descr);
        stateTextView = (TextView) view.findViewById(R.id.id_device_state);
        return view;
    }

    @Override
    public void bindData(Context context, GeneralModel generalModel) {
        pic.setImageResource(R.drawable.elementary_fan);
        name.setText(generalModel.getName());
        descr.setText(generalModel.getDescription());

        String state;
        Resources resources = context.getResources();
        if (generalModel.getState() == GeneralModel.FAN_ON) {
            state = resources.getString(R.string.enabled);
        } else {
            state = resources.getString(R.string.disabled);
        }
        stateTextView.setText(state);
    }

}

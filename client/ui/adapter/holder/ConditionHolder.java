package com.kozhurov.project294.ui.adapter.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kozhurov.project294.R;
import com.kozhurov.project294.model.GeneralModel;

public final class ConditionHolder extends BaseHolder {

    @Override
    public View bindView(LayoutInflater inflater, ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.item_device_condition, viewGroup, false);
        pic = (ImageView) view.findViewById(R.id.id_device_ico);
        name = (TextView) view.findViewById(R.id.id_device_name);
        descr = (TextView) view.findViewById(R.id.id_device_descr);
        return view;
    }

    @Override
    public void bindData(Context context, GeneralModel generalModel) {
        pic.setImageResource(R.drawable.condition);
        name.setText(generalModel.getName());
        descr.setText(generalModel.getDescription());
    }
}

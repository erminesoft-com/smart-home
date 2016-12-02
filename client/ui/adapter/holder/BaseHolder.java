package com.kozhurov.project294.ui.adapter.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kozhurov.project294.model.GeneralModel;

public abstract class BaseHolder {

    protected ImageView pic;
    protected TextView name;
    protected TextView descr;

    public abstract View bindView(LayoutInflater inflater, ViewGroup viewGroup);

    public abstract void bindData(Context context, GeneralModel generalModel);

}

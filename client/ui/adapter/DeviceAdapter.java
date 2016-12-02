package com.kozhurov.project294.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.kozhurov.project294.model.GeneralModel;
import com.kozhurov.project294.ui.adapter.holder.BaseHolder;
import com.kozhurov.project294.ui.adapter.holder.ConditionHolder;
import com.kozhurov.project294.ui.adapter.holder.FanHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DeviceAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private final Picasso picasso;
    private List<GeneralModel> models;

    public DeviceAdapter(Context context, List<GeneralModel> models) {
        inflater = LayoutInflater.from(context);
        picasso = Picasso.with(context);
        this.models = models;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public GeneralModel getItem(int i) {
        return models.get(i);
    }

    @Override
    public long getItemId(int i) {
        return models.get(i).hashCode();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getType();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        BaseHolder holder;

        if (getItemViewType(position) == GeneralModel.TYPE_ELEMENTARY_FAN) {
            if (view == null) {
                holder = new FanHolder();
                view = holder.bindView(inflater, viewGroup);
                view.setTag(holder);
            } else {
                holder = (FanHolder) view.getTag();
            }

            holder.bindData(inflater.getContext(), getItem(position));
            return view;
        }

        if (getItemViewType(position) == GeneralModel.TYPE_CONDITIONER) {
            if (view == null) {
                holder = new ConditionHolder();
                view = holder.bindView(inflater, viewGroup);
                view.setTag(holder);
            } else {
                holder = (FanHolder) view.getTag();
            }

            holder.bindData(inflater.getContext(), getItem(position));
            return view;
        }

        return view;
    }
}

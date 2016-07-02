package com.connorlin.databinding.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.connorlin.databinding.R;
import com.connorlin.databinding.databinding.ListItemBinding;
import com.connorlin.databinding.model.ObservableContact;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: ListAdapter
 * Description:
 * Author: connorlin
 * Date: Created on 2016-6-28.
 */
public class ListAdapter extends BaseAdapter {

    private final LayoutInflater mInflater;

    private List<ObservableContact> mContactsList = new ArrayList<>();

    public ListAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addContact(ObservableContact contact) {
        mContactsList.add(contact);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mContactsList.size();
    }

    @Override
    public ObservableContact getItem(int position) {
        return mContactsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_item, parent, false);
            holder.listItemBinding = DataBindingUtil.bind(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.listItemBinding.setContact(getItem(position));

        return convertView;
    }

    class ViewHolder {
        ListItemBinding listItemBinding;
    }
}

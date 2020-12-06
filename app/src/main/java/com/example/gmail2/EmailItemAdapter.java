package com.example.gmail2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

public class EmailItemAdapter extends BaseAdapter implements Filterable {

    Context context;
    List<EmailItem> items;
    List<EmailItem> itemsFiltered;

    public EmailItemAdapter(Context context, List<EmailItem> items) {
        this.context = context;
        this.items = items;
        this.itemsFiltered = items;
    }

    @Override
    public int getCount() {
        return itemsFiltered.size();
    }

    @Override
    public Object getItem(int i) {
        return itemsFiltered.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    itemsFiltered = items;
                }
                else if (charString == "NoFavorite"){
                    itemsFiltered = items;
                }
                else if (charString == "Favorite"){
                    List<EmailItem> filteredList = new ArrayList<>();
                    for (EmailItem row : items) {
                        if (row.getFavorite()) {
                            filteredList.add(row);
                        }
                    }
                    itemsFiltered = filteredList;
                }
                else {
                    List<EmailItem> filteredList = new ArrayList<>();
                    for (EmailItem row : items) {
                        if (row.getSender().toLowerCase().contains(charString.toLowerCase()) || row.getSubject().toLowerCase().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }
                    itemsFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = itemsFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                itemsFiltered = (List<EmailItem>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.email_item, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.mAvatar = view.findViewById(R.id.avatar);
            viewHolder.mSender= view.findViewById(R.id.sender);
            viewHolder.mbrief = view.findViewById(R.id.brief);
            viewHolder.mDate = view.findViewById(R.id.date);
            viewHolder.mSubject = view.findViewById(R.id.subject);
            viewHolder.mFavorite = view.findViewById(R.id.favorite);
            view.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder)view.getTag();

        EmailItem item = itemsFiltered.get(i);
        viewHolder.mAvatar.setImageResource(item.getAvatar());
        viewHolder.mSender.setText(item.getSender());
        viewHolder.mbrief.setText(item.getBrief());
        viewHolder.mSubject.setText(item.getSubject());
        viewHolder.mDate.setText(item.getDate().toString());
        viewHolder.mFavorite.setImageResource(item.getFavorite()?R.drawable.ic_baseline_star_24:R.drawable.ic_baseline_star_border_24);

        return view;
    }

    private class ViewHolder {
        public ImageView mAvatar;
        public TextView mSender;
        public TextView mSubject;
        public TextView mbrief;
        public TextView mDate;
        public ImageView mFavorite;
    }
}
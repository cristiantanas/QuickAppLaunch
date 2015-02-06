package com.ctanas.android.applaunch.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.ctanas.android.applaunch.R;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends BaseAdapter implements Filterable {

    private List<AppInfo>   applicationList = new ArrayList<>();
    private List<AppInfo>   filteredApplicationList = new ArrayList<>();
    private LayoutInflater  layoutInflater;

    private Filter          filter;

    public SearchAdapter(Context context, List<AppInfo> appInfoArrayList) {
        this.applicationList.addAll( appInfoArrayList );
        this.filteredApplicationList.addAll( appInfoArrayList );
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.filteredApplicationList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.filteredApplicationList.get( position );
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if ( convertView == null ) {
            convertView = layoutInflater.inflate(R.layout.list_item, parent, false);
        }

        AppInfo applicationInfo = this.filteredApplicationList.get( position );

        ImageView applicationIcon = (ImageView) convertView.findViewById(R.id.applicationIcon);
        applicationIcon.setImageDrawable( applicationInfo.getApplicationIcon() );

        TextView applicationName = (TextView) convertView.findViewById(R.id.applicationName);
        applicationName.setText( applicationInfo.getApplicationName() );

        return convertView;
    }

    @Override
    public Filter getFilter() {

        if ( this.filter == null ) {
            this.filter = new FilterByAppName();
        }

        return this.filter;
    }

    private class FilterByAppName extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults filterResults = new FilterResults();

            if ( constraint != null && constraint.length() > 0 ) {

                List<AppInfo> filteredAppInfo = new ArrayList<>();

                for ( AppInfo appInfo : applicationList ) {

                    if ( appInfo.getApplicationName().toLowerCase().contains(
                            constraint.toString().toLowerCase() )
                    ) {
                        filteredAppInfo.add( appInfo );
                    }

                }

                filterResults.values = filteredAppInfo;
                filterResults.count = filteredAppInfo.size();
            }
            else  {

                filterResults.values = applicationList;
                filterResults.count = applicationList.size();
            }

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            List<AppInfo> appInfoList = (List<AppInfo>) results.values;
            filteredApplicationList.clear();
            filteredApplicationList.addAll( appInfoList );

            notifyDataSetChanged();
        }
    }
}

package com.ctanas.android.applaunch;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ctanas.android.applaunch.adapter.AppInfo;
import com.ctanas.android.applaunch.adapter.SearchAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class HomeActivity extends ActionBarActivity {

    private ListView        appListView;
    private SearchAdapter   appListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle( getString(R.string.title_activity_main) );
        actionBar.setDisplayShowHomeEnabled( true );
        actionBar.setDisplayShowTitleEnabled( true );

        PackageManager packageManager = getPackageManager();

        List<ResolveInfo> activitiesResolveInfo = this.getInstalledActivitiesWithMainIntent(packageManager);
        List<AppInfo> appInfoList = this.transform(packageManager, activitiesResolveInfo);

        this.appListViewAdapter = new SearchAdapter(this, appInfoList);

        this.appListView = (ListView) findViewById(R.id.appList);
        this.appListView.setAdapter( this.appListViewAdapter );

        this.appListView.setOnItemClickListener( listViewClickListener );
    }

    private List<ResolveInfo> getInstalledActivitiesWithMainIntent(PackageManager packageManager) {

        Intent mainIntent = new Intent(Intent.ACTION_MAIN);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        return packageManager.queryIntentActivities(mainIntent, 0);
    }

    private List<AppInfo> transform(PackageManager packageManager, List<ResolveInfo> resolveInfoList) {

        ArrayList<AppInfo> appInfoArrayList = new ArrayList<>();


        for ( ResolveInfo resolveInfo : resolveInfoList ) {

            if ( resolveInfo.activityInfo != null ) {

                AppInfo currentAppInfo = new AppInfo();

                currentAppInfo.setApplicationName( resolveInfo.loadLabel(packageManager).toString() );
                currentAppInfo.setApplicationIcon( resolveInfo.loadIcon(packageManager) );

                Intent launchingIntent = packageManager.getLaunchIntentForPackage( resolveInfo.activityInfo.packageName );
                currentAppInfo.setLaunchingIntent( launchingIntent );

                appInfoArrayList.add( currentAppInfo );
            }
        }

        Collections.sort( appInfoArrayList, new AppInfo.ApplicationNameComparator() );

        return appInfoArrayList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        // Assume current activity as the searchable activity
        searchView.setSearchableInfo( searchManager.getSearchableInfo( getComponentName() ) );
        searchView.setIconifiedByDefault( false );
        searchView.setOnQueryTextListener( onQueryTextListener );
        searchView.requestFocus();

        return true;
    }

    AdapterView.OnItemClickListener listViewClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            AppInfo appInfo = (AppInfo) parent.getAdapter().getItem( position );
            startActivity( appInfo.getLaunchingIntent() );

            finish();
        }
    };

    SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {

        @Override
        public boolean onQueryTextSubmit(String newText) {
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {

            appListViewAdapter.getFilter().filter( newText );
            return true;
        }
    };

}

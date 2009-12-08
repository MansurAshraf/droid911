package com.mansoor.app.droid911.activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.mansoor.app.droid911.adapter.POIAdapter;
import com.mansoor.app.droid911.bean.POI;
import com.mansoor.app.droid911.service.POIService;
import com.mansoor.app.droid911.service.POIServiceImpl;
import com.mansoor.app.droid911.util.C;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Mansoor
 * Date: Dec 7, 2009
 * Time: 8:23:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class POIList extends ListActivity
{
    private String status;
    private String city;
    private String latitude;
    private String state;
    private String country;
    private String longitude;
    private POIAdapter poiAdapter;
    private List<POI> POIs;
    private POIService poiService = new POIServiceImpl();

    private TextView emptyView;
    private String query;
    private final Handler handler = new Handler()
    {
        public void handleMessage(final Message msg)
        {

            dismissDialog(C.Progress);
            if (POIs == null)
            {
                emptyView.setText(C.EMPTY_MESSAGE);
                POIs = new ArrayList<POI>();
            }

            poiAdapter = new POIAdapter(getApplicationContext(), R.layout.row, POIs);
            setListAdapter(poiAdapter);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        final ListView listView = getListView();

        listView.setItemsCanFocus(false);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnCreateContextMenuListener(this);
        emptyView = (TextView) findViewById(R.id.empty);
        listView.setEmptyView(emptyView);
       // LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         getIntentValues();
        //   adView = vi.inflate(R.layout.admob, null);
        getData();
    }


    private void getIntentValues()
    {
        status = this.getIntent().getExtras().getString(C.STATUS);
        query = this.getIntent().getExtras().getString(C.QUERY);
        if (status.equals(C.NEW))
        {
            city = this.getIntent().getExtras().getString(C.CITY);
            state = this.getIntent().getExtras().getString(C.STATE);
            country = this.getIntent().getExtras().getString(C.COUNTRY);
        }
        else if (status.equals(C.CURRENT))
        {
            latitude = String.valueOf(this.getIntent().getExtras().get(C.LATITUDE));
            longitude = String.valueOf(this.getIntent().getExtras().get(C.LONGITUDE));
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);//To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected Dialog onCreateDialog(int id)
    {
        ProgressDialog dialog = null;
        switch (id)
        {
            case C.Progress:
                ProgressDialog progressDialog = new ProgressDialog(POIList.this);

                progressDialog.setTitle("Searching");
                progressDialog.setMessage("");
                // progressDialog.setIndeterminate(true);
                dialog = progressDialog;
        }

        return dialog;

    }


    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId())
        {
            case R.id.call_menu:
                makeCall(info.position);
                return true;
            case R.id.directon_menu:
                getDirections(info.position);
                return true;
        }
        return false;  //To change body of overridden methods use File | Settings | File Templates.
    }/* Handles item selections */

    private void getDirections(int position)
    {
        final POI poi = POIs.get(position);
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + poi.getLat() + "," + poi.getLon()));
        startActivity(i);

    }

    private void makeCall(int position)
    {
        final POI poi = POIs.get(position);
        Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + poi.getPhone()));
        startActivity(i);
    }


    private void getData()
    {

        showDialog(C.Progress);
        ServiceThread t = new ServiceThread();
        t.start();
    }


    private class ServiceThread extends Thread
    {


        @Override
        public void run()
        {
            super.run();    //To change body of overridden methods use File | Settings | File Templates.
            try
            {
                if (status.equals(C.NEW))
                {
                    POIs = poiService.getPoI(query, city, state, country);
                }
                else if (status.equals(C.CURRENT))
                {
                    POIs = poiService.getPoI(query, latitude, longitude);
                }

                /* if (resturants != null)
                {

                    getListView().addFooterView(adView);
                }*/
                handler.sendEmptyMessage(0);
            }
            catch (Exception e)
            {
                // throw new RuntimeException(e);

            }

        }
    }
}

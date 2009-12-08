package com.mansoor.app.droid911.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.mansoor.app.droid911.activities.R;
import com.mansoor.app.droid911.bean.POI;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Mansoor
 * Date: Dec 7, 2009
 * Time: 9:39:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class POIAdapter extends ArrayAdapter<POI>
{

    private final Context context;
    private final List<POI> POIs;

    public POIAdapter(Context context, int textViewResourceId, List<POI> POIs)
    {

        super(context, textViewResourceId, POIs);
        this.context = context;
        this.POIs = POIs;
    }

    public int getCount()
    {
        return POIs.size();  //To change body of implemented methods use File | Settings | File Templates.
    }

    public POI getItem(int i)
    {
        return POIs.get(i);  //To change body of implemented methods use File | Settings | File Templates.
    }

    public long getItemId(int i)
    {
        return i;  //To change body of implemented methods use File | Settings | File Templates.
    }


    public View getView(int i, View currentView, ViewGroup viewGroup)
    {
        final POI poi = POIs.get(i);

        View v = currentView;


        if (v == null)
        {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.row, null);
        }


        final TextView name_field = (TextView) v.findViewById(R.id.name);
        name_field.setText(poi.getTitle());

        final TextView street = (TextView) v.findViewById(R.id.street);
        street.setText(poi.getStreet());

        final TextView city = (TextView) v.findViewById(R.id.city);
        city.setText(poi.getCity());


        final TextView phone = (TextView) v.findViewById(R.id.phone);
        phone.setText(poi.getPhone());

        return v;
    }
}
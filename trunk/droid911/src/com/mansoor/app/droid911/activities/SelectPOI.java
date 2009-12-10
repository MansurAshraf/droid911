package com.mansoor.app.droid911.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.mansoor.app.droid911.util.C;

/**
 * Created by IntelliJ IDEA.
 * User: Mansoor
 * Date: Dec 7, 2009
 * Time: 8:24:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class SelectPOI extends Activity implements View.OnClickListener
{
    private String status;
    private String city;
    private String latitude;
    private String state;
    private String country;
    private String longitude;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories);
        getIntentValues();
        setActionListeners();

    }

    private void setActionListeners()
    {
        Button police = (Button) findViewById(R.id.police);
        Button hospital = (Button) findViewById(R.id.hospital);
        Button autorepair = (Button) findViewById(R.id.autorepair);
        Button towtruck = (Button) findViewById(R.id.towtruck);
        Button bank = (Button) findViewById(R.id.bank);
        Button gastation = (Button) findViewById(R.id.gaststation);
        Button fire = (Button) findViewById(R.id.fire);
        Button atm = (Button) findViewById(R.id.atm);


        police.setOnClickListener(this);
        hospital.setOnClickListener(this);
        towtruck.setOnClickListener(this);
        autorepair.setOnClickListener(this);
        bank.setOnClickListener(this);
        gastation.setOnClickListener(this);
        fire.setOnClickListener(this);
        atm.setOnClickListener(this);
    }

    private void getIntentValues()
    {
        status = this.getIntent().getExtras().getString(C.STATUS);
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

    public void onClick(View view)
    {
        Intent i = new Intent(view.getContext(), POIList.class);
        i.putExtra(C.STATUS, status);
        i.putExtra(C.CITY, city);
        i.putExtra(C.STATE, state);
        i.putExtra(C.COUNTRY, country);
        i.putExtra(C.LONGITUDE, longitude);
        i.putExtra(C.LATITUDE, latitude);

        switch (view.getId())
        {

            case R.id.police:
                i.putExtra(C.QUERY, C.POLICE_STATION);
                break;
            case R.id.hospital:
                i.putExtra(C.QUERY, C.HOSPITAL);
                break;
            case R.id.autorepair:
                i.putExtra(C.QUERY, C.AUTO_WORKSHOP);
                break;
            case R.id.towtruck:
                i.putExtra(C.QUERY, C.TOW_TRUCK);
                break;
            case R.id.bank:
                i.putExtra(C.QUERY, C.BANK);
                break;
            case R.id.gaststation:
                i.putExtra(C.QUERY, C.GAS_STATION);
                break;
            case R.id.fire:
                i.putExtra(C.QUERY, C.FIRE_STATION);
                break;
            case R.id.atm:
                i.putExtra(C.QUERY, C.ATM);
                break;
        }

        startActivity(i);
    }
}

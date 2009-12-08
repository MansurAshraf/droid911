package com.mansoor.app.droid911.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.mansoor.app.droid911.util.C;

/**
 * Created by IntelliJ IDEA.
 * User: Mansoor
 * Date: Dec 7, 2009
 * Time: 8:25:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class EnterAddress extends Activity implements View.OnClickListener
{
    EditText cityField;
    EditText stateField;
    EditText countryField;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.address);

        cityField = (EditText) findViewById(R.id.city);
        stateField = (EditText) findViewById(R.id.state);
        countryField = (EditText) findViewById(R.id.country);
        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);
        displayMessage();
    }

    private void displayMessage()
    {
        boolean displayMessage = this.getIntent().getExtras().getBoolean(C.DISPLAY_MESSAGE);
        if (displayMessage)
        {
            TextView messageBox = (TextView) findViewById(R.id.message);
            messageBox.setText(C.MESSAGE);
        }
    }

    public void onClick(View view)
    {
        Intent i = new Intent(view.getContext(), SelectPOI.class);
        i.putExtra(C.CITY, cityField.getText().toString());
        i.putExtra(C.STATE, stateField.getText().toString());
        i.putExtra(C.COUNTRY, countryField.getText().toString());
        i.putExtra(C.STATUS, C.NEW);
        startActivity(i);
    }
}

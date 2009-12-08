package com.mansoor.app.droid911.service;

import com.mansoor.app.droid911.bean.POI;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Mansoor
 * Date: Dec 7, 2009
 * Time: 9:08:02 PM
 * To change this template use File | Settings | File Templates.
 */
public interface POIService
{
    public List<POI> getPoI(String query, String lat, String lon) throws IOException, JSONException;

    public List<POI> getPoI(String query, String city, String state, String country) throws IOException, JSONException;

   

}

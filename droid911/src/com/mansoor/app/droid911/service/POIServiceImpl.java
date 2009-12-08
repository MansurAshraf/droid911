package com.mansoor.app.droid911.service;

import android.util.Log;
import com.mansoor.app.droid911.bean.POI;
import com.mansoor.app.droid911.util.C;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Mansoor
 * Date: Dec 7, 2009
 * Time: 9:13:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class POIServiceImpl implements POIService
{
    public List<POI> getPoI(String query, String lat, String lon) throws IOException, JSONException
    {
        String url;
        List<POI> list = new ArrayList<POI>();
        url = C.BASE_URL + URLEncoder.encode(query) + "&sll=" + lat + "," + lon;
        Log.i("lat", lat);
        Log.i("lon", lon);
        Log.i("url", url);
        String response = sendRequest(url);
        JSONObject json = new JSONObject(response);
        JSONObject responseData = json.getJSONObject("responseData");
        JSONArray jsonArray = responseData.getJSONArray("results"); //To change body of implemented methods use File | Settings | File Templates.
        transformResult(list,jsonArray);
        return list;
    }

    public List<POI> getPoI(String query, String city, String state, String country) throws IOException, JSONException
    {
        String url;
        List<POI> list = new ArrayList<POI>();
        url = C.BASE_URL + URLEncoder.encode(query + " " + city + " " + state + " " + country);

        String response = sendRequest(url);
        JSONObject json = new JSONObject(response);
        JSONObject responseData = json.getJSONObject("responseData");
        JSONArray jsonArray = responseData.getJSONArray("results");
        transformResult(list, jsonArray);

        return list;
    }


    private void transformResult(List<POI> list, JSONArray jsonArray)
            throws JSONException
    {
        for (int i = 0; i < jsonArray.length(); i++)
        {
            POI bean = new POI();
            JSONObject result = jsonArray.getJSONObject(i);
            bean.setTitle(result.getString("titleNoFormatting"));
            bean.setCity(result.getString("city"));
            bean.setRegion(result.getString("region"));
            bean.setStreet(result.getString("streetAddress"));
            bean.setLat(result.getString("lat"));
            bean.setLon(result.getString("lng"));
            JSONArray phone = result.getJSONArray("phoneNumbers");
            bean.setPhone(phone.getJSONObject(0).getString("number"));
            list.add(bean); //To change body of implemented methods use File | Settings | File Templates.
        }
    }

    /**
     * Method sendRequest ...
     *
     * @param url of type String
     * @return String
     * @throws java.io.IOException when
     */
    private String sendRequest(String url) throws IOException
    {

        HttpClient client = new DefaultHttpClient();
        client.getParams().setIntParameter("http.connection.timeout", new Integer(15 * 1000));
        client.getParams().setIntParameter("http.socket.timeout", new Integer(30 * 1000));
        HttpGet method = new HttpGet(url);

        final HttpResponse response = client.execute(method);

        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
        {
            throw new IOException("Connection Failed");
        }

        final InputStream stream = response.getEntity().getContent();

        return this.inputStreamToString(stream);

    }


    /**
     * Method inputStreamToString ...
     *
     * @param stream of type InputStream
     * @return String
     * @throws IOException when
     */
    private String inputStreamToString(final InputStream stream) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null)
        {
            sb.append(line + "\n");
        }
        br.close();
        return sb.toString();
    }
}

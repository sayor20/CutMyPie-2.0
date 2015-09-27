package com.sayor.org.cutmypie;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.parse.GetCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Parse.initialize(this, "pvoUWtH7upbjefp5UComlHmjoYVCEK7Fb3OkMZqc", "j4UnaWOHu7g2HPJ4GAXtjXMeXSoNE5uOkaqP18fs");
        ParseAnalytics.trackAppOpened(getIntent());
/*
        FoodData foodData = new FoodData();
        foodData.setFeedcap("6");
        foodData.setFooddesc("paid food");
        foodData.setTimeexp("2");
        foodData.setLat(37.33);
        foodData.setLon(121.88);
        foodData.saveInBackground();
*/
        ParseObject testObject = new ParseObject("testobject");
        testObject.put("fooddesc", "this is test 1");
        testObject.put("foodcap", "1");
        testObject.put("timeexp", "1");
        testObject.put("lat", "51.5");
        testObject.put("lon", "0.12");
        testObject.saveInBackground();

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fragment);
        mapFragment.getMapAsync(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

       /* ParseQuery<FoodData> query = ParseQuery.getQuery(FoodData.class);

          query.findInBackground(new FindCallback<test>() {

            @Override
            public void done(List<FoodData> list, ParseException e) {
                if(e==null) {
                    for (FoodData fooddata : list) {
                        googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(fooddata.getLon(), fooddata.getLon()))
                                .title("Marker"));
                        Toast.makeText(MainActivity.this, fooddata.getLat() + "  " + fooddata.getLon(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Log.d("item", "Error:"+e.getMessage());
                }
            }
        });

        */

        ParseQuery<ParseObject> query = ParseQuery.getQuery("testobject");
        query.getInBackground("8qGMRF6fEc", new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    Toast.makeText(MainActivity.this, object.getString("lat") + "  " + object.getString("lon"), Toast.LENGTH_SHORT).show();
                } else {
                    // something went wrong
                }
            }
        });


    }
}

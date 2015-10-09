package com.sayor.org.cutmypie;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.activeandroid.query.Select;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    private ConnectivityManager cm;
    private NetworkInfo ni;
    private FoodData foodData;
    byte[] bytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        ParseAnalytics.trackAppOpened(getIntent());

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

        if (id == R.id.action_post) {
            Intent i = new Intent(this, PostActivity.class);
            startActivity(i);
            return true;
        }

        if (id == R.id.action_conversation) {
            Intent i = new Intent(this, ConversationActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("FoodData");

        ni = cm.getActiveNetworkInfo();

        if(ni!=null && ni.isConnectedOrConnecting()==true) {
            query.findInBackground(new FindCallback<ParseObject>() {

                @Override
                public void done(List<ParseObject> list, ParseException e) {
                    if (e == null) {
                        for (ParseObject parseObject : list) {

                            googleMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(parseObject.getDouble("lat"), parseObject.getDouble("lon")))
                                    .title(parseObject.getString("fooddesc"))
                                    .snippet(parseObject.getString("ownername")));
                            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                @Override
                                public void onInfoWindowClick(Marker marker) {
                                    Intent i = new Intent(MapsActivity.this, DetailsActivity.class);
                                    i.putExtra("marker", marker.getTitle());
                                    startActivity(i);
                                }
                            });
                            ParseFile parseFile = parseObject.getParseFile("photo");
                            try {
                                bytes = parseFile.getData();
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }
                            FoodData foodData = new Select().from(FoodData.class).where("fooddesc=?",parseObject.getString("fooddesc")).executeSingle();

                            if(foodData==null) {
                                foodData = new FoodData(parseObject.getString("fooddesc"), parseObject.getString("foodcap"), parseObject.getString("timeexp"), parseObject.getDouble("lat"), parseObject.getDouble("lon"), bytes, parseObject.getString("ownerid"), parseObject.getString("ownername"));
                                foodData.save();
                            }
                        }
                    } else {
                        Log.d("item", "Error:" + e.getMessage());
                    }
                }
            });
        }
        else{
            List<FoodData> foodDataList = new Select().from(FoodData.class).execute();
                for (FoodData foodData : foodDataList) {
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(foodData.getLat(), foodData.getLon()))
                            .title(foodData.getFooddesc()));
                }
        }
    }
}

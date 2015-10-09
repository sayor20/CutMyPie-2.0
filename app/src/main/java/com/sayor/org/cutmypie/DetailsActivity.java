package com.sayor.org.cutmypie;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.query.Select;


public class DetailsActivity extends ActionBarActivity {

    TextView tvDesc;
    ImageView ivPhoto;
    FoodData foodData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        loadView();

        Intent i = getIntent();
        String fooddesc =i.getStringExtra("marker");
        foodData = new Select().from(FoodData.class).where("fooddesc=?", fooddesc).executeSingle();
        tvDesc.setText(foodData.getFooddesc());
        byte[] imgFile = foodData.getImage();
        Bitmap bm = null;

        bm = BitmapFactory.decodeByteArray(imgFile, 0 , imgFile.length);

        ivPhoto.setImageBitmap(bm);
    }

    private void loadView() {
        tvDesc = (TextView)findViewById(R.id.tvDesc);
        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
    }

    public void onContact(View v) {
        Intent i =new Intent(this, ChatActivity.class);
        i.putExtra("receiverId", foodData.getOwnerid());
        i.putExtra("receiverName", foodData.getOwnername() );
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}

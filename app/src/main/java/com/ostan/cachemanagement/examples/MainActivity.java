package com.ostan.cachemanagement.examples;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.ostan.cachemanagement.R;
import com.ostan.cachemanagement.cache.ImageCacheableTask;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final ImageView imageView = (ImageView) findViewById(R.id.imageView);

        // Example 1:
        ImageCacheableTask.setNetworkImageToView(this,
                "http://voices.nationalgeographic.com/files/2014/08/NationalGeographic_1272918.jpg",
                imageView, R.mipmap.ic_launcher, R.mipmap.ic_launcher);

        // Example 2:
        new ImageCacheableTask<Drawable>(this, new ImageCacheableTask.DataReceivedCallback<Drawable>() {
            @Override
            public void onDataReceived(Drawable data) {
                imageView.setImageDrawable(data);
            }
        }).execute("http://voices.nationalgeographic.com/files/2014/08/NationalGeographic_1272918.jpg");

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
}

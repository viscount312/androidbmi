package com.demo.android.trainstation;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

public class Main extends MapActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
//        MapView map = new MapView(this, "0vUBBiZoYQvqxxLi0pgax3xP_ccG6zB-WhI4U5Q");
//        setContentView(map);
        findViews();
        setupMap();
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private MapView map;
    private MapController controller;

    private void findViews() {
            map = (MapView) findViewById(R.id.map);
            controller = map.getController();
    }

    private void setupMap() {
            GeoPoint station_taipei = new GeoPoint(
                            (int) (25.047192 * 1000000),
                            (int) (121.516981 * 1000000)
            );
            map.setTraffic(false);
            map.setBuiltInZoomControls(true);
            controller.setZoom(17);
            controller.animateTo(station_taipei);
    }
    
    protected static final int MENU_TAIPEI = Menu.FIRST;
    protected static final int MENU_TAICHUNG = Menu.FIRST+1;
    protected static final int MENU_KAOSHONG = Menu.FIRST+2;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            // TODO Auto-generated method stub
            menu.add(0, MENU_TAIPEI, 0, "台北");
            menu.add(0, MENU_TAICHUNG, 0, "台中");
            menu.add(0, MENU_KAOSHONG, 0, "高雄");
            return super.onCreateOptionsMenu(menu);
    }

    GeoPoint station_taipei = new GeoPoint(
                (int) (25.047192 * 1000000),
                    (int) (121.516981 * 1000000)
            );
    GeoPoint station_taichung = new GeoPoint(
                (int) (24.136895 * 1000000),
                    (int) (120.684975 * 1000000)
            );
    GeoPoint station_kaoshong = new GeoPoint(
                (int) (22.639359 * 1000000),
                    (int) (120.302628 * 1000000)
            );

    public boolean onOptionsItemSelected(MenuItem item) {
            // TODO Auto-generated method stub
            super.onOptionsItemSelected(item);
            switch(item.getItemId()) {
                    case MENU_TAIPEI:
                            controller.animateTo(station_taipei);
                            break;
                    case MENU_TAICHUNG:
                            controller.animateTo(station_taichung);
                            break;
                    case MENU_KAOSHONG:
                            controller.animateTo(station_kaoshong);
                            break;
            }
            return super.onOptionsItemSelected(item);
    }
    
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_I || keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            // Zooming In
//            controller.setZoom(map.getZoomLevel() + 1);
        	controller.setZoom(Math.min(map.getMaxZoomLevel(), map.getZoomLevel() + 1));
        	return true;
        } else if (keyCode == KeyEvent.KEYCODE_O || keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            // Zooming Out
//            controller.setZoom(map.getZoomLevel() - 1);
        	controller.setZoom(Math.max(15, map.getZoomLevel() - 1));
        	return true;
        } else if (keyCode == KeyEvent.KEYCODE_S) {
            // Switch to satellite view
            map.setSatellite(true) ;
            map.setTraffic(false);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_T) {
            // Switch on traffic overlays
            map.setSatellite(false) ;
            map.setTraffic(true);
            return true;
        }
        return false;
    }

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		//check connectivity
    	ConnectivityManager connectivityMgr =  (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
    	NetworkInfo netInfo = connectivityMgr.getActiveNetworkInfo();
    	if (netInfo == null) {
    		new AlertDialog.Builder(Main.this)
        	.setTitle("No Connection")
        	.setMessage("Please enable internet connection before use this app")
        	.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int whichButton) {
    		    	startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
    			}
        	})
        	.show();
    	}
	}
}
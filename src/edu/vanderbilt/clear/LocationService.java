package edu.vanderbilt.clear;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * This service updates the device's location using the NETWORK_PROVIDER rather than GPS_PROVIDER.  This is less accurate but uses vastly less battery power.
 * @author Zach McCormick
 *
 */
public class LocationService extends Service {
	
	Location currentLocation;
	LocationManager lm;
	LocationUpdateHandler mLocHandler;
	
	@Override
	public IBinder onBind(Intent arg0) {
		mLocHandler = new LocationUpdateHandler();
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 0, mLocHandler);
		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 15000, 0, mLocHandler);
		return mBinder;
	}
	
	@Override
	public boolean onUnbind(Intent i) {
		if (mLocHandler != null) {
			lm.removeUpdates(mLocHandler);
		}
		return false;
	}

	private final ILocationService.Stub mBinder = new ILocationService.Stub() {

		@Override
		public double getLat() throws RemoteException {
			if (currentLocation != null) {
				return currentLocation.getLatitude();
			} else {
				return 0.0;
			}

		}

		@Override
		public double getLon() throws RemoteException {
			if (currentLocation != null) {
				return currentLocation.getLongitude();
			} else {
				return 0.0;
			}
			
		}
		
	};
	
	private class LocationUpdateHandler implements LocationListener {
		// initially have the currentProvider be the network provider
		String currentProvider = LocationManager.NETWORK_PROVIDER;
		

		@Override
		public void onLocationChanged(Location loc) {
			if (loc.getProvider().equalsIgnoreCase(currentProvider)) {
				Log.i("LocationService", "Location Updated! " + loc.toString());
			    currentLocation = new Location(loc);
			}
		}

		@Override
		public void onProviderDisabled(String provider) {
			if (provider.equalsIgnoreCase(LocationManager.GPS_PROVIDER)) {
				currentProvider = LocationManager.NETWORK_PROVIDER;
			}
		}

		@Override
		public void onProviderEnabled(String provider) {
			if (provider.equalsIgnoreCase(LocationManager.GPS_PROVIDER)) {
				currentProvider = provider;
			}
		}

		@Override
		public void onStatusChanged(String provider, int status, 
		  Bundle extras) {}
	};
}

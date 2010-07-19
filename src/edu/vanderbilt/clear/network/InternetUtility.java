package edu.vanderbilt.clear.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetUtility {
	public static boolean hasInternet(Context myContext) {
		ConnectivityManager connec = (ConnectivityManager) myContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		boolean mobile = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED;
		boolean wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
		return mobile || wifi;
	}
}

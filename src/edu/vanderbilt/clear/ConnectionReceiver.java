package edu.vanderbilt.clear;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ConnectionReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// this is receiving notification that a data connection has been made
		// when a data connection is made, we start the data service that checks if there is any data to be sent and terminates when finished.
		context.startService(new Intent(context, edu.vanderbilt.clear.DataService.class));
	}

}

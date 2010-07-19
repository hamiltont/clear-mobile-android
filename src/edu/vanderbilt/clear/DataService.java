package edu.vanderbilt.clear;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.util.Log;
import edu.vanderbilt.clear.database.DatabaseReader;
import edu.vanderbilt.clear.database.DatabaseWriter;
import edu.vanderbilt.clear.network.InternetUtility;
import edu.vanderbilt.clear.network.SendData;

/**
 * This service sends data to the server from the data queue.
 * @author Zach McCormick
 *
 */
public class DataService extends Service {
	Context myContext = this;
	DatabaseReader dbr;
	DatabaseWriter dbw;
	Cursor c;
	String currentData;
	int result;
	
	@Override
	public void onCreate() {
		super.onCreate();
		start();
	}
	
	/**
	 * Send data if we can, terminate if we cannot.
	 */
	public void start() {
		dbr = new DatabaseReader(myContext);
		dbw = new DatabaseWriter(myContext);
		if (dbr.open() && dbw.open()) {
			c = dbr.getData();
			while (c.moveToNext() && InternetUtility.hasInternet(myContext)) {
				currentData = c.getString(c.getColumnIndex("data"));
				result = SendData.sendData(currentData);
				if (result != -1) {
					Log.v("DataService", "Data sent.  ID: " + result);
					dbw.deleteData(c.getLong(c.getColumnIndex("_id")));
				}
			}
			c.close();
			dbr.close();
			dbw.close();
			DataService.this.stopSelf();
		} else {
			DataService.this.stopSelf();
		}			

		
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
}

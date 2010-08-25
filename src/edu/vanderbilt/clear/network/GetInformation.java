package edu.vanderbilt.clear.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import android.os.RemoteException;
import edu.vanderbilt.clear.Homescreen;
import edu.vanderbilt.clear.Settings;
import edu.vanderbilt.clear.Utils;
import edu.vanderbilt.clear.objects.Test;

public class GetInformation {
	
	public static List<Test> getTests() {
		List<Test> tests = new ArrayList<Test>();
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(Settings.APP_PATH + "?lat=" + Homescreen.mService.getLat() + "&lon=" + Homescreen.mService.getLon());
			HttpResponse response = client.execute(get);
			JSONArray reply = new JSONArray(Utils.convertStreamToString(response.getEntity().getContent()));
			for(int i = 0; i < reply.length(); i++) {
				tests.add(new Test(reply.getJSONObject(i)));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return tests;
	}

}

package edu.vanderbilt.clear.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

public class GetInformation {
	
	public static List<HashMap<String, Object>> getTests(int deviceId) {
		List<HashMap<String, Object>> tests = new ArrayList<HashMap<String, Object>>();
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(Settings.APP_PATH + "poll?id=" + deviceId + "&lat=" + Homescreen.mService.getLat() + "&lon=" + Homescreen.mService.getLon());
			HttpResponse response = client.execute(get);
			JSONArray reply = new JSONArray(Utils.convertStreamToString(response.getEntity().getContent()));
			for(int i = 0; i < reply.length(); i++) {
				JSONArray test = reply.getJSONArray(i);
				JSONArray keys = test.getJSONObject(1).names();
				HashMap<String, Object> testMap = new HashMap<String, Object>();
				for(int j = 0; j < keys.length(); j++) {
					String key = keys.getString(j);
					Object value = test.getJSONObject(1).get(key);
					if (value instanceof JSONArray) {
						List<Object> listValue = new ArrayList<Object>();
						for (int k = 0; k < ((JSONArray) value).length(); k++) {
							listValue.add(((JSONArray) value).get(k));
						}
						testMap.put(key, listValue);
					} else {
						testMap.put(key, value);
					}
					
				}
				testMap.put("id", test.getInt(0));
				tests.add(testMap);
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

package edu.vanderbilt.clear.network;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import edu.vanderbilt.clear.Settings;
import edu.vanderbilt.clear.Utils;

public class SendData {
	
	public static String packageData(HashMap<String, Object> data) {
		JSONObject contents = new JSONObject();
		Iterator<Entry<String, Object>> things = data.entrySet().iterator();
		while(things.hasNext()) {
			Entry<String, Object> entry = things.next();
			try {
				contents.put(entry.getKey(), entry.getValue());
			} catch (JSONException e) {
				// do nothing
				// TODO some kind of error handler?
			}
		}
		return contents.toString();
	}
	
	public static int sendData(String data) {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(Settings.APP_PATH + "data");
			StringEntity se = new StringEntity(data);
			post.setEntity(se);
			HttpResponse response = client.execute(post);
			JSONObject reply = new JSONObject(Utils.convertStreamToString(response.getEntity().getContent()));
			return reply.getInt("id");
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;		
	}

}

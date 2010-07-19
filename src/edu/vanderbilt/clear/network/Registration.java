package edu.vanderbilt.clear.network;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import edu.vanderbilt.clear.Settings;
import edu.vanderbilt.clear.Utils;
import edu.vanderbilt.clear.objects.Device;
import edu.vanderbilt.clear.objects.Sensor;

public class Registration {
	
	/**
	 * Registers a sensor via HTTP Post to the server.
	 * @param sensor
	 * @return id of the sensor or -1 if an exception occured.
	 */
	public static int registerSensor(Sensor sensor) {
		int id = -1;
		try {
			// Use HttpClient to post to the server
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(Settings.APP_PATH + "sensor");
			StringEntity request = new StringEntity(sensor.toString());
			Log.v("Registrar", "JSON for /sensor post: " + sensor.toString());
			post.setEntity(request);
			
			// Execute the post and process the JSON response
			HttpResponse response = client.execute(post);
			id = new JSONObject(Utils.convertStreamToString(response.getEntity().getContent())).getInt("id");
		} catch(UnsupportedEncodingException e) {
			Log.e("Registrar", "This should never occur since we are using UTF-8 encoding everywhere.", e);
		} catch (ClientProtocolException e) {
			Log.e("Registrar", "The execution of the HTTP Post failed.", e);
		} catch (IOException e) {
			Log.e("Registrar", "The response stream could not be created.", e);
		} catch (JSONException e) {
			Log.e("Registrar", "The server returned invalid JSON.", e);
			// TODO : FLAG ERROR HERE
		}
		Log.v("Registrar", "ID for /sensor response: " + id);
		return id;
	}
	
	public static int registerDevice(Device device) {
		int id = -1;
		try {
			// Use HttpClient to post to the server
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(Settings.APP_PATH + "device");
			StringEntity se = new StringEntity(device.toString());
			post.setEntity(se);
			
			// Execute the post and process the JSON response
			HttpResponse response = client.execute(post);
			id = new JSONObject(Utils.convertStreamToString(response.getEntity().getContent())).getInt("id");
		} catch (UnsupportedEncodingException e) {
			Log.e("Registrar", "This should never occur since we are using UTF-8 encoding everywhere.", e);
		} catch (ClientProtocolException e) {
			Log.e("Registrar", "The execution of the HTTP Post failed.", e);
		} catch (IOException e) {
			Log.e("Registrar", "The response stream could not be created.", e);
		} catch (JSONException e) {
			Log.e("Registrar", "The server returned invalid JSON.", e);
			// TODO : FLAG ERROR HERE
		}
		Log.v("Registrar", "ID for /device response: " + id);
		return id;
	}

}

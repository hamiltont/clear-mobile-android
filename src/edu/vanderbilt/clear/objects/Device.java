package edu.vanderbilt.clear.objects;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONStringer;

import android.util.Log;

public class Device {
	private List<Integer> sensors;
	
	public Device(List<Integer> sensors) {
		this.sensors = sensors;
	}
	
	@Override
	public String toString() {
		JSONStringer temp = new JSONStringer();
		try {
			temp.object();
			temp.key("sensors").value(new JSONArray(sensors));
			temp.endObject();
		} catch (JSONException e) {
			Log.e(getClass().getName(), "This should never happen.", e);
		}
		return temp.toString();
	}

	public void setSensors(List<Integer> sensors) {
		this.sensors = sensors;
	}

	public List<Integer> getSensors() {
		return sensors;
	}
}

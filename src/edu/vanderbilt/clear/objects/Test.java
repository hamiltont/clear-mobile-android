package edu.vanderbilt.clear.objects;

import java.io.Serializable;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Test implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7598483064041252037L;
	private String name;
	private String details;
	private double lat;
	private double lon;
	private int radius;
	private List<Request> requests;
	
	public class Request implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -2013569723456871512L;

		public Request(JSONObject json) throws JSONException{
			name = json.getString("name");
			details = json.getString("details");
			type = json.getString("type");
		}
		private String name;
		private String details;
		private String type;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDetails() {
			return details;
		}
		public void setDetails(String details) {
			this.details = details;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
	}

	public Test(JSONObject json) throws JSONException{
		this.name = json.getString("name");
		this.details = json.getString("details");
		this.lat = json.getDouble("lat");
		this.lon = json.getDouble("lon");
		this.radius = json.getInt("radius");
		JSONArray reqs = json.getJSONArray("requests");
		for (int i = 0; i < reqs.length(); i++) {
			requests.add(new Request(reqs.getJSONObject(i)));
		}
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public void addRequest(Request request) {
		requests.add(request);
	}
	
	public void clearRequests() {
		requests.clear();
	}
	
	public List<Request> getRequests() {
		return requests;
	}

}

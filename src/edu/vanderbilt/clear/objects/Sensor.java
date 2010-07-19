package edu.vanderbilt.clear.objects;

import org.json.JSONException;
import org.json.JSONStringer;

import android.util.Log;


public class Sensor {
	
	private int id;
	private String name;
	private String type;
	private String vendor;
	private int version;
	
	public Sensor(String name, int type, String vendor, int version) {
		this.setName(name);
		this.setVendor(vendor);
		this.setVersion(version);
		switch(type) {
		case 1:
			setType("accelerometer");
			break;
		case 2:
			setType("magnetic field");
			break;
		case 3:
			setType("orientation");
			break;
		case 4:
			setType("gyroscope");
			break;
		case 5:
			setType("light");
			break;
		case 6:
			setType("pressure");
			break;
		case 7:
			setType("temperature");
			break;
		case 8:
			setType("proximity");
			break;
		default:
			setType("unknown");
		}		
	}
	
	public Sensor(String name, String type, String vendor, int version) {
		this.setName(name);
		this.setVendor(vendor);
		this.setVersion(version);
		this.setType(type);		
	}
	
	@Override
	public String toString() {
		JSONStringer temp = new JSONStringer();
		try {
			temp.object();
			temp.key("name").value(name);
			temp.key("type").value(type);
			temp.key("vendor").value(vendor);
			temp.key("version").value(version);
			temp.endObject();
		} catch (JSONException e) {
			Log.e(getClass().getName(), "This should never happen.", e);
		}
		return temp.toString();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	

}

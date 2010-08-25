package edu.vanderbilt.clear;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import edu.vanderbilt.clear.network.Registration;
import edu.vanderbilt.clear.objects.Device;

/**
 * This is the first activity of the program.
 * @author Zach McCormick
 *
 */
public class Homescreen extends Activity {
	
	
	Button btnRegister;
	Button btnTests;
	SharedPreferences sp;
	SharedPreferences.Editor spEdit;
	Context myContext = this;
	public static ILocationService mService;
	ServiceConnection mConnection;
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);
        
        // assign variables
        sp = this.getSharedPreferences("settings", 0);
        spEdit = sp.edit();
        btnRegister = (Button) findViewById(R.id.homescreen_register);
        btnTests = (Button) findViewById(R.id.homescreen_test);
        
        // enable/disable buttons depending on registration
        if (sp.contains("registered")) {
        	btnRegister.setEnabled(false);
        	btnTests.setEnabled(true);
        	Log.v("HomeScreen", String.valueOf(sp.getInt("registered", 0)));
        } else {
        	btnRegister.setEnabled(true);
        	btnTests.setEnabled(false);
        }
        
        // set on click listeners
        btnRegister.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new RegisterDevice().execute();
			}
        });
        btnTests.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(myContext, TestList.class);
				myContext.startActivity(i);
			}
        });
        
        // establish service connection for location service
        mConnection = new ServiceConnection() {
            public void onServiceConnected(ComponentName className,
                    IBinder service) {
                mService = ILocationService.Stub.asInterface(service);
            }
            public void onServiceDisconnected(ComponentName className) {
                mService = null;
            }

        };
        
        // start location service
        Intent intentService = new Intent(myContext, LocationService.class);
        intentService.addFlags(Context.BIND_AUTO_CREATE);
        bindService(intentService, mConnection, Context.BIND_AUTO_CREATE);
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	// end location service
    	unbindService(mConnection);
    }
    
    class RegisterDevice extends AsyncTask<Void, Void, Void> {
    	ProgressDialog dlg;
    	@Override
    	protected void onPreExecute () {
			dlg = new ProgressDialog(myContext);
			dlg.setCancelable(false);
			dlg.setMessage("Registering your device...\nThis may take a few moments.");
			dlg.setTitle("CLEAR");
			dlg.setIndeterminate(true);
			dlg.show();
    	}
    	
    	@Override
    	protected void onPostExecute(Void results) {
    		dlg.dismiss();
    		btnTests.setEnabled(true);
    		btnRegister.setEnabled(false);
    	}
    	
		@Override
		protected Void doInBackground(Void... params) {
			spEdit.putInt("registered", Registration.registerDevice(new Device(registerSensors())));
			spEdit.commit();
			return null;
		}
		
		/**
		 * Registers device sensors with server including text, camera, and audio.
		 * @return list of sensor IDs
		 */
		private List<Integer> registerSensors() {
			SensorManager sm = (SensorManager) myContext.getSystemService(SENSOR_SERVICE);
			List<Sensor> sensorList = sm.getSensorList(Sensor.TYPE_ALL);
			List<Integer> sensors = new ArrayList<Integer>();
			for (int i = 0; i < sensorList.size(); i++) {
				Sensor s = sensorList.get(i);
				edu.vanderbilt.clear.objects.Sensor currentSensor = new edu.vanderbilt.clear.objects.Sensor(s.getName(), s.getType(), s.getVendor(), s.getVersion());
				sensors.add(Registration.registerSensor(currentSensor));
			}
			// add text 'sensor'
			sensors.add(Registration.registerSensor(new edu.vanderbilt.clear.objects.Sensor("Text", "text", "none", 1)));
			// add camera 'sensor'
			sensors.add(Registration.registerSensor(new edu.vanderbilt.clear.objects.Sensor("Camera", "camera", "none", 1)));
			// add audio 'sensor'
			sensors.add(Registration.registerSensor(new edu.vanderbilt.clear.objects.Sensor("Audio", "audio", "none", 1)));
			return sensors;
		}
    	
    }
}
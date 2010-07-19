package edu.vanderbilt.clear;

import java.util.HashMap;
import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import edu.vanderbilt.clear.network.GetInformation;

/**
 * List of possible tests, gotten from a server poll.
 * @author Zach McCormick
 *
 */
public class TestList extends ListActivity {
	SharedPreferences sp;
	List<HashMap<String, Object>> tests;
	Context myContext = this;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("settings", 0);
        // asynchronously download the tests
		new DownloadTests().execute();
    }
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    	Intent i = new Intent(myContext, TestDetails.class);
    	i.putExtra("data", tests.get(position));
    	startActivity(i);

    }
    
    class DownloadTests extends AsyncTask<Void, Void, Void> {
    	ProgressDialog dlg;
    	@Override
    	protected void onPreExecute () {
			dlg = new ProgressDialog(myContext);
			dlg.setCancelable(false);
			dlg.setMessage("Downloading Available Tests...\nThis may take a few moments.");
			dlg.setTitle("CLEAR");
			dlg.setIndeterminate(true);
			dlg.show();
    	}
    	
    	@Override
    	protected void onPostExecute(Void results) {
    		setListAdapter(new SimpleAdapter(myContext, tests, android.R.layout.simple_list_item_1,new String[]{"name"}, new int[]{android.R.id.text1}));
    		dlg.dismiss();
    	}
    	
		@Override
		protected Void doInBackground(Void... params) {
			tests = GetInformation.getTests(sp.getInt("registered", 0));
			return null;
		}
		
    	
    }
    
}

package edu.vanderbilt.clear;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import edu.vanderbilt.clear.objects.Test;

/**
 * List of possible tests, gotten from a server poll.
 * 
 * @author Zach McCormick
 * 
 */
public class TestList extends ListActivity {
	SharedPreferences sp;
	List<Test> tests;
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
		protected void onPreExecute() {
			dlg = new ProgressDialog(myContext);
			dlg.setCancelable(false);
			dlg
					.setMessage("Downloading Available Tests...\nThis may take a few moments.");
			dlg.setTitle("CLEAR");
			dlg.setIndeterminate(true);
			dlg.show();
		}

		@Override
		protected void onPostExecute(Void results) {

			List<Map<String, String>> rows = new ArrayList<Map<String, String>>();
			for (Test t : tests) {
				Map<String, String> m = new HashMap<String, String>(1);
				m.put("name", t.getName());
			}

			setListAdapter(new SimpleAdapter(myContext, rows,
					android.R.layout.simple_list_item_1,
					new String[] { "name" }, new int[] { android.R.id.text1 }));
			dlg.dismiss();
		}

		@Override
		protected Void doInBackground(Void... params) {
			tests = GetInformation.getTests();
			return null;
		}

	}

}

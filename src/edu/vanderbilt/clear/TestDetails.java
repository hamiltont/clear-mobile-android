package edu.vanderbilt.clear;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.vanderbilt.clear.database.DatabaseWriter;
import edu.vanderbilt.clear.network.SendData;

/**
 * This is the activity that shows a single test and allows you to answer questions.  It still needs work.
 * @author Zach McCormick
 *
 */
public class TestDetails extends Activity {
	TextView tvName;
	TextView tvDetails;
	LinearLayout ll;
	Context myContext = this;
	SharedPreferences sp;

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        
        // assign variables
        tvName = (TextView) findViewById(R.id.details_name);
        tvDetails = (TextView) findViewById(R.id.details_details);
        ll = (LinearLayout) findViewById(R.id.details_layout);
        sp = myContext.getSharedPreferences("settings", 0);
        
        // get the hashmap passed from the test list (this might need fixed later because it may get a non-serializable object inside)
        final HashMap<String, Object> test = (HashMap<String, Object>) this.getIntent().getSerializableExtra("data");
        
        // TODO: add sensor buttons
        
        // assign the layout pieces
		tvName.setText((CharSequence) test.get("name"));
		tvDetails.setText((CharSequence) test.get("details"));
		List<String> questions = (List<String>) test.get("questions");
		for (int i = 0; i < questions.size(); i++) {
			final String question = questions.get(i);
			Button item = new Button(myContext);
			item.setText(question);
			item.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					new QuestionDialog(myContext, question, (Integer) test.get("id")).show();
				}
			});
			ll.addView(item);
		}
	}
	/**
	 * Dialog to answer a text question.  TODO: add to queue instead of directly sending it
	 * @author Zach McCormick
	 *
	 */
	class QuestionDialog extends Dialog {
		Button btnCancel;
		Button btnSubmit;
		TextView tvQuestion;
		EditText etAnswer;
		DatabaseWriter dbw;

		public QuestionDialog(final Context context, final String question, final int id) {
			super(context);
			final QuestionDialog dlg = this;
			setContentView(R.layout.questiondialog);
			btnCancel = (Button) findViewById(R.id.questiondialog_cancel);
			btnSubmit = (Button) findViewById(R.id.questiondialog_submit);
			tvQuestion = (TextView) findViewById(R.id.questiondialog_question);
			etAnswer = (EditText) findViewById(R.id.questiondialog_answer);
			this.setTitle("Answer the Question");
			btnCancel.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					QuestionDialog.this.cancel();
				}
			});
			btnSubmit.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {		
					HashMap<String, Object> data = new HashMap<String, Object>();
					data.put("test_id", id);
					data.put("device_id", sp.getInt("registered", 0));
					data.put("sensor", 1);
					data.put("question", question);
					data.put("answer", etAnswer.getText().toString());
					data.put("time", System.currentTimeMillis());
					try {
						data.put("lat", Homescreen.mService.getLat());
						data.put("lon", Homescreen.mService.getLon());
					} catch (RemoteException e) {
						data.put("error", "location unknown");
					}
					dbw = new DatabaseWriter(context);
					dbw.open();
					dbw.addData(SendData.packageData(data));
					dbw.close();
					startService(new Intent(context, edu.vanderbilt.clear.DataService.class));
					dlg.dismiss();
				}
			});
			tvQuestion.setText(question);
		}
		
	}
}

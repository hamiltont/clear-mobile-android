package edu.vanderbilt.clear.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * This is another database utility class used to write data to the database.  Most functions require passing a hashmap of data where key = column name.
 * 
 * Most functions are self-explanatory.
 * @author Zach McCormick
 *
 */
public class DatabaseWriter {
	Context myCtx; // application context
	DatabaseAccessor databaseAccessor; // helper class
	SQLiteDatabase db; // database

	public DatabaseWriter(Context context) {
		myCtx = context;
		databaseAccessor = new DatabaseAccessor(myCtx);
	}

	/**
	 * This method opens the database.
	 * @return true if successful
	 */
	public boolean open() {
		boolean result = databaseAccessor.open();
		db = databaseAccessor.getDatabase();
		if (db != null) {
			return result;
		} else {
			return false;
		}
	}
	
	public void close() {
		databaseAccessor.close();
	}
	
	public long addData(String data) {
		ContentValues cv = new ContentValues();
		cv.put("data", data);
		return db.insert("data", null, cv);
	}
	
	public long clearData() {
		return db.delete("data", null, null);
	}
	
	public int deleteData(long id) {
		return db.delete("data", "_id=" + id, null);
	}
}

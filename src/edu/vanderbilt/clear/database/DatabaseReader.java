package edu.vanderbilt.clear.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * This class is a utility class to read different types of data from the database.   Most functions are self-explanatory.
 * @author Zach McCormick
 *
 */
public class DatabaseReader {
	Context myContext; // application context
	DatabaseAccessor databaseAccessor; // our helper class
	SQLiteDatabase db; // the actual database

	public DatabaseReader(Context context) {
		myContext = context;
		databaseAccessor = new DatabaseAccessor(myContext);
	}

	/**
	 * This method opens the database using a DatabaseAccessor
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

	/**
	 * This method closes the database.  You MUST do this to avoid memory leaks!
	 */
	public void close() {
		databaseAccessor.close();
	}
	
	public Cursor getData() {
		return db.rawQuery("SELECT * FROM data", null);
	}
	
}

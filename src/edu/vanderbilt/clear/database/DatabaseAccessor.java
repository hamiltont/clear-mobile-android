package edu.vanderbilt.clear.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This is a helper class used in other database classes to connect to the database.
 * @author Zach McCormick
 *
 */
public class DatabaseAccessor {

	/**
	 * When DatabaseAccessor is instantiated, this returns the current database.  This can be null if the database hasn't been opened.
	 * @return Current database
	 */
	public SQLiteDatabase getDatabase() {
		return myDatabase;
	}

	private DatabaseHelper myDatabaseHelper;
	private SQLiteDatabase myDatabase;
	private final Context myContext;

	private final static int DATABASE_VERSION = 1; // This is the database version.  When changing it, it will destroy all tables and rebuild them.  Value is irrelevant - just increment it when you make database schema changes.

	/**
	 * Simple constructor.
	 * @param ctx - the application context
	 */
	public DatabaseAccessor(Context ctx) {
		this.myContext = ctx;
		myDatabaseHelper = new DatabaseHelper(myContext);
	}

	/**
	 * By overriding finalize, we can close the database before the reference to it is destroyed. This prevents memory leaks.
	 */
	@Override
	protected void finalize() throws Throwable {
		this.close();
		super.finalize();
	}

	/**
	 * This method must be called to do anything to the database.
	 * @return true if database opened successfully
	 */
	public boolean open() {
		try {
			myDatabase = myDatabaseHelper.getWritableDatabase();
			return true;
		} catch (SQLiteException e) {
			Log.w("DatabaseAccessor", "Opening the database failed");
			return false;
		}
	}

	/**
	 * This method must be called when done with the database to prevent memory leaking.
	 */
	public void close() {
		myDatabase.close();
		myDatabaseHelper.close();		
	}
	
	/**
	 * This is a static database helper method that defines application specific parts of the database.
	 * 
	 * In this case, it defines the table schema and the tables dropped on generation of a new database.
	 * @author Zach McCormick
	 *
	 */
	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, "data", null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			db
					.execSQL("CREATE TABLE data( _id integer PRIMARY KEY, data text)");
			db
					.execSQL("CREATE TABLE tests( _id integer PRIMARY KEY, name text, details text, questions integer)");
			db
					.execSQL("CREATE TABLE questions( _id integer PRIMARY KEY, data text)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w("DatabaseAccessor", "Upgrading database from version "
					+ oldVersion + " to " + newVersion
					+ ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + "data");
			db.execSQL("DROP TABLE IF EXISTS " + "tests");
			db.execSQL("DROP TABLE IF EXISTS " + "questions");
			onCreate(db);
		}
	}
}

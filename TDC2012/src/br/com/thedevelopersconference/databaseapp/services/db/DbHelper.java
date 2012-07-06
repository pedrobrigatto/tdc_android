package br.com.thedevelopersconference.databaseapp.services.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.thedevelopersconference.databaseapp.R;

/**
 * Used to manage the database instance and provide a reference to it when needed.
 * 
 * @author pedrobrigatto
 */
public class DbHelper extends SQLiteOpenHelper {
	
	public static final byte DB_READABLE_MODE = 0;
	public static final byte DB_WRITEABLE_MODE = 1;
	
	private Context context;
	
	public DbHelper(Context context) {
		super(context, context.getResources().getString(R.string.tdc_database), 
				null, Integer.parseInt(context.getResources().
						getString(R.string.tdc_database_version)));
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(context.getResources().getString(R.string.professional_create));
		db.execSQL(context.getResources().getString(R.string.speech_create));
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// It would be the time to do the backup before deleting. Since it is 
		// a demonstration, just drop the tables and recreate them.
		db.execSQL(context.getResources().getString(R.string.speech_drop));
		db.execSQL(context.getResources().getString(R.string.professional_drop));
		onCreate(db);
	}
	
	public SQLiteDatabase getDatabase(byte mode) {
		
		SQLiteDatabase db = null;
		
		switch (mode) {
		case DB_READABLE_MODE:
			db = this.getReadableDatabase();
		case DB_WRITEABLE_MODE:
			db = this.getWritableDatabase();
		} 
		return db;
	}
}

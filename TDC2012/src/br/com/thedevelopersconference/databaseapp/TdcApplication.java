package br.com.thedevelopersconference.databaseapp;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import br.com.thedevelopersconference.databaseapp.services.db.DbHelper;

public class TdcApplication extends Application {
	
	private DbHelper dbHelper;
	
	@Override
	public void onCreate() {
		super.onCreate();
		//teste
		Resources res = getResources();
		
		// As soon as the app is created, a key is stored in the preferences file,
		// so it can be checked when users wants to add a speech through a mobile.
		SharedPreferences preferences = getSharedPreferences(
				res.getString(R.string.tdc_app_preferences), MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString(res.getString(R.string.tdc_preferences_secret_key_name),
				res.getString(R.string.tdc_preferences_secret_key));
		
		editor.commit();
		
		editor = null;
		preferences = null;
		res = null;
	}

	public DbHelper getDatabaseHelper() {
		if (dbHelper == null) {
			dbHelper = new DbHelper(getApplicationContext());
		}
		return dbHelper;
	}
}

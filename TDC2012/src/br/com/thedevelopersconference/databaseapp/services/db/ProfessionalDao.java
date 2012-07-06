package br.com.thedevelopersconference.databaseapp.services.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.thedevelopersconference.databaseapp.R;
import br.com.thedevelopersconference.databaseapp.TdcApplication;
import br.com.thedevelopersconference.databaseapp.domain.Professional;

/**
 * Handles data associated with professionals.
 * 
 * @author pedrobrigatto
 */
public class ProfessionalDao {

	private Context context;

	public ProfessionalDao(Context context) {
		this.context = context;
	}

	public boolean save (Professional professional) {
		boolean saved = false;

		TdcApplication app = (TdcApplication) context;
		SQLiteDatabase db = app.getDatabaseHelper().getWritableDatabase();
		Resources res = app.getResources();

		ContentValues values = new ContentValues();
		values.put(res.getString(R.string.professional_field_name), professional.getName());
		values.put(res.getString(R.string.professional_field_role), professional.getRole());
		
		if (db.insert(res.getString(R.string.professional_table), 
				null, values) > 0) {
			saved = true;
		}
		
		db.close();

		db = null;
		res = null;
		app = null;

		return saved;
	}

	public boolean update (Professional professional) {
		boolean updated = false;

		return updated;
	}

	public List<Professional> listAll() {
		List<Professional> professionals = new ArrayList<Professional>();
		
		TdcApplication app = (TdcApplication) context;
		SQLiteDatabase db = app.getDatabaseHelper().getWritableDatabase();
		Resources res = app.getResources();
		
		Cursor cursor = db.query(res.getString(R.string.professional_table), 
				null, null, null, null, null, null);
		
		Professional current = null;
		
		while (cursor.moveToNext()) {
			current = new Professional(cursor.getString(0));
			current.setRole(cursor.getInt(1));
			professionals.add(current);
		}
		
		cursor.close();
		db.close();
		cursor = null;
		db = null;
		res = null;
		app = null;

		return professionals;
	}

	public Professional findById (String id) {
		Professional professional = null;
		
		TdcApplication app = (TdcApplication) context;
		SQLiteDatabase db = app.getDatabaseHelper().getReadableDatabase();
		Resources res = app.getResources();
		
		Cursor cursor = db.query(res.getString(R.string.professional_table), 
				null, res.getString(R.string.professional_field_name) + "=?", 
				new String[]{id}, null, null, null);
		
		if (cursor.moveToNext()) {
			professional = new Professional(cursor.getString(0));
			professional.setRole(cursor.getInt(1));
		}
		
		cursor.close();
		db.close();
		cursor = null;
		db = null;
		res = null;
		app = null;

		return professional;
	}
}

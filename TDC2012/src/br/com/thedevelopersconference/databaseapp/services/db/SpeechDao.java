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
import br.com.thedevelopersconference.databaseapp.domain.Speech;

/**
 * Handles data associated with speeches.
 * 
 * @author pedrobrigatto
 */
public class SpeechDao {
	
	private Context context;
	
	public SpeechDao(Context context) {
		this.context = context;
	}
	
	public boolean save (Speech speech) {
		boolean saved = false;
		
		TdcApplication app = (TdcApplication) context;
		SQLiteDatabase db = app.getDatabaseHelper().getWritableDatabase();
		Resources res = app.getResources();
		
		ContentValues values = new ContentValues();
		values.put(res.getString(R.string.speech_field_title), speech.getTitle());
		values.put(res.getString(R.string.speech_field_description), speech.getDescription());
		values.put(res.getString(R.string.speech_field_professional_name), speech.getAuthor().getName());
		values.put(res.getString(R.string.speech_field_speech_date), speech.getWhen());
		
		if (db.insert(res.getString(R.string.speech_table), null, values) > 0) {
			saved = true;
		}
		
		db.close();
		
		db = null;
		res = null;
		app = null;
		
		return saved;
	}
	
	public List<Speech> listAll() {
		List<Speech> all = new ArrayList<Speech>();
		
		TdcApplication app = (TdcApplication) context;
		SQLiteDatabase db = app.getDatabaseHelper().getReadableDatabase();
		Resources res = app.getResources();
		
		Cursor cursor = db.query(res.getString(R.string.speech_table), 
				null, null, null, null, null, null);
		
		Speech current = null;
		
		while (cursor.moveToNext()) {
			current = new Speech();
			current.setTitle(cursor.getString(0));
			current.setDescription(cursor.getString(1));
			current.setAuthor(new Professional(cursor.getString(2)));
			current.setWhen(cursor.getString(3));
			all.add(current);
		}
		
		cursor.close();
		db.close();
		cursor = null;
		db = null;
		res = null;
		app = null;
		
		return all;
	}
	
	public List<Speech> listByProfessional(String name) {
		List<Speech> byProfessional = new ArrayList<Speech>();
		
		TdcApplication app = (TdcApplication) context;
		SQLiteDatabase db = app.getDatabaseHelper().getReadableDatabase();
		Resources res = app.getResources();
		
		Cursor cursor = db.query(res.getString(R.string.speech_table), null, 
				res.getString(R.string.speech_field_professional_name) + "=?", 
				new String[] {name}, null, null, null);
		
		Speech current = null;
		
		while (cursor.moveToNext()) {
			current = new Speech();
			current.setTitle(cursor.getString(0));
			current.setDescription(cursor.getString(1));
			current.setAuthor(new Professional(cursor.getString(2)));
			current.setWhen(cursor.getString(3));
			byProfessional.add(current);
		}
		
		cursor.close();
		db.close();
		cursor = null;
		db = null;
		res = null;
		app = null;
		
		return byProfessional;
	}
	
	public Speech findByTitle (String title) {
		Speech speech = null;
		
		TdcApplication app = (TdcApplication) context;
		SQLiteDatabase db = app.getDatabaseHelper().getReadableDatabase();
		Resources res = app.getResources();
		
		Cursor cursor = db.query(res.getString(R.string.speech_table), null, 
				res.getString(R.string.speech_field_title) + "=?", 
				new String[] {title}, null, null, null);
		
		if (cursor.moveToNext()) {
			speech = new Speech();
			speech.setTitle(cursor.getString(0));
			speech.setDescription(cursor.getString(1));
			speech.setAuthor(new Professional(cursor.getString(2)));
			speech.setWhen(cursor.getString(3));
		}
		
		cursor.close();
		db.close();
		cursor = null;
		db = null;
		res = null;
		app = null;
		
		return speech;
	}

	public List<Speech> findAll() {
		
		Speech speech = null;
		List<Speech> all = new ArrayList<Speech>();
		
		TdcApplication app = (TdcApplication) context;
		SQLiteDatabase db = app.getDatabaseHelper().getReadableDatabase();
		Resources res = app.getResources();
		
		Cursor cursor = db.query(res.getString(R.string.speech_table), null, 
				null, null, null, null, null);
		
		while (cursor.moveToNext()) {
			speech = new Speech();
			speech.setTitle(cursor.getString(0));
			speech.setDescription(cursor.getString(1));
			speech.setAuthor(new Professional(cursor.getString(2)));
			speech.setWhen(cursor.getString(3));
			all.add(speech);
		}
		
		cursor.close();
		cursor = null;
		return all;
	}
}

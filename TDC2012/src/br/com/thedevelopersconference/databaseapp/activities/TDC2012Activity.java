package br.com.thedevelopersconference.databaseapp.activities;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import br.com.thedevelopersconference.databaseapp.R;
import br.com.thedevelopersconference.databaseapp.activities.adapters.SpeechListAdapter;
import br.com.thedevelopersconference.databaseapp.domain.Speech;
import br.com.thedevelopersconference.databaseapp.services.db.SpeechDao;
import br.com.thedevelopersconference.databaseapp.services.db.Util;

/**
 * Main view. Displays the list of speeches and some actions that drive the user
 * across the other features.
 * 
 * @author pedrobrigatto
 */
public class TDC2012Activity extends Activity {

	private static byte ADDING_SPEECH = 0;
	private static byte ADDING_PROFESSIONAL = 1;
	private static byte LISTING_PROFESSIONALS = 2;

	private ListView speeches;
	private EditText textSecretKey;
	private Button actionEnterSecretKey;
	private Button actionListProfessionals;
	private Button actionAddProfessional;
	private Button actionAddSpeech;

	private byte comingFrom = -1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tdc_main);

		textSecretKey = (EditText) findViewById(R.id.tdc_main_secret_key);

		speeches = (ListView) findViewById(R.id.tdc_home_all_speeches);

		SpeechDao dao = new SpeechDao(getApplicationContext());
		speeches.setAdapter(new SpeechListAdapter(getBaseContext(), dao.findAll()));

		speeches.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, 
					View view, int position, long id) {
				Speech speech = (Speech) speeches.getItemAtPosition(position);
				Intent intent = new Intent(getBaseContext(), SpeechInfoView.class);
				intent.putExtra("speech", speech);
				startActivity(intent);
			}
		});

		actionEnterSecretKey = (Button) findViewById(R.id.tdc_main_action_checksecret);
		actionEnterSecretKey.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				SharedPreferences preferences = getSharedPreferences(
						getResources().getString(R.string.tdc_app_preferences), MODE_PRIVATE);

				String secret = preferences.getString(getResources().getString(
						R.string.tdc_preferences_secret_key_name), "no_key");

				String userInput = textSecretKey.getText().toString();

				if (!"no_key".equals(secret) && null != userInput && 
						secret.equals(userInput)) {
					actionAddProfessional.setVisibility(View.VISIBLE);
					actionAddSpeech.setVisibility(View.VISIBLE);
					
					// Hiding the secret key input components, they are not 
					// necessary anymore.
					LinearLayout secretArea = (LinearLayout) findViewById(R.id.tdc_main_input_secret_area);
					secretArea.setVisibility(View.GONE);
					
					textSecretKey.setText("");
				} else {
					Toast.makeText(getBaseContext(), 
							getBaseContext().getResources().getString(
									R.string.tdc_main_message_wrong_key), 
									Toast.LENGTH_LONG).show();
					textSecretKey.setText("");
				}
			}
		});

		actionListProfessionals = (Button) findViewById(R.id.tdc_home_listProfessional);
		actionListProfessionals.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				comingFrom = LISTING_PROFESSIONALS;
				startActivity(new Intent(getBaseContext(), AllProfessionalsView.class));
			}
		});

		actionAddProfessional = (Button) findViewById(R.id.tdc_home_addProfessional);
		actionAddProfessional.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				comingFrom = ADDING_PROFESSIONAL;
				startActivity(new Intent(getBaseContext(), AddProfessionalView.class));
			}
		});

		actionAddSpeech = (Button) findViewById(R.id.tdc_home_addSpeech);
		actionAddSpeech.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				comingFrom = ADDING_SPEECH;
				startActivity(new Intent(getBaseContext(), AddSpeechView.class));
			}
		});

		checkExternalFileApi();
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (comingFrom == ADDING_SPEECH) {  // update the list view.
			List<Speech> newData = new SpeechDao(getApplicationContext()).listAll();
			SpeechListAdapter adapter = (SpeechListAdapter) speeches.getAdapter();
			adapter.updateModel(newData);
		}
	}

	/**
	 * This is a method just to show some of the things we can do with the API 
	 * to handle external files. Here, it is plain Java I/O API and Linux-like
	 * file path notation, nothing different from it.
	 */
	private void checkExternalFileApi () {

		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			// We can read and write the media
			Log.i(Util.LOG_TAG, "===============> Nice, we can read and write in the media.");
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			// We can only read the media
			Log.i(Util.LOG_TAG, "===============> Hummmm, OK. We can only read this media.");
		} else if (Environment.MEDIA_SHARED.equals(state)) {
			Log.i(Util.LOG_TAG, "===============> Alright baby, the media is present, " +
					"not mounted, and shared via USB mass storage.");
		} else {
			Log.i(Util.LOG_TAG, "===============> Simply do not know what to say about the media.");
		}

		File file = getExternalCacheDir();

		if (file != null) {
			Log.i(Util.LOG_TAG, "===============> External cache directory path is " 
					+ file.getAbsolutePath());
		}

		file = Environment.getExternalStorageDirectory();
		if (file != null) {
			Log.i(Util.LOG_TAG, "===============> External storage directory path is " 
					+ file.getAbsolutePath());
		}

		file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
		if (file != null) {
			Log.i(Util.LOG_TAG, "===============> External storage directory path is " 
					+ file.getAbsolutePath());
		}
	}
}
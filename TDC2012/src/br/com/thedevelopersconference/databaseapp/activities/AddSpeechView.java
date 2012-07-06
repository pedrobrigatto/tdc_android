package br.com.thedevelopersconference.databaseapp.activities;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import br.com.thedevelopersconference.databaseapp.R;
import br.com.thedevelopersconference.databaseapp.activities.adapters.ProfessionalListAdapter;
import br.com.thedevelopersconference.databaseapp.domain.Professional;
import br.com.thedevelopersconference.databaseapp.domain.Speech;
import br.com.thedevelopersconference.databaseapp.services.db.ProfessionalDao;
import br.com.thedevelopersconference.databaseapp.services.db.SpeechDao;

/**
 * Helps the user on adding a new speech to the agenda.
 * 
 * @author pedrobrigatto
 */
public class AddSpeechView extends Activity {

	private static final int DATE_PICKER_DIALOG = 0;

	private EditText title;
	private EditText description;
	private EditText when;
	private Spinner professional;
	private Button actionSave;
	private Button actionDiscard;
	
	private SpeechDateListener dateListener = new SpeechDateListener();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tdc_add_speech_layout);

		title = (EditText) findViewById(R.id.tdc_add_speech_title_text);
		description = (EditText) findViewById(R.id.tdc_add_speech_desc_text);

		when = (EditText) findViewById(R.id.tdc_add_speech_when_text);
		when.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					showDialog(DATE_PICKER_DIALOG);
				}
			}
		});

		professional = (Spinner) findViewById(R.id.tdc_add_speech_professionals);

		ProfessionalDao dao = new ProfessionalDao(getApplicationContext());
		professional.setAdapter(new ProfessionalListAdapter(getBaseContext(), dao.listAll()));

		actionSave = (Button) findViewById(R.id.tdc_add_speech_action_add);
		actionSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Speech speech = createSpeechFromFields();

				if (speech != null) {
					SpeechDao speechDao = new SpeechDao(getApplicationContext());
					if (speechDao.save(speech)) {
						Toast.makeText(getBaseContext(), 
								getBaseContext().getResources().getString(R.string.tdc_add_speech_success), 
								Toast.LENGTH_LONG).show();
						cleanScreen();
					} else {
						Toast.makeText(getBaseContext(), 
								getBaseContext().getResources().getString(R.string.tdc_add_speech_failure),
								Toast.LENGTH_LONG).show();
					}
				} else {  // fill in the blanks and try again
					Toast.makeText(getBaseContext(), 
							getBaseContext().getResources().getString(R.string.tdc_add_speech_blank_fields), 
							Toast.LENGTH_LONG).show();
				}
			}
		});

		actionDiscard = (Button) findViewById(R.id.tdc_add_speech_action_discard);
		actionDiscard.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private Speech createSpeechFromFields() {
		Speech speech = null;
		String empty = "";

		if (title.getText().toString() != null && !empty.equals(title.getText().toString()) &&
				description.getText().toString() != null && !empty.equals(description.getText().toString()) &&
				when.getText().toString() != null && !empty.equals(when.getText().toString())) {

			speech = new Speech();
			speech.setTitle(title.getText().toString());
			speech.setDescription(description.getText().toString());
			speech.setWhen(when.getText().toString());
			speech.setAuthor((Professional)professional.getSelectedItem());
		}
		empty = null;
		return speech;
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		final Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH);
		int year = calendar.get(Calendar.YEAR);
		return new DatePickerDialog(this, dateListener, year, month, day);
	}

	private void cleanScreen() {
		title.setText("");
		description.setText("");
		when.setText("");
	}
	
	/**
	 * Class used to set the results of the date selection in the date picker dialog.
	 * 
	 * @author pedrobrigatto
	 */
	private class SpeechDateListener implements OnDateSetListener {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			String empty = "";
			String bar = "/";
			when.setText(empty + dayOfMonth + bar + (monthOfYear + 1) + bar + year);
			empty = null;
			bar = null;
		}
	}
}

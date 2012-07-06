package br.com.thedevelopersconference.databaseapp.activities;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import br.com.thedevelopersconference.databaseapp.R;
import br.com.thedevelopersconference.databaseapp.domain.Speech;

/**
 * Displays the details of a speech and allows the user to take notes about the
 * content presented on it by the speecher.
 * 
 * @author pedrobrigatto
 */
public class SpeechInfoView extends Activity {

	private static final byte TDC_ADD_NOTE_DIALOG = 0;

	private TextView title;
	private TextView speecher;
	private TextView description;

	private Button actionAddNote;
	private Button actionSeeNotes;

	private Dialog dialog;
	private EditText dialogBody;

	private Speech speech;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tdc_speech_info_layout);

		speech = getIntent().getParcelableExtra("speech");

		title = (TextView) findViewById(R.id.tdc_speech_info_title);
		title.setText(speech.getTitle());

		speecher = (TextView) findViewById(R.id.tdc_speech_info_speecher);
		speecher.setText(speech.getAuthor().getName());

		description = (TextView) findViewById(R.id.tdc_speech_info_description);
		description.setText(speech.getDescription());

		actionAddNote = (Button) findViewById(R.id.tdc_speech_info_action_addnote);
		actionAddNote.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(TDC_ADD_NOTE_DIALOG);
			}
		});

		actionSeeNotes = (Button) findViewById(R.id.tdc_speech_info_action_list_notes);
		actionSeeNotes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getBaseContext(), NotesView.class));
			}
		});
	}

	@Override
	protected Dialog onCreateDialog (int dialogId) {
		dialog = new Dialog(this);
		dialog.setContentView(R.layout.tdc_dialog_addnote);

		dialogBody = (EditText) dialog.findViewById(R.id.tdc_dialog_add_note_body);

		Button dialogSave = (Button) dialog.findViewById(R.id.tdc_dialog_addnote_save);
		dialogSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (saveToNotesFile(dialogBody.getText().toString())) {
					Toast.makeText(getBaseContext(), 
							getResources().getString(R.string.tdc_dialog_add_note_saved), 
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(getBaseContext(), 
							getResources().getString(R.string.tdc_dialog_add_note_unsaved), 
							Toast.LENGTH_LONG).show();
				}
				dialog.dismiss();
			}
		});

		Button dialogDiscard = (Button) dialog.findViewById(R.id.tdc_dialog_addnote_discard);
		dialogDiscard.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		return dialog;
	}

	private boolean saveToNotesFile (String note) {

		boolean saved = false;
		FileOutputStream fos = null;
		DataOutputStream dos = null;

		try {
			
			fos = new FileOutputStream(new File(getFilesDir() + "/my_notes.txt"));
			dos = new DataOutputStream(fos);

			String line = "\n" + note;
			dos.write(line.getBytes());
			saved = true;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (dos != null) {
					dos.close();
					dos = null;
				}

				if (fos != null) {
					fos.close();
					fos = null;
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return saved;
	}
}

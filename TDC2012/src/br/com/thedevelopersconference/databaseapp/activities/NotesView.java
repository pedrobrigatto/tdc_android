package br.com.thedevelopersconference.databaseapp.activities;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.thedevelopersconference.databaseapp.R;
import br.com.thedevelopersconference.databaseapp.activities.adapters.MyNotesAdapter;

/**
 * Shows the notes added by a user about a specific speech.
 * 
 * @author pedrobrigatto
 */
public class NotesView extends Activity {
	
	private static final byte NOTES_INFO_DIALOG = 0;

	private ListView notes;
	private Dialog noteInfoDialog;
	private int selectedPosition;

	@Override
	protected void onCreate (Bundle savedInstance) {
		super.onCreate(savedInstance);
		setContentView(R.layout.tdc_notes_list_layout);

		notes = (ListView) findViewById(R.id.tdc_mynotes_listview);

		List<String> allNotes = getNotesFromFile();
		
		if (allNotes == null) {
			Toast.makeText(this, 
					getResources().getString(R.string.notes_file_unavailable), 
					Toast.LENGTH_LONG).show();
			allNotes = new ArrayList<String>();
		} else if (allNotes.size() == 0) {
			Toast.makeText(this, 
					getResources().getString(R.string.notes_file_unavailable), 
					Toast.LENGTH_LONG).show();
			allNotes = new ArrayList<String>();
		}
		
		notes.setAdapter(new MyNotesAdapter(this, allNotes));
		notes.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, 
					View view, int position, long id) {
				selectedPosition = position;
				showDialog(NOTES_INFO_DIALOG);
			}
		});
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		noteInfoDialog = new Dialog(this);
		noteInfoDialog.setContentView(R.layout.tdc_dialog_note_details);

		TextView body = (TextView) noteInfoDialog.findViewById(R.id.tdc_dialog_noteinfo_body);
		Button dialogOk = (Button) noteInfoDialog.findViewById(R.id.tdc_dialog_noteinfo_ok);
		body.setText(notes.getItemAtPosition(selectedPosition).toString());
		dialogOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				noteInfoDialog.dismiss();
			}
		});
		return noteInfoDialog;
	}

	private List<String> getNotesFromFile() {
		List<String> theNotes = null;
		
		File file = null;
		FileInputStream fis = null;
		DataInputStream dis = null;
		BufferedReader br = null;
		
		try {
//			file = new File(getResources().getString(R.string.tdc_notes_file));
			
			file = new File(getFilesDir() + "/my_notes.txt");
			fis = new FileInputStream(file);
			
			if (fis != null) {
				theNotes = new ArrayList<String>();
				dis = new DataInputStream(fis);
				br = new BufferedReader(new InputStreamReader(dis));
				String line = null;
				
				while ((line = br.readLine()) != null) {
					theNotes.add(line);
				}
			}			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
					br = null;
				}
				
				if (dis != null) {
					dis.close();
					dis = null;
				}
				
				if (fis != null) {
					fis.close();
					fis = null;
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return theNotes;
	}
}

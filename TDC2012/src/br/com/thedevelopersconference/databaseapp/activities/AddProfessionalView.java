package br.com.thedevelopersconference.databaseapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import br.com.thedevelopersconference.databaseapp.R;
import br.com.thedevelopersconference.databaseapp.domain.AppModel;
import br.com.thedevelopersconference.databaseapp.domain.Professional;
import br.com.thedevelopersconference.databaseapp.services.db.ProfessionalDao;

/**
 * View used to add professionals to the database.
 * 
 * @author pedrobrigatto
 */
public class AddProfessionalView extends Activity {
	
	private EditText name;
	private Spinner role;
	private Button actionSave;
	private Button actionDiscard;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tdc_add_professional_layout);
		
		name = (EditText) findViewById(R.id.tdc_add_prof_name);
		
		role = (Spinner) findViewById(R.id.tdc_add_prof_roles);
		role.setAdapter(new ArrayAdapter<String>(getBaseContext(), 
				android.R.layout.simple_spinner_dropdown_item, AppModel.PROFESSIONAL_ROLES));
		
		actionSave = (Button) findViewById(R.id.tdc_add_prof_action_add);
		actionSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if (null != name.getText().toString() &&
						!"".equals(name.getText().toString())) {
					ProfessionalDao dao = new ProfessionalDao(getApplicationContext());
					Professional prof = new Professional(name.getText().toString());
					prof.setRole(role.getSelectedItemPosition());
					
					if (dao.save(prof)) {
						Toast.makeText(getBaseContext(), 
							getResources().getString(R.string.tdc_add_prof_prof_saved), 
							Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(getBaseContext(), 
								getResources().getString(R.string.tdc_add_prof_prof_not_saved), 
								Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(getBaseContext(), 
							getResources().getString(R.string.tdc_add_prof_empty_fields), 
							Toast.LENGTH_LONG).show();
				}
				
				name.setText("");
			}
		});
		
		actionDiscard = (Button) findViewById(R.id.tdc_add_prof_action_discard);
		actionDiscard.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}

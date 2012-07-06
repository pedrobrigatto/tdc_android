package br.com.thedevelopersconference.databaseapp.activities;

import android.app.Dialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import br.com.thedevelopersconference.databaseapp.R;
import br.com.thedevelopersconference.databaseapp.activities.adapters.ProfessionalListAdapter;
import br.com.thedevelopersconference.databaseapp.domain.Professional;
import br.com.thedevelopersconference.databaseapp.services.db.ProfessionalDao;

/**
 * View used to check all the professionals stored in the database.
 * 
 * @author pedrobrigatto
 */
public class AllProfessionalsView extends ListActivity {

	private Dialog dialog;

	// Dialog components
	private TextView professionalCode;
	private TextView professionalName;
	private TextView professionalRole;
	private Button dialogOk;

	private int selected = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ProfessionalDao dao = new ProfessionalDao(getApplicationContext());
		setListAdapter(new ProfessionalListAdapter(getBaseContext(), dao.listAll()));
		setContentView(R.layout.tdc_all_professionals_layout);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		this.selected = position;
		
		dialog = new Dialog(this);
		dialog.setContentView(R.layout.tdc_dialog_professional_info);
		dialog.setCancelable(true);

		professionalCode = (TextView) dialog.findViewById(R.id.tdc_dialog_professional_info_code);
		professionalName = (TextView) dialog.findViewById(R.id.tdc_dialog_professional_info_name);
		professionalRole = (TextView) dialog.findViewById(R.id.tdc_dialog_professional_info_role);

		refreshProfessionalData();

		dialogOk = (Button) dialog.findViewById(R.id.tdc_dialog_professional_info_ok);
		dialogOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
		});
		
		dialog.show();
	}

	private void refreshProfessionalData() {
		Professional professional = (Professional) getListView().getItemAtPosition(selected);

		professionalCode.setText(professional.getCode());
		professionalName.setText(professional.getName());

		if (professional.getRole() == Professional.ROLE_DEVELOPER) {
			professionalRole.setText("Developer");
		} else if (professional.getRole() == Professional.ROLE_DEVELOPER) {
			professionalRole.setText("Architect");
		}
	}

	@Override
	protected Dialog onCreateDialog(int dialogType) {
		return dialog;
	}
}

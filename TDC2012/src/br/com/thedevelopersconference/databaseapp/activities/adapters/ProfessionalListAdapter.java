package br.com.thedevelopersconference.databaseapp.activities.adapters;

import java.util.List;

import br.com.thedevelopersconference.databaseapp.R;
import br.com.thedevelopersconference.databaseapp.domain.Professional;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Adapter used to help the professional list to show its data.
 * 
 * @author pedrobrigatto
 */
public class ProfessionalListAdapter extends BaseAdapter {
	
	private Context context;
	private List<Professional> professionals;
	
	public ProfessionalListAdapter (Context context, List<Professional> professionals) {
		this.context = context;
		this.professionals = professionals;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return professionals.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return professionals.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) 
				context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row = inflater.inflate(R.layout.tdc_professional_list_layout, null);
		
		TextView title = (TextView) row.findViewById(R.id.prof_list_row_title);
		TextView subtitle = (TextView) row.findViewById(R.id.prof_list_row_subtitle);
		
		title.setText(professionals.get(position).getName());
		
		int role = professionals.get(position).getRole();
		
		if (role == Professional.ROLE_ARCHITECT) {
			subtitle.setText("Role: Architect");
		} else if (role == Professional.ROLE_DEVELOPER) {
			subtitle.setText("Role: Developer");
		} else {
			subtitle.setText("Role: Undefined");
		}
		return row;
	}
}

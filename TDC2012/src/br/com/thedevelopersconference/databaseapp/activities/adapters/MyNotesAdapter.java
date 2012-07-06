package br.com.thedevelopersconference.databaseapp.activities.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.thedevelopersconference.databaseapp.R;

/**
 * Helps the adapter list view to accomodate and handle the data related to the
 * user's personal notes on a given speech.
 * 
 * @author pedrobrigatto
 */
public class MyNotesAdapter extends BaseAdapter {

	private List<String> notes;
	private Context context;

	public MyNotesAdapter (Context context, List<String> notes) {
		this.context = context;
		this.notes = notes;
	}
	
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return notes.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return notes.get(position);
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
		
		View row = inflater.inflate(R.layout.tdc_mynotes_row_layout, null);
		TextView content = (TextView) row.findViewById(R.id.tdc_mynotes_list_row_content);
		content.setText(notes.get(position));
		return row;
	}
}

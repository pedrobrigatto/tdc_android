package br.com.thedevelopersconference.databaseapp.activities.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.thedevelopersconference.databaseapp.R;
import br.com.thedevelopersconference.databaseapp.domain.Speech;

/**
 * Helps populating the speech list view and the management of data it displays.
 * 
 * @author pedrobrigatto
 */
public class SpeechListAdapter extends BaseAdapter {
	
	private Context context;
	private List<Speech> speeches;
	
	public SpeechListAdapter(Context context, List<Speech> speeches) {
		this.context = context;
		this.speeches = speeches;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return speeches.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return speeches.get(position);
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
		View row = inflater.inflate(R.layout.tdc_speech_list_layout, null);
		
		TextView title = (TextView) row.findViewById(R.id.speech_list_row_title);
		TextView info = (TextView) row.findViewById(R.id.speech_list_row_author);
		
		title.setText(speeches.get(position).getTitle());
		info.setText(speeches.get(position).getAuthor().getName() + 
				" at " + speeches.get(position).getWhen());
		return row;
	}

	public void updateModel(List<Speech> newData) {
		speeches = newData;
		notifyDataSetChanged();
	}
}

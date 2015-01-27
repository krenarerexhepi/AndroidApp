package uk.ac.aber.rpsrrec.ui;

import java.util.ArrayList;

import uk.ac.aber.plantcatalog.R;
import uk.ac.aber.rpsrrec.data.Sighting;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SightingListAdapter extends ArrayAdapter<Sighting> {
	private final Context context;
	private final  ArrayList<Sighting> values;

	public SightingListAdapter(Context context, ArrayList<Sighting> values) {
		super(context, R.layout.sighting_list_item, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.sighting_list_item, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.specimenName);
		textView.setText(values.get(position).getSpecies());

		textView = (TextView) rowView.findViewById(R.id.specimenDescription);
		textView.setText(values.get(position).getDescription());

		return rowView;
	}
}

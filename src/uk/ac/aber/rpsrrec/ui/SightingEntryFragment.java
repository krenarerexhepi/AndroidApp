package uk.ac.aber.rpsrrec.ui;

import uk.ac.aber.plantcatalog.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

public class SightingEntryFragment extends DialogFragment {

	private SightingEntryListener fragmentListener;
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		LayoutInflater inflater = getActivity().getLayoutInflater();

		builder
			.setView(inflater.inflate(R.layout.fragment_sighting_entry, null))
			.setTitle(R.string.sighting_entry_dialog)
			.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
					fragmentListener.onSightingEntryPositiveClick(SightingEntryFragment.this);
				}
			})
			.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
					fragmentListener.onSightingEntryDialogNegativeClick(SightingEntryFragment.this);
				}
			});

		return builder.create();
	}

	public interface SightingEntryListener {
		public void onSightingEntryPositiveClick(DialogFragment dialog);
		public void onSightingEntryDialogNegativeClick(DialogFragment dialog);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			fragmentListener = (SightingEntryListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement SightingEntryListener");
		}
	}

}

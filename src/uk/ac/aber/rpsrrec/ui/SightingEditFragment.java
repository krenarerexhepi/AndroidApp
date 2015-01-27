package uk.ac.aber.rpsrrec.ui;

import uk.ac.aber.plantcatalog.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class SightingEditFragment extends DialogFragment {

	private SightingEntryListener entryFragmentListener;
	private SightingEditListener editFragmentListener;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.fragment_sighting_entry, null);

		builder
			.setView(view)
			.setTitle(R.string.sighting_entry_dialog)
			.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
					entryFragmentListener.onSightingEntryPositiveClick(SightingEditFragment.this);
					editFragmentListener.onSightingEditNegativeClick(SightingEditFragment.this);
				}
			})
			.setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
					entryFragmentListener.onSightingEntryNeutralClick(SightingEditFragment.this);
				}
			})
			.setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
					editFragmentListener.onSightingEditNegativeClick(SightingEditFragment.this);
				}
			});

		ImageButton specimenPictureButton = (ImageButton) view.findViewById(R.id.captureSpecimenImage);
		specimenPictureButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				entryFragmentListener.onCameraSpeciesClick(SightingEditFragment.this);
			}
		});

		ImageButton locationPictureButton = (ImageButton) view.findViewById(R.id.captureLocationImage);
		locationPictureButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				entryFragmentListener.onCameraLocationClick(SightingEditFragment.this);
			}
		});

		return builder.create();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			entryFragmentListener = (SightingEntryListener) activity;
			editFragmentListener = (SightingEditListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement SightingEntryListener and SightingEditListener");
		}
	}

}

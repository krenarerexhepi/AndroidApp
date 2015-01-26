package uk.ac.aber.rpsrrec.ui;

import uk.ac.aber.plantcatalog.R;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class SightingEntryFragment extends DialogFragment {

	private SightingEntryListener fragmentListener;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_sighting_entry, container, false);

		Button specimenPictureButton = (Button) view.findViewById(R.id.captureSpecimenImage);
		specimenPictureButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				fragmentListener.onCameraSpeciesClick(SightingEntryFragment.this);
			}
		});

		Button locationPictureButton = (Button) view.findViewById(R.id.captureLocationImage);
		locationPictureButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				fragmentListener.onCameraLocationClick(SightingEntryFragment.this);
			}
		});

		Button saveButton = (Button) view.findViewById(R.id.save);
		saveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				fragmentListener.onSightingEntryPositiveClick(SightingEntryFragment.this);
			}
		});

		Button cancelButton = (Button) view.findViewById(R.id.cancel);
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				fragmentListener.onSightingEntryNegativeClick(SightingEntryFragment.this);
			}
		});

		fragmentListener.onCreateGetLocation(SightingEntryFragment.this);

		return view;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
/*
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
*/
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		dialog.setTitle(R.string.log_on_dialog);

		return dialog;
	}

	public interface SightingEntryListener {
		public void onCreateGetLocation(DialogFragment dialog);
		public void onSightingEntryPositiveClick(DialogFragment dialog);
		public void onSightingEntryNegativeClick(DialogFragment dialog);
		public void onCameraSpeciesClick(DialogFragment dialog);
		public void onCameraLocationClick(DialogFragment dialog);
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

package uk.ac.aber.rpsrrec.ui;

import uk.ac.aber.plantcatalog.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

public class ReserveEntryFragment extends DialogFragment {

	private ReserveEntryDialogListener fragmentListener;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		LayoutInflater inflater = getActivity().getLayoutInflater();

		builder
			.setView(inflater.inflate(R.layout.fragment_reserve_entry, null))
			.setTitle(R.string.reserve_entry_dialog)
			.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
					fragmentListener.onReserveEntryDialogPositiveClick(ReserveEntryFragment.this);
				}
			})
			.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
					fragmentListener.onReserveEntryDialogNegativeClick(ReserveEntryFragment.this);
				}
			});

		return builder.create();
	}

	public interface ReserveEntryDialogListener {
		public void onReserveEntryDialogPositiveClick(DialogFragment dialog);
		public void onReserveEntryDialogNegativeClick(DialogFragment dialog);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			fragmentListener = (ReserveEntryDialogListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement LogOnDialogListener");
		}
	}

}

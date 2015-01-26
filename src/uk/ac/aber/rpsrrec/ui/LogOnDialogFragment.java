package uk.ac.aber.rpsrrec.ui;

import uk.ac.aber.plantcatalog.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

public class LogOnDialogFragment extends DialogFragment {

	private LogOnDialogListener fragmentListener;
/*
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_log_on, container, false);
	}
*/
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		LayoutInflater inflater = getActivity().getLayoutInflater();

		builder
			.setView(inflater.inflate(R.layout.fragment_log_on, null))
			.setTitle(R.string.log_on_dialog)
			.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
					fragmentListener.onLogOnDialogPositiveClick(LogOnDialogFragment.this);
				}
			})
			.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
					fragmentListener.onLogOnDialogNegativeClick(LogOnDialogFragment.this);
				}
			});

		return builder.create();
/*
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		dialog.setTitle(R.string.log_on_dialog);

		return dialog;
*/
	}

	public interface LogOnDialogListener {
		public void onLogOnDialogPositiveClick(DialogFragment dialog);
		public void onLogOnDialogNegativeClick(DialogFragment dialog);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			fragmentListener = (LogOnDialogListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement LogOnDialogListener");
		}
	}

}

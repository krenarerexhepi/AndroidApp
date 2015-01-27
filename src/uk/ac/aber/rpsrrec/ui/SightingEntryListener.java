package uk.ac.aber.rpsrrec.ui;

import android.app.DialogFragment;

public interface SightingEntryListener {
	public void onCreateGetLocation(DialogFragment dialog);
	public boolean onSightingEntryPositiveClick(DialogFragment dialog);
	public void onSightingEntryNeutralClick(DialogFragment dialog);
	public void onCameraSpeciesClick(DialogFragment dialog);
	public void onCameraLocationClick(DialogFragment dialog);
}

package uk.ac.aber.rpsrrec.ui;

import android.app.DialogFragment;
import android.view.View;

public interface SightingEntryListener {
	public void onCreateSightingSearch(View view);
	public void onCreateGetLocation(DialogFragment dialog);
	public boolean onSightingEntryPositiveClick(DialogFragment dialog);
	public void onSightingEntryNeutralClick(DialogFragment dialog);
	public void onCameraSpeciesClick(DialogFragment dialog);
	public void onCameraLocationClick(DialogFragment dialog);
}

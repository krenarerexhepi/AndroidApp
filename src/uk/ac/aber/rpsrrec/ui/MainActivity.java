package uk.ac.aber.rpsrrec.ui;

import java.util.ArrayList;

import uk.ac.aber.plantcatalog.R;
import uk.ac.aber.rpsrrec.data.Sighting;
import uk.ac.aber.rpsrrec.data.User;
import uk.ac.aber.rpsrrec.data.Visit;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

/** 
 * Missing:
 *   Soft keyboard on auto-focus edit fields
 *   Parcelable or any other way to restore session after closing/minimising app
 *   Camera stuff
 *   Sending preparations/summary page
 *   Proper location
 *   Users reserve selection (another dialog?)
 *   Species/reserve search for easier user selection
 *   Updating species/reserve data from database
 */

public class MainActivity extends FragmentActivity
	implements LogOnDialogFragment.LogOnDialogListener, SightingEntryFragment.SightingEntryListener {

	Visit visit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

//		Bundle data = getIntent().getExtras();
//
//		if (data != null) {
//			visit = data.getParcelable("visit");
//
//			ArrayList<Sighting> sightings = visit.getSightings();
//
//			ArrayAdapter<Sighting> sightingsAdapter = 
//					new ArrayAdapter<Sighting>(this, android.R.layout.simple_list_item_1, sightings);
//
//			ListView listview = (ListView) findViewById(R.id.sightingListView);
//			listview.setAdapter(sightingsAdapter);
//		}

		if (visit == null) {
			LogOnDialogFragment logOn = new LogOnDialogFragment();
			logOn.show(getFragmentManager(), "log_on");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void recordSighting(View view) {
/*
		Intent intent = new Intent(this, SightingEntryActivity.class);
		intent.putExtra("visit", visit);
		startActivity(intent);
*/
		SightingEntryFragment sightingEntry = new SightingEntryFragment();
		sightingEntry.show(getFragmentManager(), "sighting_entry");
	}

// LOGON LISTENER /////////////////////////////////////////////////////////////

	@Override
	public void onLogOnDialogPositiveClick(DialogFragment dialog) {

		Dialog dialogView = dialog.getDialog();

		EditText editText = (EditText) dialogView.findViewById(R.id.userName);
		String userName = editText.getText().toString();

		editText = (EditText) dialogView.findViewById(R.id.userPhone);
		String userPhone = editText.getText().toString();

		editText = (EditText) dialogView.findViewById(R.id.userEmail);
		String userEmail = editText.getText().toString();

		if (userName.equals("") || userPhone.equals("") || userEmail.equals("")) {
			Toast.makeText(getApplicationContext(), "User details not complete", Toast.LENGTH_SHORT).show();
		} else {
			visit = new Visit(new User(userName, userPhone, userEmail), "date");
			Toast.makeText(getApplicationContext(), "User details added", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onLogOnDialogNegativeClick(DialogFragment dialog) {
		Toast.makeText(getApplicationContext(), "User details required", Toast.LENGTH_SHORT).show();
	}

	// SIGHTING ENTRY LISTENER ////////////////////////////////////////////////

	@Override
	public void onSightingEntryPositiveClick(DialogFragment dialog) {

		Dialog dialogView = dialog.getDialog();

		EditText specimenName = (EditText) dialogView.findViewById(R.id.specimenName);
		String name = specimenName.getText().toString();

		Spinner daforSelected = (Spinner) dialogView.findViewById(R.id.daforSelector);
		String dafor = daforSelected.getSelectedItem().toString();

		EditText descriptionDone = (EditText) dialogView.findViewById(R.id.description);
		String description = descriptionDone.getText().toString();

		if (visit != null) {
			visit.addNewSighting(new Sighting(name, description, dafor));
		}

		Toast.makeText(getApplicationContext(), "Sighting details added", Toast.LENGTH_SHORT).show();

		ArrayList<Sighting> sightings = visit.getSightings();

		ArrayAdapter<Sighting> sightingsAdapter = 
				new ArrayAdapter<Sighting>(this, android.R.layout.simple_list_item_1, sightings);

		ListView listview = (ListView) findViewById(R.id.sightingListView);
		listview.setAdapter(sightingsAdapter);
	}

	@Override
	public void onSightingEntryDialogNegativeClick(DialogFragment dialog) {
		Toast.makeText(getApplicationContext(), "Sighting entry canceled", Toast.LENGTH_SHORT).show();
	}

}

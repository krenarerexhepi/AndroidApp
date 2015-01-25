package uk.ac.aber.rpsrrec.ui;

import java.util.ArrayList;

import uk.ac.aber.plantcatalog.R;
import uk.ac.aber.rpsrrec.data.Sighting;
import uk.ac.aber.rpsrrec.data.User;
import uk.ac.aber.rpsrrec.data.Visit;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements LogOnDialogFragment.LogOnDialogListener {

	Visit visit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Bundle data = getIntent().getExtras();

		if (data != null) {
			visit = data.getParcelable("visit");

			ListView listview = (ListView) findViewById(R.id.sightingListView);
			ArrayList<Sighting> sightings = visit.getSightings();
		}

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
		Intent intent = new Intent(this, SightingEntryActivity.class);

		intent.putExtra("visit", visit);

		startActivity(intent);
	}

// LOGON DIALOG ///////////////////////////////////////////////////////////////

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {

		Dialog dialogView = dialog.getDialog();

		EditText editText = (EditText) dialogView.findViewById(R.id.userName);
		String userName = editText.getText().toString();

		editText = (EditText) dialogView.findViewById(R.id.userPhone);
		String userPhone = editText.getText().toString();

		editText = (EditText) dialogView.findViewById(R.id.userEmail);
		String userEmail = editText.getText().toString();

		visit = new Visit(new User(userName, userPhone, userEmail), "date");

		Toast.makeText(getApplicationContext(), "User details added.", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		Toast.makeText(getApplicationContext(), "User details required.", Toast.LENGTH_SHORT).show();
	}

}

package uk.ac.aber.rpsrrec.ui;

import uk.ac.aber.plantcatalog.R;
import uk.ac.aber.rpsrrec.data.Sighting;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class SightingEntryActivity extends Activity {

	// TODO Implement the picture taking methods from the button

	private Location androidGpsLocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO make it incorporate the gps stuff and display it at the start
		// TODO also update androidGpsLocation
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sighting_entry);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sighting_entry, menu);
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

	public void addSighting(View view) {
		//Does it go to visits or List?
		Intent intent = new Intent(this, SightingsListActivity.class);

		EditText specimenName = (EditText) findViewById(R.id.specimenName);
		String name = specimenName.getText().toString();

		Spinner daforSelected = (Spinner) findViewById(R.id.daforSelector);
		String dafor = daforSelected.getSelectedItem().toString();

		// TODO do something about location!!

		EditText descriptionDone = (EditText) findViewById(R.id.description);
		String description = descriptionDone.getText().toString();

//		ImageView specimenImageTaken = (ImageView) findViewById(R.id.specimenImageDisplay);
//		// this might be wrong (getting the image)
//		Bitmap specimenImage = specimenImageTaken.getDrawingCache();
//
//		ImageView locationImageTaken = (ImageView) findViewById(R.id.locationImageDisplay);
//		// this might be wrong (getting the image)
//		Bitmap locationImage = locationImageTaken.getDrawingCache();

//		intent.putExtra("sighting", new Sighting(name, description, dafor,
//				androidGpsLocation, specimenImage, locationImage));
		
		intent.putExtra("sighting", new Sighting(name, description, dafor,
				null, null, null));
		
		startActivity(intent);
	}
}

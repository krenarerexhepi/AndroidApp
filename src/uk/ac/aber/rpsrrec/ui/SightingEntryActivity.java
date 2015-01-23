package uk.ac.aber.rpsrrec.ui;

import uk.ac.aber.plantcatalog.R;
import uk.ac.aber.rpsrrec.data.Sighting;
import uk.ac.aber.rpsrrec.data.Visit;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class SightingEntryActivity extends Activity {

	// TODO Implement the picture taking methods from the button
	private Visit visit;
	private Location loc;
	private double locLat;
	private double locLng;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO make it incorporate the gps stuff and display it at the start
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sighting_entry);

		Bundle data = getIntent().getExtras();

		visit = data.getParcelable("visit");

		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

		LocationListener locationListener = new LocationListener() {
		    public void onLocationChanged(Location location) {
		      loc = location;
		    }

		    public void onStatusChanged(String provider, int status, Bundle extras) {}

		    public void onProviderEnabled(String provider) {}

		    public void onProviderDisabled(String provider) {}
		  };

		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
	}

	public void showLocation(View view) {
		try {
			Thread.sleep(5000);
			if (loc != null) {
				TextView latView = (TextView) findViewById(R.id.latitudeDisplay);
				String lat = String.valueOf(locLat = loc.getLatitude());
				TextView lngView = (TextView) findViewById(R.id.longitudeDisplay);
				String lng = String.valueOf(locLng = loc.getLongitude());
				latView.setText(lat);
				lngView.setText(lng);
			}
			else {
				locLat = 270;
				locLng = 270;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		Intent intent = new Intent(this, SightingsListActivity.class);

		EditText specimenName = (EditText) findViewById(R.id.specimenName);
		String name = specimenName.getText().toString();

		Spinner daforSelected = (Spinner) findViewById(R.id.daforSelector);
		String dafor = daforSelected.getSelectedItem().toString();

		EditText descriptionDone = (EditText) findViewById(R.id.description);
		String description = descriptionDone.getText().toString();
/*
		ImageView specimenImageTaken = (ImageView) findViewById(R.id.specimenImageDisplay);
		// this might be wrong (getting the image)
		Bitmap specimenImage = specimenImageTaken.getDrawingCache();

		ImageView locationImageTaken = (ImageView) findViewById(R.id.locationImageDisplay);
		// this might be wrong (getting the image)
		Bitmap locationImage = locationImageTaken.getDrawingCache();
*/
		visit.addNewSighting(new Sighting(name, description, dafor, locLat, locLng));
		intent.putExtra("visit", visit);
		
		startActivity(intent);
	}
}
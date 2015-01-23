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
import android.widget.Toast;

public class SightingEntryActivity extends Activity implements LocationListener {

	// TODO Implement the picture taking methods from the button
	private Visit visit;
	private double locLat;
	private double locLng;
	private LocationManager locationManager;
	private String provider;
	private TextView latView;
	private TextView lngView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO make it incorporate the gps stuff and display it at the start
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sighting_entry);
		latView = (TextView) findViewById(R.id.latitudeDisplay);
		lngView = (TextView) findViewById(R.id.longitudeDisplay);

		Bundle data = getIntent().getExtras();

		visit = data.getParcelable("visit");

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		// Selecting the location provider
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(provider);

		// Initialize lcoation fields
		if (location != null) {
			System.out.println("Provider " + provider + " has been selected.");
			onLocationChanged(location);
		} else {
			latView.setText("NA");
			lngView.setText("NA");
		}

	}

	/* Request updates at startup */
	@Override
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER, 400, 1, this);
	}

	/* Remove the locationlistener updates when Activity is paused */
	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location location) {
		int lat = (int) (location.getLatitude());
		int lng = (int) (location.getLongitude());
		latView.setText(String.valueOf(lat));
		lngView.setText(String.valueOf(lng));
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderEnabled(String provider) {
		Toast.makeText(this, "Enabled new provider " + provider,
		Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(this, "Disabled provider " + provider,
		Toast.LENGTH_SHORT).show();
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
package uk.ac.aber.rpsrrec.ui;

import java.util.ArrayList;

import uk.ac.aber.plantcatalog.R;
import uk.ac.aber.rpsrrec.data.Sighting;
import uk.ac.aber.rpsrrec.data.User;
import uk.ac.aber.rpsrrec.data.Visit;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Missing: Soft keyboard on auto-focus edit fields Parcelable or any other way
 * to restore session after closing/minimising app Camera stuff Sending
 * preparations/summary page Proper location Users reserve selection (another
 * dialog?) Species/reserve search for easier user selection Updating
 * species/reserve data from database
 */

public class MainActivity extends FragmentActivity implements
		LogOnDialogFragment.LogOnDialogListener,
		SightingEntryFragment.SightingEntryListener,
		LocationListener {

	private Visit visit;

	private DialogFragment dialog;

	private static final int REQUEST_IMAGE_CAPTURE_SPECIMEN = 1;
	private static final int REQUEST_IMAGE_CAPTURE_LOCATION = 2;
	private boolean specimenPic = false;
	private boolean locationPic = false;

	private double locLat;
	private double locLng;

	private LocationManager locationManager;
	private String provider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (visit == null) {
			visit = new Visit();
		}

		if (visit.getUser() == null) {
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
			Toast.makeText(getApplicationContext(),
					"User details not complete", Toast.LENGTH_SHORT).show();
		} else {
			visit.setUser(new User(userName, userPhone, userEmail));
			visit.setDate("date");
			Toast.makeText(getApplicationContext(), "User details added",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onLogOnDialogNegativeClick(DialogFragment dialog) {
		Toast.makeText(getApplicationContext(), "User details required",
				Toast.LENGTH_SHORT).show();
	}

// SIGHTING ENTRY LISTENER ////////////////////////////////////////////////////

	@Override
	public void onCreateGetLocation(DialogFragment dialog) {

		Dialog dialogView = dialog.getDialog();

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

		TextView latView = (TextView) dialogView.findViewById(R.id.latitudeDisplay);
		TextView lngView = (TextView) dialogView.findViewById(R.id.longitudeDisplay);

		// Selecting the location provider
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(provider);

		// Initialize lcoation fields
		if (location != null) {
			//System.out.println("Provider " + provider + " has been selected.");
			onLocationChanged(location);

			Toast.makeText(this, "LAT: " + locLat + "LNG: " + locLng, Toast.LENGTH_SHORT).show();

//			int lat = (int) locLat;
//			int lng = (int) locLng;
//			latView.setText(String.valueOf(lat));
//			lngView.setText(String.valueOf(lng));
		}
		else {
			Toast.makeText(this, "Cant find location using " + provider, Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onSightingEntryPositiveClick(DialogFragment dialog) {

		Dialog dialogView = dialog.getDialog();

		EditText specimenName = (EditText) dialogView.findViewById(R.id.specimenName);
		String name = specimenName.getText().toString();

		Spinner daforSelected = (Spinner) dialogView.findViewById(R.id.daforSelector);
		String dafor = daforSelected.getSelectedItem().toString();

		EditText descriptionDone = (EditText) dialogView.findViewById(R.id.description);
		String description = descriptionDone.getText().toString();

		ImageView specimenImageTaken = (ImageView) dialogView.findViewById(R.id.specimenImageDisplay);

		Bitmap specimenImage = ((BitmapDrawable)specimenImageTaken.getDrawable()).getBitmap();

		ImageView locationImageTaken = (ImageView) dialogView.findViewById(R.id.locationImageDisplay);

		Bitmap locationImage = ((BitmapDrawable)locationImageTaken.getDrawable()).getBitmap();

		if (visit != null) {
			visit.addNewSighting(new Sighting(name, dafor, description, locLat, locLng, specimenImage, locationImage, specimenPic , locationPic));
		}

		dialog.dismiss();

		Toast.makeText(getApplicationContext(), "Sighting details added", Toast.LENGTH_SHORT).show();

		ArrayList<Sighting> sightings = visit.getSightings();

		ArrayAdapter<Sighting> sightingsAdapter = new ArrayAdapter<Sighting>(this, android.R.layout.simple_list_item_1, sightings);

		ListView listview = (ListView) findViewById(R.id.sightingListView);
		listview.setAdapter(sightingsAdapter);
	}

	@Override
	public void onSightingEntryNegativeClick(DialogFragment dialog) {
		Toast.makeText(getApplicationContext(), "Sighting entry canceled", Toast.LENGTH_SHORT).show();

		dialog.dismiss();

	}

// CAMERA MANAGER /////////////////////////////////////////////////////////////

	@Override
	public void onCameraSpeciesClick(DialogFragment dialog) {
		this.dialog = dialog;
		specimenPic = true;
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, REQUEST_IMAGE_CAPTURE_SPECIMEN);
	}

	@Override
	public void onCameraLocationClick(DialogFragment dialog) {
		this.dialog = dialog;
		locationPic = true;
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, REQUEST_IMAGE_CAPTURE_LOCATION);
	}

	private void dispatchTakePictureIntent() {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		if(specimenPic) {
			if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
				startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_SPECIMEN);
			}
		}
		if(locationPic) {
			if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
				startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_LOCATION);
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Dialog dialogView = dialog.getDialog();
		if(specimenPic) {
			if (requestCode == REQUEST_IMAGE_CAPTURE_SPECIMEN && resultCode == RESULT_OK) {
				ImageView picDisplay = (ImageView) dialogView.findViewById(R.id.specimenImageDisplay);
				Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");

				picDisplay.setImageBitmap(imageBitmap);
			}
		} 
		if(locationPic) {
			if (requestCode == REQUEST_IMAGE_CAPTURE_LOCATION && resultCode == RESULT_OK) {
				ImageView picDisp = (ImageView) dialogView.findViewById(R.id.locationImageDisplay);
				Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");

				picDisp.setImageBitmap(imageBitmap);
			}
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		locLat = location.getLatitude();
		locLng = location.getLongitude();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {}

	@Override
	public void onProviderEnabled(String provider) {}

	@Override
	public void onProviderDisabled(String provider) {}

}

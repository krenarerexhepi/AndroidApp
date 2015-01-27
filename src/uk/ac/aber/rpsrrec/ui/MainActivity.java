package uk.ac.aber.rpsrrec.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import uk.ac.aber.plantcatalog.R;
import uk.ac.aber.rpsrrec.data.SendToServer;
import uk.ac.aber.rpsrrec.data.Sighting;
import uk.ac.aber.rpsrrec.data.User;
import uk.ac.aber.rpsrrec.data.Visit;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements
		LogOnDialogFragment.LogOnDialogListener,
		ReserveEntryFragment.ReserveEntryDialogListener,
		SightingEntryListener,
		SightingEditListener,
		LocationListener {

	private Visit visit;

	private DialogFragment dialog;

	private static final String STATE_VISIT = "visit";

	private static final int REQUEST_IMAGE_CAPTURE_SPECIMEN = 1;
	private static final int REQUEST_IMAGE_CAPTURE_LOCATION = 2;
	private boolean specimenPic = false;
	private boolean locationPic = false;

	private double locLat;
	private double locLng;

	private LocationManager locationManager;
	private String provider;

	private ListView listView;

	private int sightingPosition = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState != null) {
			visit = savedInstanceState.getParcelable(STATE_VISIT);
		}
		if (visit == null) {
			visit = new Visit();
		}

		if (visit.getUser() == null) {
			LogOnDialogFragment logOn = new LogOnDialogFragment();
			logOn.show(getFragmentManager(), "log_on");
		}

		listView = (ListView) findViewById(R.id.sightingListView);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				sightingPosition = position;
				editSighting();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_add_user) {
			addUser();
		}
		else if (id == R.id.action_select_reserve) {
			selectReserve();
		}
		else if (id == R.id.action_delete_visit) {
			visit = new Visit();
			updateSightingList();
			updateLocation();
		}
		else if (id == R.id.action_send_visit) {
			SendToServer send = new SendToServer(visit);
			visit = new Visit();
			updateSightingList();
			updateLocation();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putParcelable(STATE_VISIT, visit);
		super.onSaveInstanceState(savedInstanceState);
	}

	public void addUser() {
		LogOnDialogFragment logOn = new LogOnDialogFragment();
		logOn.show(getFragmentManager(), "log_on");
	}

	public void selectReserve() {

		ReserveEntryFragment reserveEntry = new ReserveEntryFragment();
		reserveEntry.show(getFragmentManager(), "reserve_entry");
		addDate();
	}

	public void addDate() {
		Date date = Calendar.getInstance().getTime();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss", Locale.UK);
		String formattedDate = simpleDateFormat.format(date);
		visit.setDate(formattedDate);
	}

	public void updateLocation() {
		TextView reserveView = (TextView) findViewById(R.id.locationView);
		reserveView.setText("No Location Selected");
	}

	public void recordSighting(View view) {
		SightingEntryFragment sightingEntry = new SightingEntryFragment();
		sightingEntry.show(getFragmentManager(), "sighting_entry");
	}

	public void updateSightingList() {
		ArrayList<Sighting> sightings = visit.getSightings();

		SightingListAdapter sightingsAdapter = new SightingListAdapter(this, sightings);

		listView.setAdapter(sightingsAdapter);
	}

	public void editSighting() {
		SightingEditFragment sightingEdit = new SightingEditFragment();
		sightingEdit.show(getFragmentManager(), "sighting_edit");
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
		}

		if (visit.getReserveName() == null) {
			selectReserve();
		}
	}

	@Override
	public void onLogOnDialogNegativeClick(DialogFragment dialog) {
		Toast.makeText(getApplicationContext(), "User details required",
				Toast.LENGTH_SHORT).show();

		if (visit.getReserveName() == null) {
			selectReserve();
		}
	}

// RESERVE ENTRY LISTENER /////////////////////////////////////////////////////

	@Override
	public void onCreateReserveSearch(View view) {
		Resources r = getResources();
		String [] list = r.getStringArray(R.array.reserves_names);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
 
		AutoCompleteTextView textView = (AutoCompleteTextView) view.findViewById(R.id.reserveName);
		textView.setThreshold(1);
        textView.setAdapter(adapter);
	}

	@Override
	public void onReserveEntryDialogPositiveClick(DialogFragment dialog) {

		Dialog dialogView = dialog.getDialog();

		EditText editText = (EditText) dialogView.findViewById(R.id.reserveName);
		String reserveName = editText.getText().toString();

		if(reserveName.equals("")) {
			Toast.makeText(getApplicationContext(),
					"Reserve details not complete", Toast.LENGTH_SHORT).show();
		} else {
			visit.setReserve(reserveName);
			TextView reserveView = (TextView) findViewById(R.id.locationView);
			reserveView.setText(reserveName);
		}
	}

	@Override
	public void onReserveEntryDialogNegativeClick(DialogFragment dialog) {
		Toast.makeText(getApplicationContext(), "Reserve details required",
				Toast.LENGTH_SHORT).show();
	}

// SIGHTING ENTRY LISTENER ////////////////////////////////////////////////////

	@Override
	public void onCreateSightingSearch(View view) {
		Resources r = getResources();
		String [] list = r.getStringArray(R.array.species_names);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
 
		AutoCompleteTextView textView = (AutoCompleteTextView) view.findViewById(R.id.specimenName);
		textView.setThreshold(1);
        textView.setAdapter(adapter);
	}

	@Override
	public void onCreateGetLocation(DialogFragment dialog) {

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(provider);

		if (location != null) {
			onLocationChanged(location);
			Toast.makeText(this, "LAT: " + locLat + " LNG: " + locLng, Toast.LENGTH_SHORT).show();
		}
		else {
			Toast.makeText(this, "Cant find location using " + provider, Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public boolean onSightingEntryPositiveClick(DialogFragment dialog) {

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

		if (name.equals("")) {
			Toast.makeText(getApplicationContext(), "Species is required", Toast.LENGTH_SHORT).show();
			return false;
		}

		if (visit != null) {
			visit.addNewSighting(new Sighting(name, dafor, description, locLat, locLng, specimenImage, locationImage, specimenPic, locationPic));
			updateSightingList();
			return true;
		}
		else {
			Toast.makeText(getApplicationContext(), "No current visit", Toast.LENGTH_SHORT).show();
			return false;
		}
	}

	@Override
	public void onSightingEntryNeutralClick(DialogFragment dialog) {
		Toast.makeText(getApplicationContext(), "Sighting entry canceled", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onSightingEditNegativeClick(DialogFragment dialog) {
		visit.getSightings().remove(sightingPosition);

		updateSightingList();
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

// LOCATION MANAGER ///////////////////////////////////////////////////////////

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

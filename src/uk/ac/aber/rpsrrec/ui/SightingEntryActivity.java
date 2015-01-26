package uk.ac.aber.rpsrrec.ui;

import uk.ac.aber.plantcatalog.R;
import uk.ac.aber.rpsrrec.data.Sighting;
import uk.ac.aber.rpsrrec.data.Visit;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
//import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
//import android.widget.TextView;
import android.widget.Toast;

public class SightingEntryActivity extends Activity implements LocationListener {

	private Visit visit;
	
	private double locLat;
	private double locLng;
	
//	private LocationManager locationManager;
//	private String provider;
//	
//	private TextView latView;
//	private TextView lngView;
	
///// camera variables /////

	static final int REQUEST_IMAGE_CAPTURE = 1;
	static final int REQUEST_IMAGE_CAPT = 2;
	private boolean lococationPic = false;
	private boolean specimenPic = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO make it incorporate the gps stuff and display it at the start
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sighting_entry);
//		latView = (TextView) findViewById(R.id.latitudeDisplay);
//		lngView = (TextView) findViewById(R.id.longitudeDisplay);

		Bundle data = getIntent().getExtras();

		if (data != null) {
			visit = data.getParcelable("visit");
		} else {
			return;
		}

//		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//		// Selecting the location provider
//		Criteria criteria = new Criteria();
//		provider = locationManager.getBestProvider(criteria, false);
//		Location location = locationManager.getLastKnownLocation(provider);
//
//		// Initialize lcoation fields
//		if (location != null) {
//			System.out.println("Provider " + provider + " has been selected.");
//			onLocationChanged(location);
//		} else {
//			latView.setText("NA");
//			lngView.setText("NA");
//		}

	}

//	/* Request updates at startup */
//	@Override
//	protected void onResume() {
//		super.onResume();
//		locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER, 400, 1, this);
//	}
//
//	/* Remove the locationlistener updates when Activity is paused */
//	@Override
//	protected void onPause() {
//		super.onPause();
//		locationManager.removeUpdates(this);
//	}

	@Override
	public void onLocationChanged(Location location) {
//		int lat = (int) (location.getLatitude());
//		int lng = (int) (location.getLongitude());
//		latView.setText(String.valueOf(lat));
//		lngView.setText(String.valueOf(lng));
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
		Intent intent = new Intent(this, MainActivity.class);

		EditText specimenName = (EditText) findViewById(R.id.specimenName);
		String name = specimenName.getText().toString();

		Spinner daforSelected = (Spinner) findViewById(R.id.daforSelector);
		String dafor = daforSelected.getSelectedItem().toString();

		EditText descriptionDone = (EditText) findViewById(R.id.description);
		String description = descriptionDone.getText().toString();

		ImageView specimenImageTaken = (ImageView) findViewById(R.id.specimenImageDisplay);
		// this might be wrong (getting the image)
		
		Bitmap specimenImage = ((BitmapDrawable)specimenImageTaken.getDrawable()).getBitmap();
//		Bitmap specimenImage = ((BitmapDrawable)specimenImageTaken.getDrawable());

		ImageView locationImageTaken = (ImageView) findViewById(R.id.locationImageDisplay);
		// this might be wrong (getting the image)
		Bitmap locationImage = ((BitmapDrawable)locationImageTaken.getDrawable()).getBitmap();
//		Bitmap locationImage = locationImageTaken.getDrawingCache();

//		visit.addNewSighting(new Sighting(name, description, dafor, locLat, locLng, specimenImage , locationImage));
		
//		visit.addNewSighting(new Sighting(name, description, dafor, 5.6, 5.6, specimenImage , locationImage, specimenPic ,lococationPic));
//		intent.putExtra("visit", visit);
		
//		 Sighting sight = new Sighting(name, description, dafor, 5.6, 5.6, specimenImage , locationImage, specimenPic ,lococationPic);
//			intent.putExtra("visit", sight);

//		visit.addNewSighting(new Sighting(name, description, dafor, locLat, locLng, specimenImage, locationImage, specimenPic, lococationPic));
		intent.putExtra("visit", visit);

		startActivity(intent);
	}

// CAMERA /////////////////////////////////////////////////////////////////////
	
//	static final int REQUEST_IMAGE_CAPTURE = 1;
//	static final int REQUEST_IMAGE_CAPT = 2;
//	private boolean lococationPic = false;
//	private boolean specimenPic = false;
	
	//this method is for specimen picture
	public void openCamera(View view){
		specimenPic = true;
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, 1);
	}
	
	//this method is for location picture
	public void openCameraloc(View view){
		lococationPic = true;
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, REQUEST_IMAGE_CAPT);
	}

/** Never used? */
//	private void dispatchTakePictureIntent() {
//	    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//	    if(!lococationPic){
//	    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//	        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//	    }
//	    }else{
//	    	startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPT);
//	    }
//	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(!lococationPic){
		if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//	        Bundle extras = data.getExtras();
//	        Bitmap imageBitmap = (Bitmap) extras.get("data");
	        ImageView picDisplay = (ImageView) findViewById(R.id.specimenImageDisplay);
	        Bitmap imageBitmap = (Bitmap) data.getExtras().get("data"); 
	        
	        picDisplay.setImageBitmap(imageBitmap);
	        
//	        picDisplay.setImageBitmap(imageBitmap);
	    }
		}else{
			if (requestCode == REQUEST_IMAGE_CAPT && resultCode == RESULT_OK) {
			ImageView picDisp = (ImageView) findViewById(R.id.locationImageDisplay);
	        Bitmap imageBitmap = (Bitmap) data.getExtras().get("data"); 
	        picDisp.setImageBitmap(imageBitmap);
			}
		}
	    
	    
	}
	
}

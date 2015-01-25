package uk.ac.aber.rpsrrec.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import uk.ac.aber.plantcatalog.R;
import uk.ac.aber.rpsrrec.data.User;
import uk.ac.aber.rpsrrec.data.Visit;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class VisitActivity extends Activity {

	private Visit visit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_visit);

		Bundle data = getIntent().getExtras();

		User user = data.getParcelable("user");
		//String date = DateFormat.getDateTimeInstance().format(new Date());

		Date date = Calendar.getInstance().getTime();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK);
		String formattedDate = simpleDateFormat.format(date);

		visit = new Visit(user, formattedDate);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.visit, menu);
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

	/** Called when the user clicks the Start Recording button */
	public void startRecording(View view) {
		Intent intent = new Intent(this, SightingsListActivity.class);

		EditText editText = (EditText) findViewById(R.id.visit_ReserveName);
		String reserve = editText.getText().toString();

		visit.setReserve(reserve);
		intent.putExtra("visit", visit);

		startActivity(intent);
	}

}

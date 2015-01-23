package uk.ac.aber.rpsrrec.ui;

import uk.ac.aber.plantcatalog.R;
import uk.ac.aber.rpsrrec.data.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class LogOnActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_on);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.log_on, menu);
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

	/** Called when the user clicks the Log On button */
	public void userLogOn(View view) {
		Intent intent = new Intent(this, VisitActivity.class);

		EditText editText = (EditText) findViewById(R.id.logOn_UserName);
		String userName = editText.getText().toString();

		editText = (EditText) findViewById(R.id.logOn_UserPhone);
		String userPhone = editText.getText().toString();

		editText = (EditText) findViewById(R.id.logOn_UserEmail);
		String userEmail = editText.getText().toString();

		intent.putExtra("user", new User(userName, userPhone, userEmail));

		startActivity(intent);
	}

}

package uk.ac.aber.rpsrrec.ui;

import uk.ac.aber.plantcatalog.R;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LogOnFragment extends Fragment {

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_log_on, container, false);
    }

	public void userLogOn(View view) {
		
	}

	public interface LogOnFragmentListener {
		public void onLogOnClick(LogOnFragment fragment);
	}
}

package uk.ac.aber.rpsrrec.data;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;

public class SendToServer {
	private Visit visit;

	public SendToServer(Visit visit) {
		this.visit = visit;
		AsyncDownloader aD = new AsyncDownloader();
		aD.execute();
	}

	private class AsyncDownloader extends AsyncTask<Object, String, Integer> {

		@Override
		protected Integer doInBackground(Object... papams) {
			sendData(visit);

			return 1;
		}

		private final static String SERVER_URL = "http://users.aber.ac.uk/pjn/plantcatalog/include/php/fetch_data.php";
		private final static String USER_AGENT = "Mozilla/5.0";

		public boolean sendData(Visit visit) {
			HttpClient httpclient = new DefaultHttpClient();

			int len = visit.getSightings().size();
			for (int i = 0; i < len; i++) {
				sendUserData(visit, httpclient, i);
			}

			return true;
		}

		private boolean sendUserData(Visit visit, HttpClient client, int sighting) {

			HttpPost httppost = new HttpPost(SERVER_URL);
			httppost.setHeader("user agent", USER_AGENT);

			List<NameValuePair> use = new ArrayList<NameValuePair>();

			use.add(new BasicNameValuePair("Name", visit.getUserName()));
			use.add(new BasicNameValuePair("Email", visit.getUserEmail()));
			use.add(new BasicNameValuePair("Phone", visit.getUserPhone()));
			use.add(new BasicNameValuePair("reserveName", visit.getReserveName()));
			use.add(new BasicNameValuePair("Date", visit.getDate()));
			use.add(new BasicNameValuePair("speciesName", visit.getSightings().get(sighting).getSpecies()));
			use.add(new BasicNameValuePair("description", visit.getSightings().get(sighting).getDescription()));
			use.add(new BasicNameValuePair("abundance", visit.getSightings().get(sighting).getAbundance()));
			use.add(new BasicNameValuePair("latitude", String.valueOf(visit.getSightings().get(sighting).getLat())));
			use.add(new BasicNameValuePair("longitude", String.valueOf(visit.getSightings().get(sighting).getLng())));

			try {
				httppost.setEntity(new UrlEncodedFormEntity(use));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			return sendFinalData(client, httppost);
		}

		private boolean sendFinalData(HttpClient client, HttpPost httppost) {
			HttpResponse httpResponse = null;

			try {
				httpResponse = client.execute(httppost);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}

			int responseId = httpResponse.getStatusLine().getStatusCode();
			if (responseId == 200) {
				return true;
			} else {
				return false;
			}
		}

		private HttpPost createNewPost() {
			HttpPost httppost = new HttpPost(SERVER_URL);
			httppost.setHeader("user agent", USER_AGENT);
			return httppost;
		}
	}
}

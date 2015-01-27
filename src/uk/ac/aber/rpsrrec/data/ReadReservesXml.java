package uk.ac.aber.rpsrrec.data;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.res.XmlResourceParser;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;


public class ReadReservesXml {
	private ArrayList<String> reserves = new ArrayList<String>();

	public String[] readReserves(){
		AsyncDownloader aD = new AsyncDownloader();
		aD.execute();
		
		return autoComplete(reserves);
		
	}
	public ArrayList<String> getArray(){
		return reserves;
	}
	
	public void SetArray(ArrayList<String> finishedArray){
		reserves = finishedArray;
	}
	
	private class AsyncDownloader extends AsyncTask<Object, String, Integer> {

		@Override
		protected Integer doInBackground(Object... params) {
			XmlPullParser recievedData = tryDownloadingXmlData();
			int recordsFound = tryParsingXmlData(recievedData);
			return recordsFound;
		}

		private XmlPullParser tryDownloadingXmlData() {
			try {
				URL xmlUrl = new URL(
						"http://users.aber.ac.uk/pjn/plantcatalog/reserves/xml.php");
				XmlPullParser recievedData = XmlPullParserFactory.newInstance()
						.newPullParser();
				recievedData.setInput(xmlUrl.openStream(), null);
				return recievedData;
			} catch (Exception e) {
			}
			return null;
		}

		private int tryParsingXmlData(XmlPullParser recievedData) {
			if (recievedData != null) {
				try {
					return processRecievedData(recievedData);
				} catch (Exception e) {

				}
			}
			return 0;
		}
		private ArrayList<String> resArray; 
		//private ArrayList<String> reserves = new ArrayList<String>();
		private int processRecievedData(XmlPullParser recievedData)
				throws XmlPullParserException, IOException {
			int eventType = -1;
			int recordsFound = 0;
			String name = "";
			String data = "";
			
			while (eventType != XmlResourceParser.END_DOCUMENT) {
				String tagName = recievedData.getName();

				switch (eventType) {
				case XmlResourceParser.START_TAG:

					if (tagName.equals("reserves")) {
						tagName = recievedData.getName();
					}
					if (tagName.equals("reserve")) {
						tagName = recievedData.getName();
					}
					if (tagName.equals("rsv_name")) {

						if (recievedData.next() == XmlPullParser.TEXT) {
							resArray = getArray();
							name =  recievedData.getText();
							resArray.add(name);
							data += name;
						}

					}

					break;

				case XmlResourceParser.END_TAG:
					
					publishProgress(data);
					
					break;
				}
				try {
					eventType = recievedData.next();
				} catch (Exception e) {

				}
			}
			if (recordsFound == 0) {
				publishProgress();
			}

			return recordsFound;
		}

		@Override
		protected void onProgressUpdate(String... values) {
			if (values.length == 0) {
				// no data
			}
			if (values.length == 1) {
				SetArray(resArray);
			}
			super.onProgressUpdate(values);
		}

	}
	
	public String[] autoComplete(ArrayList<String> res){
		String [] hello = res.toArray(new String[res.size()]);
	
		 
//		AutoCompleteTextView auto = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
//		ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1 ,hello);
//		auto.setThreshold(1);
//		auto.setAdapter(adapter);
		return hello;
		}
}

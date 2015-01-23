package uk.ac.aber.rpsrrec.data;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

public class Sighting implements Parcelable {

	// location from android device
	private double locLat;
	private double locLng;
/*
	// image of the specimen
	private Bitmap specimenImage;

	// image of the location
	private Bitmap specimenLocationImage;
*/
	// string for the description field
	private String description;

	// String for abundance
	private String abundance;

	// string for plant name
	private String name;

	public double getLat() {
		return locLat;
	}

	public double getLng() {
		return locLng;
	}
/*
	public Bitmap getSpecimenImage() {
		return specimenImage;
	}

	public Bitmap getSpecimenLocationImage() {
		return specimenLocationImage;
	}
*/
	public String getDescription() {
		return description;
	}

	public String getAbundance() {
		return abundance;
	}

	public String getName() {
		return name;
	}

	public Sighting(String name, String description, String dafor,
			double locLat, double locLng/*, Bitmap specimen, Bitmap specimenLocation*/) {

		this.name = name;
		assignDaforToAbundance(dafor);
		this.locLat = locLat;
		this.locLng = locLng;
		this.description = description;

/*
		if (specimen != null) {
			this.specimenImage = specimen;
		}

		if (specimenLocation != null) {
			this.specimenLocationImage = specimenLocation;
		}
*/
	}

	private void assignDaforToAbundance(String dafor) {

		if (dafor.equals("D(Dominant)")) {
			abundance = "Dominant";
		} else if (dafor.equals("A(Abundant)")) {
			abundance = "Abundant";
		} else if (dafor.equals("F(Frequent)")) {
			abundance = "Frequent";
		} else if (dafor.equals("O(Occasional)")) {
			abundance = "Occasional";
		} else if (dafor.equals("R(Rare)")) {
			abundance = "Rare";
		}
	}
	
	public Sighting(Parcel in) {
/*
		specimenImage = Bitmap.CREATOR.createFromParcel(in);
		specimenLocationImage = Bitmap.CREATOR.createFromParcel(in);

		location = Location.CREATOR.createFromParcel(in);
*/
		locLat = in.readDouble();
		locLng = in.readDouble();
		abundance = in.readString();
		description = in.readString();
		name = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// we used dest as a parcel for the next three instead of a seperate
		// thing
		// we might get null pointer if speciminImage is null, Change here if
		// happens!!!
/*
		specimenImage.writeToParcel(dest, flags);
		specimenLocationImage.writeToParcel(dest, flags);
*/

		dest.writeDouble(locLat);
		dest.writeDouble(locLng);
		dest.writeString(abundance);
		dest.writeString(description);
		dest.writeString(name);
	}
	
	public static final Parcelable.Creator<Sighting> CREATOR = new Parcelable.Creator<Sighting>() {
		public Sighting createFromParcel(Parcel in) {
			return new Sighting(in);
		}

		public Sighting[] newArray(int size) {
			return new Sighting[size];
		}
	};
}
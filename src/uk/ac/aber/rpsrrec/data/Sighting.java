package uk.ac.aber.rpsrrec.data;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Sighting implements Parcelable {

	// location from android device
	private double locLat;
	private double locLng;

	private int specimenPictureState = 0;
	private int locationPictureState = 0;

	
	ByteArrayOutputStream compressSpecimen = new ByteArrayOutputStream();
	
	
	// image of the specimen
	private Bitmap specimenImage;

	// image of the location
	private Bitmap specimenLocationImage;

	// string for the description field
	private String description;

	// String for abundance
	private String abundance;

	// string for plant name
	private String name;

	// Getters & Setters //////////////////////////////////////////////////////

	public double getLat() {
		return locLat;
	}

	public double getLng() {
		return locLng;
	}

	/*
	 * public Bitmap getSpecimenImage() { return specimenImage; }
	 * 
	 * public Bitmap getSpecimenLocationImage() { return specimenLocationImage;
	 * }
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

	public Bitmap getBitmap(){
		return specimenImage;
	}
	
	public Bitmap getBitmaploc(){
		return specimenLocationImage;
	}
	public Sighting(String name, String description, String dafor,
			double locLat, double locLng, Bitmap specimen,
			Bitmap specimenLocation, boolean specimenState,
			boolean locationState) {

		this.name = name;
		assignDaforToAbundance(dafor);
		this.locLat = locLat;
		this.locLng = locLng;
		this.description = description;
		if(specimenState){
			specimenPictureState = 1;
		}
		
		if(locationState){
			locationPictureState = 1;
		}
//		this.specimenPictureState = specimenState;
//		this.locationPictureState = locationState;

		if (specimenPictureState == 1) {
			this.specimenImage = specimen;
//			this.specimenImage.compress(Bitmap.CompressFormat.PNG, 50, compressSpecimen);
		}

		if (locationPictureState == 1) {
			this.specimenLocationImage = specimenLocation;
		}

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
		 * specimenImage = Bitmap.CREATOR.createFromParcel(in);
		 * specimenLocationImage = Bitmap.CREATOR.createFromParcel(in);
		 * 
		 * location = Location.CREATOR.createFromParcel(in);
		 */
		specimenPictureState = in.readInt();
		locationPictureState = in.readInt();
		
		if (specimenPictureState == 1) {

			specimenImage = in.readParcelable(Bitmap.class.getClassLoader());
		}

		if (locationPictureState == 1) {
			
			specimenLocationImage = in.readParcelable(Bitmap.class.getClassLoader());
		}
		
		locLat = in.readDouble();
		locLng = in.readDouble();
		abundance = in.readString();
		description = in.readString();
		name = in.readString();
//		specimenPictureState = in.readInt();
//		locationPictureState = in.readInt();
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
		 * specimenImage.writeToParcel(dest, flags);
		 * specimenLocationImage.writeToParcel(dest, flags);
		 */
		
		dest.writeInt(specimenPictureState);
		dest.writeInt(locationPictureState);
		
		if (specimenPictureState == 1) {
			dest.writeParcelable(specimenImage, 1);		
		}
//
		if (locationPictureState == 1) {
			dest.writeParcelable(specimenLocationImage, 1);
		}
		
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

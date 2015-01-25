package uk.ac.aber.rpsrrec.data;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Sighting implements Parcelable {

	private String species;
	private String abundance;
	private String description;

	private double locLat;
	private double locLng;

	private Bitmap specimenPicture;
	private Bitmap locationPicture;
	private int specimenPictureState;
	private int locationPictureState;

	public Sighting(String species, String dafor, String description, double locLat, double locLng,
			Bitmap specimenPicture, Bitmap locationPicture,
			boolean specimenPictureState, boolean locationPictureState) {

		this.species = species;
		assignDaforToAbundance(dafor);
		this.description = description;
		this.locLat = locLat;
		this.locLng = locLng;

		if(specimenPictureState) {
			this.specimenPictureState = 1;
			this.specimenPicture = specimenPicture;
		}

		if(locationPictureState){
			this.locationPictureState = 1;
			this.locationPicture = locationPicture;
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

	public String toString() {
		return species + " " + description;
	}

// Getters & Setters //////////////////////////////////////////////////////////

	public double getLat() { return locLat; }

	public double getLng() { return locLng; }

	public String getDescription() { return description; }

	public String getAbundance() { return abundance; }

	public String getSpecies() { return species; }

	public Bitmap getSpecimenPicture(){ return specimenPicture; }
	
	public Bitmap getLocationPicture(){ return locationPicture; }

// Parcelable /////////////////////////////////////////////////////////////////

	public Sighting(Parcel in) {

		species = in.readString();
		abundance = in.readString();
		description = in.readString();
		locLat = in.readDouble();
		locLng = in.readDouble();

		specimenPictureState = in.readInt();
		locationPictureState = in.readInt();

		if (specimenPictureState == 1) {
			specimenPicture = in.readParcelable(Bitmap.class.getClassLoader());
		}

		if (locationPictureState == 1) {
			locationPicture = in.readParcelable(Bitmap.class.getClassLoader());
		}
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeString(species);
		dest.writeString(abundance);
		dest.writeString(description);
		dest.writeDouble(locLat);
		dest.writeDouble(locLng);

		dest.writeInt(specimenPictureState);
		dest.writeInt(locationPictureState);

		if (specimenPictureState == 1) {
			dest.writeParcelable(specimenPicture, 1);		
		}

		if (locationPictureState == 1) {
			dest.writeParcelable(locationPicture, 1);
		}
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

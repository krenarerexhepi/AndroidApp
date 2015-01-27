package uk.ac.aber.rpsrrec.data;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Visit implements Parcelable {

	private User user;
	private String reserve;
	private String date;
	private ArrayList<Sighting> sightings;

	public Visit() {
		sightings = new ArrayList<Sighting>();
	}

	public Visit(User user, String date) {
		this.user = user;
		this.date = date;
		sightings = new ArrayList<Sighting>();
	}

	public void addNewSighting(Sighting newSighting) {
		sightings.add(newSighting);
	}

// Getters & Setters //////////////////////////////////////////////////////////

	public void setReserve(String reserve) { this.reserve = reserve; }

	public void setUser(User user) { this.user = user; }

	public void setDate(String date) { this.date = date; }

	public User getUser() { return user; }

	public String getUserName() { return user.getName(); }

	public String getUserPhone() { return user.getPhone(); }

	public String getUserEmail() { return user.getEmail(); }

	public String getReserveName() { return reserve; }

	public String getDate() { return date; }

	public ArrayList<Sighting> getSightings() { return sightings; }

// Parcelable /////////////////////////////////////////////////////////////////

	private Visit(Parcel in) {
		user = in.readParcelable(getClass().getClassLoader());
		reserve = in.readString();
		date = in.readString();
		if (sightings == null)
			sightings = new ArrayList<Sighting>();
		in.readTypedList(sightings, Sighting.CREATOR);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(user, flags);
		dest.writeString(reserve);
		dest.writeString(date);
		dest.writeTypedList(sightings);
	}

	public static final Parcelable.Creator<Visit> CREATOR = new Parcelable.Creator<Visit>() {
		public Visit createFromParcel(Parcel in) {
			return new Visit(in);
		}

		public Visit[] newArray(int size) {
			return new Visit[size];
		}
	};

}

package uk.ac.aber.rpsrrec.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Visit implements Parcelable {

	private User user;
	private String reserve;
	private String date;

	public Visit(User user, String date) {
		this.user = user;
		this.date = date;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

	public String getUserName() { return user.getName(); }

	public String getUserPhone() { return user.getPhone(); }

	public String getUserEmail() { return user.getEmail(); }

	public String getReserveName() { return reserve; }

	public String getDate() { return date; }

	// Parcelable /////////////////////////////////////////////////////////////

	private Visit(Parcel in) {
		user = in.readParcelable(getClass().getClassLoader());
		reserve = in.readString();
		date = in.readString();
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

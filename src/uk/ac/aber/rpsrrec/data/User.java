package uk.ac.aber.rpsrrec.data;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

	private String name;
	private String phone;
	private String email;

	public User(String name, String phone, String email) {
		this.name = name;
		this.phone = phone;
		this.email = email;
	}

// Getters & Setters //////////////////////////////////////////////////////////

	public String getName() { return name; }

	public String getPhone() { return phone; }

	public String getEmail() { return email; }

// Parcelable /////////////////////////////////////////////////////////////////

	private User(Parcel in) {
		name = in.readString();
		phone = in.readString();
		email = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(phone);
		dest.writeString(email);
	}

	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
		public User createFromParcel(Parcel in) {
			return new User(in);
		}

		public User[] newArray(int size) {
			return new User[size];
		}
	};

}

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

	public User(Parcel in) {
		String[] data = new String[3];

		in.readStringArray(data);
		this.name = data[0];
		this.phone = data[1];
		this.email = data[2];
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeStringArray(new String[] {this.name, this.phone, this.email});
	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public User createFromParcel(Parcel in) {
			return new User(in);
		}

		public User[] newArray(int size) {
			return new User[size];
		}
	};

	public String getName() {
		return name;
	}

}

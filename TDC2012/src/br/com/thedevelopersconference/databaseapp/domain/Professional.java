package br.com.thedevelopersconference.databaseapp.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The author of a speech.
 * 
 * @author pedrobrigatto
 */
public class Professional implements Parcelable {

	public static final int ROLE_DEVELOPER = 0;
	public static final int ROLE_ARCHITECT = 1;

	private String code;
	private String name;
	private int role;

	public static final Parcelable.Creator<Professional> CREATOR = 
			new Parcelable.Creator<Professional>() {

		@Override
		public Professional createFromParcel(Parcel source) {
			return new Professional(source);
		}

		@Override
		public Professional[] newArray(int size) {
			return new Professional[size];
		}
	};

	public Professional(String name, int role) {
		this.name = name;
		this.role = role;
	}

	public Professional (Parcel parcel) {
		readFromParcel(parcel);
	}	

	public Professional(String name) {
		this.name = name;
	}

	public void setCode (String code) {
		this.code = code;
	}

	public String getCode () {
		return this.code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(code);
		dest.writeString(name);
		dest.writeInt(role);
	}
	
	private void readFromParcel(Parcel parcel) {
		this.code = parcel.readString();
		this.name = parcel.readString();
		this.role = parcel.readInt();
	}
}

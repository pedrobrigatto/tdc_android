package br.com.thedevelopersconference.databaseapp.domain;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * A speech to be presented on TDC 2012.
 * 
 * @author pedrobrigatto
 */
public class Speech implements Parcelable {

	private String title;
	private String description;
	private Professional author;
	private String when;

	public static final Parcelable.Creator<Speech> CREATOR = 
			new Parcelable.Creator<Speech>() {

		@Override
		public Speech createFromParcel(Parcel source) {
			return new Speech(source);
		}

		@Override
		public Speech[] newArray(int size) {
			return new Speech[size];
		}
	};

	public Speech() {}

	public Speech(String title) {
		this.title = title;
	}

	public Speech (Parcel parcel) {
		readFromParcel(parcel);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Professional getAuthor() {
		return author;
	}

	public void setAuthor(Professional author) {
		this.author = author;
	}

	public String getWhen() {
		return when;
	}

	public void setWhen(String when) {
		this.when = when;
	}

	// ----------- Parcelable API ------------------

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		dest.writeString(title);
		dest.writeString(description);
		dest.writeParcelable(author, flags);
		dest.writeString(when);
	}

	private void readFromParcel(Parcel parcel) {
		this.title = parcel.readString();
		this.description = parcel.readString();
		this.author = parcel.readParcelable(Professional.class.getClassLoader());
		this.when = parcel.readString();
	}
}

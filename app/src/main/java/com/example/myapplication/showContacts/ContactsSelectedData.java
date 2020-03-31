package com.example.myapplication.showContacts;

import android.os.Parcel;
import android.os.Parcelable;

public class ContactsSelectedData  implements Parcelable {

   private String contactName;
   private String contactNumber;
   private boolean isChecked;
  public ContactsSelectedData(Parcel in) {
    contactName = in.readString();
    contactNumber = in.readString();
  }

  public static final Creator<ContactsSelectedData> CREATOR = new Creator<ContactsSelectedData>() {
    @Override
    public ContactsSelectedData createFromParcel(Parcel in) {
      return new ContactsSelectedData(in);
    }

    @Override
    public ContactsSelectedData[] newArray(int size) {
      return new ContactsSelectedData[size];
    }
  };

  public ContactsSelectedData() {

  }

  public String getContactName() {
    return contactName;
  }

  public void setContactName(String contactName) {
    this.contactName = contactName;
  }

  public String getContactNumber() {
    return contactNumber;
  }

  public void setContactNumber(String contactNumber) {
    this.contactNumber = contactNumber;
  }

  public boolean isChecked() {
    return isChecked;
  }

  public void setChecked(boolean checked) {
    isChecked = checked;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(contactName);
    dest.writeString(contactNumber);
  }
}

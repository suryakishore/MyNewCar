package com.example.myapplication.showContacts;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * view model class for show contacts
 */
public class ShowContactsViewModel extends ViewModel {
  private MutableLiveData<Boolean> mAddContact = new MutableLiveData<>();

  /**
   * listens change addContact clicked.
   */
  public void addContacts() {
    mAddContact.postValue(true);
  }

  /**
   * notify when add contact clicked.
   */
  public MutableLiveData<Boolean> getContactsData() {
    return mAddContact;
  }
}

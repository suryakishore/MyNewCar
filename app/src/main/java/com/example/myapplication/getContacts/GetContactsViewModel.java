package com.example.myapplication.getContacts;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.myapplication.showContacts.ContactsSelectedData;
import java.util.ArrayList;

/**
 * view model class for get contacts
 */
public class GetContactsViewModel extends ViewModel {
  private MutableLiveData<Boolean> mSubMitContacts = new MutableLiveData<>();

  /**
   * listens submit contacts clicked.
   */
  public void submitContacts() {
    mSubMitContacts.postValue(true);
  }

  /**
   * notify when  submit data clicked
   */
  public MutableLiveData<Boolean> submitData() {
    return mSubMitContacts;
  }

  /**
   * get contacts data from phone by using context
   * @param context context
   * @return it will return the contacts data
   */
  public ArrayList<ContactsSelectedData> getContactsData(Context context) {
    Log.d("exe", "context" + context);
    ArrayList<ContactsSelectedData> nameList = new ArrayList<>();
    ContentResolver cr = context.getContentResolver();
    Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
        null, null, null, null);
    if (cur != null && cur.getCount() != 0) {
      while (cur != null && cur.moveToNext()) {
        ContactsSelectedData contactsSelectedData = new ContactsSelectedData();
        String id = cur.getString(
            cur.getColumnIndex(ContactsContract.Contacts._ID));
        String name = cur.getString(cur.getColumnIndex(
            ContactsContract.Contacts.DISPLAY_NAME));
        contactsSelectedData.setContactName(name);
        if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
          Cursor pCur = cr.query(
              ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
              null,
              ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
              new String[]{id}, null);
          while (pCur.moveToNext()) {
            String phoneNo = pCur.getString(pCur.getColumnIndex(
                ContactsContract.CommonDataKinds.Phone.NUMBER));
            contactsSelectedData.setContactNumber(phoneNo);
          }
          pCur.close();
        }
        nameList.add(contactsSelectedData);
      }
    }
    if (cur != null) {
      cur.close();
    }
    return nameList;
  }
}

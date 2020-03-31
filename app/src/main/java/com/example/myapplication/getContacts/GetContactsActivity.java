package com.example.myapplication.getContacts;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityGetContactsBinding;
import com.example.myapplication.showContacts.ContactsSelectedData;
import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * used for get contacts from device.
 */
public class GetContactsActivity extends AppCompatActivity {
  public static final int REQUEST_READ_CONTACTS = 10;
  private ActivityGetContactsBinding mBinding;
  private GetContactsAdapter mGetContactsAdapter;
  private ArrayList<ContactsSelectedData> mContactsSelectedData = new ArrayList<>();
  private GetContactsViewModel mGetContactsViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initializeView();
    initializeViewModel();
    contactsFromDevice();
    subscribeSubmitData();
  }

  /**
   * initialize view
   */
  private void initializeView() {
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_get_contacts);
  }

  /**
   * this method is used to initialize for viewModel
   */
  private void initializeViewModel() {
    mGetContactsViewModel = ViewModelProviders.of(this).get(GetContactsViewModel.class);
    mBinding.setViewModel(mGetContactsViewModel);
    mGetContactsAdapter = new GetContactsAdapter(mContactsSelectedData);
    mBinding.rvContactsList.setAdapter(mGetContactsAdapter);
  }

  /**
   * get contact from device after permission accepted.
   */
  private void contactsFromDevice() {
    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
        == PackageManager.PERMISSION_GRANTED) {
      mContactsSelectedData.addAll(getAllContacts());
      mGetContactsAdapter.notifyDataSetChanged();
    } else {
      requestPermission();
    }
  }

  private void subscribeSubmitData() {
    mGetContactsViewModel.submitData().observe(this, new Observer<Boolean>() {
      @Override
      public void onChanged(Boolean aBoolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
          Predicate<ContactsSelectedData> condition =
              new Predicate<ContactsSelectedData>() {
                @Override
                public boolean test(ContactsSelectedData contactsSelectedData) {
                  return contactsSelectedData.isChecked() == false;
                }
              };
          mContactsSelectedData.removeIf(condition);
          Intent intent = new Intent();
          intent.putExtra("Data", mContactsSelectedData);
          setResult(Activity.RESULT_OK, intent);
          finish();
        }
      }
    });
  }

  private ArrayList<ContactsSelectedData> getAllContacts() {
    return mGetContactsViewModel.getContactsData(this);
  }

  private void requestPermission() {
    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
        android.Manifest.permission.READ_CONTACTS)) {
      // show UI part if you want here to show some rationale !!!
    } else {
      ActivityCompat.requestPermissions(this,
          new String[]{android.Manifest.permission.READ_CONTACTS},
          REQUEST_READ_CONTACTS);
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode,
      String permissions[], int[] grantResults) {
    switch (requestCode) {
      case REQUEST_READ_CONTACTS: {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          mContactsSelectedData.addAll(getAllContacts());
          mGetContactsAdapter.notifyDataSetChanged();
        } else {
          Toast.makeText(this, getResources().getString(R.string.permissionError),
              Toast.LENGTH_LONG);
        }
        return;
      }
    }
  }
}

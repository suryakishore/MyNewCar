package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.getContacts.GetContactsActivity;
import com.example.myapplication.showContacts.ContactsSelectedData;
import com.example.myapplication.showContacts.ShowContactsAdapter;
import com.example.myapplication.showContacts.ShowContactsViewModel;
import java.util.ArrayList;

/**
 * this will show the all selected contacts
 */
public class MainActivity extends AppCompatActivity {
  private ArrayList<ContactsSelectedData> mContactsSelectedData = new ArrayList<>();
  private ShowContactsAdapter mShowContactsAdapter;
  private ActivityMainBinding mBinding;
  private ShowContactsViewModel mShowContactsViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initializeView();
    initializeViewModel();
    subscribeToAddContactsData();
  }

  /**
   * initialize view
   */
  private void initializeView() {
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
  }

  /**
   * this method is used to initialize for viewModel
   */
  private void initializeViewModel() {
    mShowContactsViewModel = ViewModelProviders.of(this).get(ShowContactsViewModel.class);
    mBinding.setViewModel(mShowContactsViewModel);
    mShowContactsAdapter = new ShowContactsAdapter(mContactsSelectedData);
    mBinding.rvSelectedContactsList.setAdapter(mShowContactsAdapter);
  }

  /**
   * subscribe to contacts data
   */
  private void subscribeToAddContactsData() {
    mShowContactsViewModel.getContactsData().observe(this, new Observer<Boolean>() {
      @Override
      public void onChanged(Boolean aBoolean) {
        Intent intent = new Intent(MainActivity.this, GetContactsActivity.class);
        startActivityForResult(intent, 100);
      }
    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (data != null) {
      if (requestCode == 100) {
        if (resultCode == Activity.RESULT_OK) {
          ArrayList<ContactsSelectedData> contactsSelectedData = data.getParcelableArrayListExtra("Data");
          mContactsSelectedData.clear();
          mContactsSelectedData.addAll(contactsSelectedData);
          mShowContactsAdapter.notifyDataSetChanged();
          if (mContactsSelectedData.size() > 0) {
            mBinding.noContacts.setVisibility(
                mContactsSelectedData.size() > 0 ? View.GONE : View.VISIBLE);
          }
        }
      }
    }
  }
}

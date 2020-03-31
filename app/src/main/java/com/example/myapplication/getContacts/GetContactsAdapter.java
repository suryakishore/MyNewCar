package com.example.myapplication.getContacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ItemGetContactBinding;
import com.example.myapplication.showContacts.ContactsSelectedData;
import java.util.ArrayList;

/**
 * adapter class for the get all contacts
 */
public class GetContactsAdapter extends
    RecyclerView.Adapter<GetContactsAdapter.ViewHolder> {
  private ArrayList<ContactsSelectedData> mContactsSelectedData;
  private Context mContext;

  public GetContactsAdapter(ArrayList<ContactsSelectedData> contactsSelectedData) {
    mContactsSelectedData = contactsSelectedData;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    mContext = parent.getContext();
    ItemGetContactBinding binding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_get_contact, parent, false);
    return new ViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
    final ContactsSelectedData contactsSelectedData = mContactsSelectedData.get(position);
    if (contactsSelectedData != null) {
      holder.mBinding.cbSelectContact.setChecked(contactsSelectedData.isChecked());
      holder.mBinding.tvContactName.setText(
          String.format("%s:%s", mContext.getResources().getString(R.string.name),
              contactsSelectedData.getContactName()));
      holder.mBinding.tvContactNumber.setText(
          String.format("%s:%s", mContext.getResources().getString(R.string.number),
              contactsSelectedData.getContactNumber()));
      holder.mBinding.clGetContacts.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (contactsSelectedData.isChecked()) {
            contactsSelectedData.setChecked(false);
            holder.mBinding.cbSelectContact.setChecked(false);
          } else {
            contactsSelectedData.setChecked(true);
            holder.mBinding.cbSelectContact.setChecked(true);
          }
        }
      });
    }
  }

  @Override
  public int getItemCount() {
    return mContactsSelectedData.size();
  }

  /**
   * view holder class highlights
   */
  class ViewHolder extends RecyclerView.ViewHolder {
    ItemGetContactBinding mBinding;

    ViewHolder(@NonNull ItemGetContactBinding binding) {
      super(binding.getRoot());
      this.mBinding = binding;
    }
  }
}
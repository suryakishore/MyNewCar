package com.example.myapplication.showContacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ItemSelectedContactBinding;
import java.util.ArrayList;

/**
 * adapter class for the show contacts
 */
public class ShowContactsAdapter extends
    RecyclerView.Adapter<ShowContactsAdapter.ViewHolder> {
  private ArrayList<ContactsSelectedData> mContactsSelectedData;
  private Context mContext;

  public ShowContactsAdapter(ArrayList<ContactsSelectedData> contactsSelectedData) {
    mContactsSelectedData = contactsSelectedData;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    mContext = parent.getContext();
    ItemSelectedContactBinding binding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_selected_contact, parent, false);
    return new ViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    ContactsSelectedData contactsSelectedData = mContactsSelectedData.get(position);
    if (contactsSelectedData != null) {
      holder.mBinding.tvContactName.setText(
          String.format("%s:%s", mContext.getResources().getString(R.string.name),
              contactsSelectedData.getContactName()));
      holder.mBinding.tvContactNumber.setText(
          String.format("%s:%s", mContext.getResources().getString(R.string.number),
              contactsSelectedData.getContactNumber()));
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
    ItemSelectedContactBinding mBinding;

    ViewHolder(@NonNull ItemSelectedContactBinding binding) {
      super(binding.getRoot());
      this.mBinding = binding;
    }
  }
}
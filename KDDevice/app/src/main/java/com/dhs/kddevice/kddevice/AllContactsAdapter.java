package com.dhs.kddevice.kddevice;

/**
 * Created by Chinnaraj on 1/16/2018.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.dhs.kddevice.kddevice.util.PreferenceHolder;
import com.dhs.kddevice.kddevice.vo.ContactVO;

import java.util.List;

public class AllContactsAdapter extends RecyclerView.Adapter<AllContactsAdapter.ContactViewHolder> {

    PreferenceHolder contactPref;
    private List<ContactVO> contactVOList;
    private Context mContext;

    public AllContactsAdapter(List<ContactVO> contactVOList, Context mContext) {
        contactPref = PreferenceHolder.getInstance();
        this.contactVOList = contactVOList;
        this.mContext = mContext;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_contact_view, null);
        ContactViewHolder contactViewHolder = new ContactViewHolder(view);
        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        final ContactVO contactVO = contactVOList.get(position);
        holder.tvContactName.setText(contactVO.getContactName());
        holder.tvPhoneNumber.setText(contactVO.getContactNumber());
        holder.chkContactBox.setId(position);
        holder.chkContactBox.setChecked(contactPref.hasContact(contactVO.getContactNumber()));

        holder.chkContactBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactVO vo = contactVOList.get(view.getId());
                if (((CheckBox)view).isChecked()) {
                    contactPref.addContact(vo.getContactNumber());
                } else {
                    contactPref.removeContact(vo.getContactNumber());
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return contactVOList.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        //        ImageView ivContactImage;
        CheckBox chkContactBox;
        TextView tvContactName;
        TextView tvPhoneNumber;

        public ContactViewHolder(View itemView) {
            super(itemView);
//            ivContactImage = (ImageView) itemView.findViewById(R.id.ivContactImage);
            chkContactBox = (CheckBox) itemView.findViewById(R.id.chkContact);
            tvContactName = (TextView) itemView.findViewById(R.id.tvContactName);
            tvPhoneNumber = (TextView) itemView.findViewById(R.id.tvPhoneNumber);
        }
    }
}
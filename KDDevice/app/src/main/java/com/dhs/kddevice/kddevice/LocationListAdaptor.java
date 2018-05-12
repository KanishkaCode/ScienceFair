package com.dhs.kddevice.kddevice;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dhs.kddevice.kddevice.util.PreferenceHolder;
import com.dhs.kddevice.kddevice.vo.LocationVO;

/**
 * Created by Chinnaraj on 3/14/2018.
 */

public class LocationListAdaptor extends RecyclerView.Adapter<LocationListAdaptor.LocationViewHolder> {

    private Context mContext;

    public LocationListAdaptor(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public LocationListAdaptor.LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_single_location_view, null);
        LocationListAdaptor.LocationViewHolder locationViewHolder = new LocationListAdaptor.LocationViewHolder(view);
        return locationViewHolder;
    }

    @Override
    public void onBindViewHolder(LocationListAdaptor.LocationViewHolder holder, int position) {
        final LocationVO locationVO = PreferenceHolder.getInstance().getLocationsList().get(position);
        holder.btnLocationTxt.setText(locationVO.getName());
        Log.e("location Name",locationVO.getName());
        //put a time delay above the .setText


        holder.btnLocationTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = "http://maps.google.com/maps?daddr=" + locationVO.getLattitude() + "," + locationVO.getLongitude();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        PreferenceHolder holder = PreferenceHolder.getInstance();
        return (holder.getLocationsList() == null ? 5 : holder.getLocationsList().size());
    }


    public static class LocationViewHolder extends RecyclerView.ViewHolder {
        Button btnLocationTxt;

        public LocationViewHolder(View itemView) {
            super(itemView);
            btnLocationTxt = (Button) itemView.findViewById(R.id.btnLocationTxt);
        }
    }
}
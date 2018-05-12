package com.dhs.kddevice.kddevice.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;

/**
 * Created by Chinnaraj on 1/2/2018.
 */

public class PermissionUtil {


    /**
     * Check that all given permissions have been granted by verifying that each entry in the
     * given array is of the value {@link PackageManager#PERMISSION_GRANTED}.
     */

    public static final int REQUEST_LOCATION = 0;

    public static boolean verifyLocationPermissions(Context context) {

        boolean hasPermission = true;
        ArrayList<String> permissionList = new ArrayList<>();
        PermissionUtil permissionUtils = new PermissionUtil();

        permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        if (context != null && permissionList != null) {
            for (String permission : permissionList) {
                int accessPermission = ContextCompat.checkSelfPermission(context, permission);
                hasPermission = (hasPermission & (accessPermission == PackageManager.PERMISSION_GRANTED));
            }
            return hasPermission;
        } else {
            return false;
        }
    }

    /*public static boolean requestLocationPermission(AppCompatActivity activity) {
        boolean shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(activity,  Manifest.permission.ACCESS_COARSE_LOCATION);
        shouldProvideRationale = shouldProvideRationale & (ActivityCompat.shouldShowRequestPermissionRationale(activity,  Manifest.permission.ACCESS_FINE_LOCATION));
        return true;
    }*/

    public static void requestLocationPermission(Activity mActivity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION)) {
            ActivityCompat.requestPermissions(mActivity,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        } else {
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        }

    }

    public static boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if (grantResults.length < 1) {
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

}

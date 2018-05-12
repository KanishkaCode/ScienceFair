package com.dhs.kddevice.kddevice.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.dhs.kddevice.kddevice.vo.LocationVO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Chinnaraj on 1/16/2018.
 */

public class PreferenceHolder {

    private static PreferenceHolder _instance;
    private Set<String> contactPreferences;
    private boolean contactsModified = false;
    private List<LocationVO> locationsList;

    private PreferenceHolder() {
    }

    public static PreferenceHolder getInstance() {
        if (_instance == null) {
            _instance = new PreferenceHolder();
            _instance.setContactPreferences(new HashSet<String>());
        }
        return _instance;
    }

    public static PreferenceHolder getInstance(Set<String> contactsList) {
        if (_instance == null) {
            _instance = new PreferenceHolder();
            _instance.setContactPreferences(contactsList);
        }
        return _instance;
    }


    public void addContact(String phoneNumber) {
        if (!contactPreferences.contains(phoneNumber)) {
            contactPreferences.add(phoneNumber);
            contactsModified = true;
        }
    }

    public void removeContact(String phoneNumber) {
        if (contactPreferences.contains(phoneNumber)) {
            contactPreferences.remove(phoneNumber);
            contactsModified = true;
        }
    }

    public boolean isContactsModified() {
        return contactsModified;
    }

    public Set<String> getContactPreferences() {
        return contactPreferences;
    }

    public void setContactPreferences(Set<String> contactPreferences) {
        this.contactPreferences = contactPreferences;
    }

    public boolean hasContact(String phoneNumber) {
        return contactPreferences.contains(phoneNumber);
    }

    public void loadContacts(Context context)
    {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        Set<String> someSets = sharedPref.getStringSet("KDD", new HashSet<String>() );
        contactPreferences = new HashSet<>(someSets); // THIS LINE CREATE A COPY
    }

    public void saveContacts(Context context)
    {
        if (getContactPreferences().size() > 0 && isContactsModified()) {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putStringSet("KDD", getContactPreferences());
            editor.commit();
        }
    }

    public List<LocationVO> getLocationsList() {
        return locationsList;
    }

    public void setLocationsList(List<LocationVO> locationsList) {
        this.locationsList = locationsList;
    }

}

package com.medBuddy;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

//import com.google.firebase.database.Exclude;
//import com.google.firebase.database.IgnoreExtraProperties;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
@IgnoreExtraProperties
public class UserInfo implements Parcelable {
    public String uid;
    public String UserName;
    public String UserDateOfBirth;
    public String UserBloodGroup;
    public String UserLastDonation;
    public String UserEmail;
    public String UserPhone;
    public String UserLocation;
    public String UserPhotoUrl;
    public String medical;
    public Map<String, Boolean> stars = new HashMap<>();
    public static List<String> listUID, listName, listPhoto;

    UserInfo(){
        listName = new ArrayList<>();
        listPhoto = new ArrayList<>();
    }

    protected UserInfo(Parcel in) {
        uid = in.readString();
        UserName = in.readString();
        UserBloodGroup = in.readString();
        UserLocation = in.readString();
        medical = in.readString();
    }

    public static final Creator<DrInfo> CREATOR = new Creator<DrInfo>() {
        @Override
        public DrInfo createFromParcel(Parcel in) {
            return new DrInfo(in);
        }

        @Override
        public DrInfo[] newArray(int size) {
            return new DrInfo[size];
        }
    };

    @Exclude
    public void pp(){

    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("UserName", UserName);
        result.put("UserBloodGroup", UserBloodGroup);
        result.put("UserLocation", UserLocation);
        result.put("medical", medical);

        return result;
    }

    public String getUid()
    {
        return this.uid;
    }

    public String getUserName()
    {
        return this.UserName;
    }

    public String getUserBloodGroup()
    {
        return this.UserBloodGroup;
    }

    public String getUserLocation()
    {
        return this.UserLocation;
    }
    public String getUserPhotoUrl(){return this.UserPhotoUrl;}



    public void putList(){
        listName.add(this.UserName);
        listPhoto.add(this.UserPhotoUrl);
//        listName.put(this.drName);
    }

    UserInfo(UserInfo dr)
    {
        this.UserName = dr.UserName;
        this.UserBloodGroup = dr.UserBloodGroup;
        this.UserLocation = dr.UserLocation;
        this.medical = dr.medical;
    }

    UserInfo(String UserBloodGroup, String UserLocation, String medical)
    {
        this.UserBloodGroup = UserBloodGroup;
        this.UserLocation = UserLocation;
        this.medical = medical;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(uid);
        parcel.writeString(UserBloodGroup);
        parcel.writeString(UserLocation);
        parcel.writeString(medical);
    }
}

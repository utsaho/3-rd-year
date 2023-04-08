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
public class DrInfo implements Parcelable {
    public String uid;
    public String drName;
    public String drSpecialist;
    public String drMedical;
    public String drLocation;
    public String photoUrl;
    public Map<String, Boolean> stars = new HashMap<>();
    public static List<String> listUID, listName, listPhoto;

    DrInfo(){
        listName = new ArrayList<>();
        listPhoto = new ArrayList<>();
    }

    protected DrInfo(Parcel in) {
        uid = in.readString();
        drName = in.readString();
        drSpecialist = in.readString();
        drMedical = in.readString();
        drLocation = in.readString();
        photoUrl = in.readString();
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
        result.put("drName", drName);
        result.put("drSpecialist", drSpecialist);
        result.put("drMedical", drMedical);
        result.put("drLocation", drLocation);

        return result;
    }

    public String getUid()
    {
        return this.uid;
    }

    public String getDrName()
    {
        return this.drName;
    }

    public String getDrSpecialist()
    {
        return this.drSpecialist;
    }

    public String getDrMedical()
    {
        return this.drMedical;
    }

    public String getDrLocation()
    {
        return this.drLocation;
    }
    public String getPhotoUrl(){return this.photoUrl;}



    public void putList(){
        listName.add(this.drName);
        listPhoto.add(this.photoUrl);
//        listName.put(this.drName);
    }

    DrInfo(DrInfo dr)
    {
        this.drName = dr.drName;
        this.drSpecialist = dr.drSpecialist;
        this.drMedical = dr.drMedical;
        this.photoUrl = dr.photoUrl;
        this.drLocation = dr.drLocation;
    }

    DrInfo(String drName, String drSpecialist, String drMedical, String drLocation)
    {
        this.drName = drName;
        this.drLocation = drLocation;
        this.drSpecialist = drSpecialist;
        this.drMedical = drMedical;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(uid);
        parcel.writeString(drName);
        parcel.writeString(drSpecialist);
        parcel.writeString(drMedical);
        parcel.writeString(drLocation);
        parcel.writeString(photoUrl);
    }
}

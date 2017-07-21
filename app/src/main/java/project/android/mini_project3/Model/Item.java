package project.android.mini_project3.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by qkrqh on 2017-07-17.
 */

public class Item {

    private int _id = -1;
    private String name;
    private String address;
    private String phone;
    private String explain;
    private Double latitude;
    private Double longitude;
    private Marker marker;

    public Item(){}

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public Item(int _id, String name, String phone, String address, String explain, Double latitude, Double longitude) {
        this._id = _id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.explain = explain;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public Item(String name, String phone, String address, String explain, Double latitude, Double longitude) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.explain = explain;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Marker getMarker(){
        return marker;
    }

    public void setMarker(Marker marker){
        this.marker = marker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setLatLng(LatLng latLng){
        this.latitude = latLng.latitude;
        this.longitude = latLng.longitude;
    }
}

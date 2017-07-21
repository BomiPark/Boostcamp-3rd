package project.android.mini_project3.Fragment;


import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import project.android.mini_project3.Listener.FragmentChangeListener;
import project.android.mini_project3.Model.Item;
import project.android.mini_project3.Model.ItemLab;
import project.android.mini_project3.R;

import static project.android.mini_project3.Fragment.DetailFragment.mapToDetail;
import static project.android.mini_project3.Main.MainActivity.updateItemId;
import static project.android.mini_project3.R.id.address;


/**
 * Created by qkrqh on 2017-07-18.
 */

public class MapFragment extends Fragment {

    GoogleMap googleMap;
    Geocoder geoCoder;

    static View view;

    private Marker otherMarker;

    private MarkerOptions markerOptions;
    private ArrayList<Item> itemList; // 지금까지 저장한 데이터 목록

    private LatLng changedLatLng;
    private TextView adderss; // 맵 프래그먼트 상단의 주소 텍스트뷰
    private Item newItem;

    private Item changedItem;

    Button save, update;

    FragmentChangeListener listener;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {
            view = inflater.inflate(R.layout.fragment_map, container, false);
        }
        catch (InflateException e){
        }

        itemList = new ArrayList<Item>();
        changedItem = new Item();

        newItem  = ItemLab.get(getActivity()).getItem(getArguments().getInt("id"));

        readData();

        geoCoder = new Geocoder(getActivity());

        adderss = (TextView)view.findViewById(address);
        adderss.setText(newItem.getAddress());
        save = (Button)view.findViewById(R.id.save);
        update = (Button)view.findViewById(R.id.update);
        save.setOnClickListener(clickListener);
        update.setOnClickListener(clickListener);

        com.google.android.gms.maps.MapFragment mapFragment = (com.google.android.gms.maps.MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(mapReadyCallback);

        return view;
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        listener = (FragmentChangeListener) activity;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(view != null){
            ViewGroup parent = (ViewGroup)view.getParent();
            if(parent!=null){
                parent.removeView(view);
            }
        }
    }

    // 구글맵 사용 시 호출되는 콜백인터페이스
    OnMapReadyCallback mapReadyCallback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap Map) {

            googleMap = Map;

            googleMap.clear();

            UiSettings settings = googleMap.getUiSettings();
            settings.setZoomControlsEnabled(true);

            LatLng baseLatLng = new LatLng(newItem.getLatitude(), newItem.getLongitude());
            changedLatLng = baseLatLng;

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(baseLatLng, 17));

            markerOptions = new MarkerOptions();
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.pink_marker));

            for (Item item : itemList) {
                markerOptions.title(item.getName());
                markerOptions.snippet(item.getPhone());
                markerOptions.position(new LatLng(item.getLatitude(), item.getLongitude()));

                if (newItem.get_id() == item.get_id())
                    otherMarker = googleMap.addMarker(markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_marker)));
                else
                    otherMarker = googleMap.addMarker(markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.pink_marker)));
                item.setMarker(otherMarker);
                otherMarker.setDraggable(true);

            }

            googleMap.setOnMarkerDragListener(makerDragListener);

            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

                @Override
                public boolean onMarkerClick(Marker marker) {
                    for (Item item : itemList) {
                        if (item.getMarker().equals(marker)) {
                            adderss.setText(item.getAddress());
                            updateItemId = item.get_id();
                            break;
                        }
                    }
                    return false;
                }
            });

            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {  //마커 윈도우 클릭
                @Override
                public void onInfoWindowClick(Marker marker) {
                    for (Item item : itemList) {
                        if (item.getMarker().equals(marker)) {//현재 클릭한 InfoWindow의 마커를 itemList 의 item 가 갖고 있는 마커와 비교
                            adderss.setText(item.getAddress());
                            changedItem = item;
                            updateItemId = item.get_id();
                            break;
                        }
                    }
                }
            });

        }
    };

    GoogleMap.OnMarkerDragListener makerDragListener = new GoogleMap.OnMarkerDragListener() {
        @Override
        public void onMarkerDragStart(Marker marker) {
        }

        @Override
        public void onMarkerDrag(Marker marker) {
        }

        @Override
        public void onMarkerDragEnd(Marker marker) {
            for (Item item : itemList) {
                if (item.getMarker().equals(marker)) { //현재 클릭한 InfoWindow의 마커를 itemList 의 item 가 갖고 있는 마커와 비교
                    changedItem = item;
                    updateItemId = changedItem.get_id();
                    break;
                }
            }
            changedLatLng = marker.getPosition();
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(changedLatLng, 17));
        }
    };

    public void readData() {

        itemList= null;
        ItemLab itemLab = ItemLab.get(getActivity());
        itemList = itemLab.getItems();

    }
    public LatLng getChangedPosition(){

        return changedLatLng;
    }

    void updateDB(Item item){

        ItemLab itemLab = ItemLab.get(getActivity());
        itemLab.updateItem(item);
    }

    String setAddress(LatLng latLng){
        List<Address> addressList = null;

        try {
            addressList = geoCoder.getFromLocation(latLng.latitude, latLng.longitude, 2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addressList != null) {
            for (Address address : addressList) {
                return  address.getAddressLine(0).toString();
            }
        }
        return null;
    }

    Button.OnClickListener clickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {

            switch (view.getId()){
                case R.id.save :
                    changedItem.setLatLng(getChangedPosition());
                    String newAddr = setAddress(getChangedPosition());
                    if(newAddr != null)
                        changedItem.setAddress(newAddr);
                    updateDB(changedItem);
                    Toast.makeText(getActivity(), "반영되었습니다", Toast.LENGTH_LONG).show();
                    break;
                case R.id.update :
                    listener.changeFragment(updateItemId, mapToDetail);
                    break;
            }
        }
    };

}

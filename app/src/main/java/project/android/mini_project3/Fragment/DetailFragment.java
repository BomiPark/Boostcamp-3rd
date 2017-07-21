package project.android.mini_project3.Fragment;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

import project.android.mini_project3.Listener.FragmentChangeListener;
import project.android.mini_project3.Model.Item;
import project.android.mini_project3.Model.ItemLab;
import project.android.mini_project3.R;

import static project.android.mini_project3.Main.MainActivity.updateItemId;
import static project.android.mini_project3.R.id.up;
import static project.android.mini_project3.R.id.update;


/**
 * Created by qkrqh on 2017-07-19.
 */

public class DetailFragment extends Fragment {

    TextView address, counts;
    Geocoder geoCoder;
    EditText name, phone, explain;
    LatLng latlng;
    Button saveBtn;
    Item item;

    public static final int mapToDetail =0;
    public static final int detailToMap = 1;

    FragmentChangeListener listener;

    static View view;

    public DetailFragment(){}

    public static Fragment newInstance(int id, int state) {

        Fragment fragment;

        if(state == mapToDetail)
            fragment = new DetailFragment();
        else
            fragment = new MapFragment();

        Bundle args = new Bundle();
        args.putInt("id", id);
        fragment.setArguments(args);

        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        view =  inflater.inflate(R.layout.fragment_detail, container, false);

        geoCoder = new Geocoder(getActivity());

        name = (EditText)view.findViewById(R.id.name);
        phone = (EditText)view.findViewById(R.id.phone);
        address = (TextView)view.findViewById(R.id.address);
        explain = (EditText)view.findViewById(R.id.explain);
        counts = (TextView)view.findViewById(R.id.counts);

        saveBtn = (Button)view.findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                saveDB();
            }
        });

        explain.addTextChangedListener(textWatcher);

        if(updateItemId > -1){
            settingView();
        }

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

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void afterTextChanged(Editable edit) {
            String s = edit.toString();
            if (s.length() > 0 && s.length() <= 500)
                counts.setText(s.length() + "");

        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
        }
    };

    LatLng setLatlng(){ // address 위도 경도로 변환

        List<Address> addressList = null;

        try {
            addressList = geoCoder.getFromLocationName(address.getText().toString(), 3);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addressList == null) {
            latlng = new LatLng(37.4015984, 127.1087813);
        }

        for (Address address : addressList) {
            if(address.getLongitude() > 0 && address.getLongitude() > 0)
                return latlng = new LatLng(address.getLatitude(),address.getLongitude());
        }

        return latlng;
    }

    void saveDB(){

        if(setLatlng() != null)
            latlng = new LatLng(setLatlng().latitude, setLatlng().longitude);
        else
            latlng = new LatLng(0,0);

        ItemLab itemLab = ItemLab.get(getActivity());

        if(updateItemId < 0){
            Toast.makeText(getActivity(), "저장되었습니다", Toast.LENGTH_LONG).show();
            item = new Item(name.getText().toString(), phone.getText().toString(), address.getText().toString(), explain.getText().toString(), latlng.latitude, latlng.longitude);
            item.set_id(itemLab.addItem(item));
        }
        else {
            Toast.makeText(getActivity(), "수정되었습니다", Toast.LENGTH_LONG).show();
            item = new Item(item.get_id(),name.getText().toString(), phone.getText().toString(), address.getText().toString(), explain.getText().toString(), latlng.latitude, latlng.longitude);
            itemLab.updateItem(item);
        }

        listener.changeFragment(item.get_id(), detailToMap);
    }

    void settingView(){

        ItemLab itemLab = ItemLab.get(getActivity());
        item = itemLab.getItem(updateItemId);

        name.setText(item.getName());
        address.setText(item.getAddress());
        phone.setText(item.getPhone());
        explain.setText(item.getExplain());

    }
}

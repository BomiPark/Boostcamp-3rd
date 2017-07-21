package project.android.mini_project3.Main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import project.android.mini_project3.Fragment.DetailFragment;
import project.android.mini_project3.Fragment.MapFragment;
import project.android.mini_project3.Listener.FragmentChangeListener;
import project.android.mini_project3.Model.Item;
import project.android.mini_project3.R;

import static project.android.mini_project3.Fragment.DetailFragment.detailToMap;
import static project.android.mini_project3.Fragment.DetailFragment.mapToDetail;

public class MainActivity extends AppCompatActivity implements FragmentChangeListener{

    FrameLayout container;
    DetailFragment detailFragment;
    FragmentManager fragmentManager;

    public static int updateItemId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = (FrameLayout)findViewById(R.id.container);

        setToolBar();

        detailFragment = new DetailFragment();

        fragmentManager = getSupportFragmentManager();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, detailFragment).addToBackStack(null).commit();
    }

    void setToolBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        View viewToolbar = getLayoutInflater().inflate(R.layout.tool_bar, null);

        actionBar.setCustomView(viewToolbar, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
    }

    public void replaceFragment(Fragment fragment){

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

    }

    @Override
    public void changeFragment(int id, int state) {

        replaceFragment(DetailFragment.newInstance(id, state));

    }

}

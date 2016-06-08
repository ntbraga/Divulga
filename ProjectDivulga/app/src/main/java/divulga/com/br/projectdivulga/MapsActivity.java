package divulga.com.br.projectdivulga;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import divulga.com.br.projectdivulga.ModelDB.Establishments;
import divulga.com.br.projectdivulga.rest.RealmController;

public class MapsActivity extends BaseActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Establishments establishments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        int id = getIntent().getIntExtra("id", -1);
        if(id > -1){
            establishments = RealmController.getInstance().get(Establishments.class, id);
            setTitle(establishments.getEstab_name());
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getLocation();
    }

    private void getLocation(){
        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geoCoder.getFromLocationName(establishments.getAddress_street()+" numero "+
                    establishments.getAddress_number()+", "+establishments.getAddress_district()+", "+establishments.getAddress_zip(), 1);
            if (addresses.size() > 0)
            {
                Double lat = addresses.get(0).getLatitude();
                Double lon = addresses.get(0).getLongitude();

                final LatLng user = new LatLng(lat, lon);
        /*used marker for show the location */
                Marker hamburg = mMap.addMarker(new MarkerOptions()
                        .position(user)
                        .title(establishments.getEstab_name())
                        .icon(BitmapDescriptorFactory
                                .fromResource(R.drawable.ic_marker)));

                // Move the camera instantly to hamburg with a zoom of 15.
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(user, 15));

                // Zoom in, animating the camera.
                mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

package com.example.uitest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (bLocationPermissionGranted){
            if(ActivityCompat.checkSelfPermission(this , Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this , Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                return;
            }
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);

    }

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private static final float DEFAULT_ZOOM = 19f;
    private boolean bLocationPermissionGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
    }
    private void getLocationPermission() {
        String[] strPermissions = {FINE_LOCATION , COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext() , FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if (ContextCompat.checkSelfPermission(this.getApplicationContext() , COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                bLocationPermissionGranted = true;
                initMap();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        bLocationPermissionGranted = false;
        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0){
                    for (int i = 0; i < grantResults.length; i++){
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            bLocationPermissionGranted = false;
                            return;
                        }
                    }
                    bLocationPermissionGranted = true;
                    initMap();
                }
        }
    }
//    private void getDeviceLocation(){
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//        try {
//            if(bLocationPermissionGranted){
//                Task location = fusedLocationProviderClient.getLastLocation();
//                location.addOnCompleteListener(new OnCompleteListener() {
//                    @Override
//                    public void onComplete(@NonNull Task task) {
//                        if(task.isSuccessful()){
//                            Location currentLocation = (Location)task.getResult();
//                            moveCamera(new LatLng(currentLocation.getLatitude() , currentLocation.getLongitude()) , DEFAULT_ZOOM , "My Location");
//                        }
//                        else{
//                            Toast.makeText(MapActivity.this , "Unable to get current location" , Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//            }
//        }catch (SecurityException e){
//            Toast.makeText(MapActivity.this, e.getMessage() , Toast.LENGTH_LONG).show();
//        }
//    }

    private void initMap(){
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(MapActivity.this);
    }
//    private void moveCamera(LatLng latLng , float zoom , String title){
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng , zoom));
//        MarkerOptions options = new MarkerOptions().position(latLng)
//                .title(title);
//        if(!title.equals("My Location"))
//            mMap.addMarker(options);
//        hideKeyboard();
//    }
//    private void hideKeyboard(){
//        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//    }
}
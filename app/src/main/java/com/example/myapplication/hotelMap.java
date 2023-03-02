package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class hotelMap extends AppCompatActivity {

    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_map);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googlemap);

        client = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(hotelMap.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            RealtimeLocation();
        } else {
            ActivityCompat.requestPermissions(hotelMap.this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

    private void RealtimeLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @SuppressLint("MissingPermission")
                        @Override
                        public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            //MarkerOptions options = new MarkerOptions().position(latLng).title("Your Location");
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
//                            googleMap.addMarker(options);
                            googleMap.setMyLocationEnabled(true);
                            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                                @Override
                                public void onMapClick(@NonNull @NotNull LatLng latLng) {
                                    MarkerOptions options = new MarkerOptions().position(latLng);
                                    googleMap.addMarker(options);
                                    googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                        @Override
                                        public boolean onMarkerClick(@NonNull @NotNull Marker marker) {
                                            try {
                                                Geocoder geocoder = new Geocoder(hotelMap.this, Locale.getDefault());
                                                List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                                                double latitude = addresses.get(0).getLatitude();
                                                double longitude = addresses.get(0).getLongitude();
                                                String locality = addresses.get(0).getLocality();
                                                String address = addresses.get(0).getAddressLine(0);

                                                FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser();
                                                String userID = uid.getUid();
                                                FirebaseDatabase.getInstance().getReference("Hotel").child(userID).child("latitude").setValue(latitude);
                                                FirebaseDatabase.getInstance().getReference("Hotel").child(userID).child("longitude").setValue(longitude);
                                                if (addresses.get(0).getLocality()==null){
                                                    FirebaseDatabase.getInstance().getReference("Hotel").child(userID).child("locality").setValue("");
                                                }else {
                                                    FirebaseDatabase.getInstance().getReference("Hotel").child(userID).child("locality").setValue(locality);
                                                }
                                                FirebaseDatabase.getInstance().getReference("Hotel").child(userID).child("address").setValue(address);
                                                Intent intent = new Intent(hotelMap.this, sellerHotelAccount.class);
                                                //Just for pass data to another activity
                                                //intent.putExtra("Latitude", latLng.latitude);
                                                //intent.putExtra("Longitude", latLng.longitude);
                                                //intent.putExtra("Locality", addresses.get(0).getLocality());
                                                //intent.putExtra("Address", addresses.get(0).getAddressLine(0));
                                                startActivity(intent);
                                            }catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                            return true;
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                RealtimeLocation();
            }
        }
    }
}
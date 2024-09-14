package com.example.shopbee.ui.checkout.shipping;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.shopbee.R;
import com.example.shopbee.data.model.api.AddressResponse;
import com.example.shopbee.data.model.api.UserResponse;
import com.example.shopbee.databinding.ModifyByMapBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.common.base.BaseFragment;
import com.example.shopbee.ui.main.MainActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ModifyByMapFragment extends BaseFragment<ModifyByMapBinding, ModifyByMapViewModel> implements OnMapReadyCallback {
    private ModifyByMapBinding binding;
    private MapView mapView;
    private GoogleMap googleMap;
    private LatLng selectedLatLng;
    private ImageView saveButton;
    private String countryDef;
    private UserResponse userResponse;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.modify_by_map;
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
        viewModel.setNavigator(this);
    }

    @Override
    public FragmentType getFragmentType() {
        return FragmentType.HIDE_BOTTOM_BAR;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = getViewDataBinding();
        loadRealtimeData();
        countryDef = getArguments().getString("country_def");
        mapView = binding.mapView;
        saveButton = binding.buttonSave;
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        saveButton.setOnClickListener(v -> {
            if (selectedLatLng != null) {
                Address address = getAddressFromCoordinates(selectedLatLng.latitude, selectedLatLng.longitude);
                if (address != null) {
                    Toast.makeText(getContext(), "Address: " + address.getAddressLine(0), Toast.LENGTH_SHORT).show();
                    String newAddress = getAddressBeforeFirstComma(address.getAddressLine(0));
                    String city = address.getLocality();
                    String state = address.getAdminArea();
                    String country = address.getCountryName();
                    String postalCode = address.getPostalCode();
                    AddressResponse addressResponse = new AddressResponse(newAddress, city, state, country, postalCode, false, userResponse.getFull_name());
                    userResponse.getAddress().add(addressResponse);
                    viewModel.updateUserFirebase();
                    NavController navController = NavHostFragment.findNavController(ModifyByMapFragment.this);
                    navController.navigateUp();
                } else {
                    Toast.makeText(getContext(), "Unable to get address from coordinates", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "No location selected", Toast.LENGTH_SHORT).show();
            }
        });
        binding.buttonBackSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = NavHostFragment.findNavController(ModifyByMapFragment.this);
                navController.navigateUp();
            }
        });

        return binding.getRoot();
    }

    private void loadRealtimeData() {
        viewModel.getUserResponse().observeForever(response -> userResponse = response);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        getCurrentLocation();
    }

    private void getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                if (location != null) {
                    LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));
                    googleMap.addMarker(new MarkerOptions().position(currentLatLng).title("Your Location"));
                    selectedLatLng = currentLatLng;
                    Address address = getAddressFromCoordinates(selectedLatLng.latitude, selectedLatLng.longitude);
                    if (address != null){
                        binding.addressDisplay.setText("Address: " + address.getAddressLine(0));
                    }
                    googleMap.setOnMapClickListener(latLng -> {
                        googleMap.clear();
                        googleMap.addMarker(new MarkerOptions().position(latLng).title("Pinned Location"));
                        selectedLatLng = latLng;
                        Address address1 = getAddressFromCoordinates(selectedLatLng.latitude, selectedLatLng.longitude);
                        if (address1 != null){
                            binding.addressDisplay.setText("Address: " + address1.getAddressLine(0));
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Unable to get current location", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(), "Location permission is required to access the map.", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    private void convertLocationToCoordinates(String location) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(location, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

                googleMap.addMarker(new MarkerOptions().position(latLng).title(address.getAddressLine(0)));
            } else {
                Toast.makeText(getContext(), "Location not found", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Unable to get location", Toast.LENGTH_SHORT).show();
        }
    }
    private void convertCoordinatesToAddress(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                googleMap.addMarker(new MarkerOptions().position(latLng).title(address.getAddressLine(0)));
            } else {
                Toast.makeText(getContext(), "Address not found", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Unable to get address", Toast.LENGTH_SHORT).show();
        }
    }
    private Address getAddressFromCoordinates(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                return addresses.get(0);
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private String getAddressBeforeFirstComma(String addressLine) {
        if (addressLine != null && !addressLine.isEmpty()) {
            int commaIndex = addressLine.indexOf(",");
            if (commaIndex != -1) {
                return addressLine.substring(0, commaIndex).trim();
            } else {
                return addressLine.trim(); // No comma found, return the whole string
            }
        }
        return ""; // Return empty string if input is null or empty
    }
}

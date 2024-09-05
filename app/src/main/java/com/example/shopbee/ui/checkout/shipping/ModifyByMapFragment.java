package com.example.shopbee.ui.checkout.shipping;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.shopbee.R;
import com.example.shopbee.databinding.ModifyByMapBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.common.base.BaseFragment;
import com.example.shopbee.ui.main.MainActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ModifyByMapFragment extends BaseFragment<ModifyByMapBinding, ModifyByMapViewModel> implements OnMapReadyCallback {
    private ModifyByMapBinding binding;
    private MapView mapView;
    private GoogleMap googleMap;
    private LatLng selectedLatLng;
    private ImageView saveButton;
    private String countryDef;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = getViewDataBinding();
        countryDef = getArguments().getString("country_def");
        mapView = binding.mapView;
        saveButton = binding.buttonSave;
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.getBottomBar().hideBottomBar();

        saveButton.setOnClickListener(v -> {
            if (selectedLatLng != null) {
                Toast.makeText(getContext(), "Location: " + selectedLatLng.latitude + ", " + selectedLatLng.longitude, Toast.LENGTH_SHORT).show();
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
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setOnMapClickListener(latLng -> {
            selectedLatLng = latLng;
            googleMap.clear();
            googleMap.addMarker(new MarkerOptions().position(latLng).title("Selected Location"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        });
        Log.d("TAGGG", "onMapReady: " + countryDef);
        LatLng def_Lat = getLatLngFromCountry(countryDef, "AIzaSyC3c-TFmjbhJaN1YZlAQ4l-1kxpuo0tO3s");
        LatLng defaultLocation = new LatLng(def_Lat.latitude, def_Lat.longitude);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10));
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
    private static LatLng getLatLngFromCountry(String countryName, String apiKey) {
        LatLng latLng = null;
        try {
            String url = "https://maps.googleapis.com/maps/api/geocode/json?address="
                    + countryName + "&key=" + apiKey;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonObject = new JSONObject(response.toString());
            JSONArray results = jsonObject.getJSONArray("results");
            if (results.length() > 0) {
                JSONObject location = results.getJSONObject(0)
                        .getJSONObject("geometry")
                        .getJSONObject("location");

                double lat = location.getDouble("lat");
                double lng = location.getDouble("lng");

                latLng = new LatLng(lat, lng);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return latLng;
    }
}

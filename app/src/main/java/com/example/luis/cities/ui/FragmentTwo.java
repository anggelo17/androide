package com.example.luis.cities.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.luis.cities.R;
import com.example.luis.cities.model.Coord;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FragmentTwo extends BaseFragment implements OnMapReadyCallback{

    MapView mapView;
    Coord coord;
    GoogleMap  map;
    View mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bu= getArguments();
        if (bu!=null){
            coord = (Coord) bu.getSerializable("coord");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        mView= view;
        initViews(view);
        return view;
    }

    private void initViews(View view) {

        mapView= (MapView) view.findViewById(R.id.mapView);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mapView!=null){

            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());
        map = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        Log.d("map","lat=="+coord.getLat()+"long"+coord.getLon());

        LatLng latLng= new LatLng(Double.parseDouble(coord.getLat()),Double.parseDouble(coord.getLon()));

        googleMap.addMarker(new MarkerOptions().position(latLng));

        CameraUpdate center=
                CameraUpdateFactory.newLatLng(latLng);
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(8);

        googleMap.moveCamera(center);
        googleMap.animateCamera(zoom);

    }
}

package com.example.hp.immob;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.immob.api.ApiInterface;
import com.example.hp.immob.config.ApiClient;
import com.example.hp.immob.config.Constant;
import com.example.hp.immob.model.Bien;
import com.example.hp.immob.model.Image;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivityHome extends Fragment implements OnMapReadyCallback {
    private static final String TAG = "MapsActivityHome";
    private GoogleMap mMap;
    private ApiInterface apiInterface = ApiClient.retrofit.create(ApiInterface.class);
    private List<Bien> biens;
    private List<Image> images = new ArrayList<>();
   private TextView titre, prix, dateAjout, region;
   private ImageView Img;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        return inflater.inflate(R.layout.activity_maps_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


        super.onActivityCreated(savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Call<List<Bien>> call = apiInterface.getAllGoods();
        call.enqueue(new Callback<List<Bien>>() {
            @Override
            public void onResponse(Call<List<Bien>> call, Response<List<Bien>> response) {

                biens = response.body();
                for (int i = 0; i < biens.size(); i++) {

                    Call<List<Image>> callimg = apiInterface.getImg(biens.get(i).getIdBien());
                    callimg.enqueue(new Callback<List<Image>>() {
                        @Override
                        public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {
                            images.add(response.body().get(0));
                            for(Bien b:biens){
                                mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(Double.parseDouble(b.getLatitude()), Double.parseDouble(b.getLongitude())))
                                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_house)));
                            }

                        }

                        @Override
                        public void onFailure(Call<List<Image>> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Bien>> call, Throwable t) {

            }
        });
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
        mMap.getUiSettings().setZoomControlsEnabled(true);
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(34.80,10.55)));
        // mMap.animateCamera(CameraUpdateFactory.zoomBy(5));



        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View v = getActivity().getLayoutInflater().inflate(R.layout.row_item, null);
                Img = (ImageView) v.findViewById(R.id.image_view);
                titre = (TextView) v.findViewById(R.id.titre);
                prix = (TextView) v.findViewById(R.id.prix);
                region = (TextView) v.findViewById(R.id.region);
                dateAjout = (TextView) v.findViewById(R.id.date_ajout);

                Img.setImageBitmap(Constant.convert(images.get(Integer.parseInt(new String(marker.getId())
                        .replace("m", ""))).getImg()));
                titre.setText(biens.get(Integer.parseInt(new String(marker.getId())
                        .replace("m", ""))).getTitre());
                prix.setText(biens.get(Integer.parseInt(new String(marker.getId())
                        .replace("m", ""))).getPrix());
                region.setText(biens.get(Integer.parseInt(new String(marker.getId())
                        .replace("m", ""))).getRegion());
                dateAjout.setText(biens.get(Integer.parseInt(new String(marker.getId())
                        .replace("m", ""))).getDateAjout());


                return v;
            }
        });



        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                startActivity(new Intent(getActivity(), FullAnnActivity.class)
                        .putExtra("id", biens.get(Integer.parseInt(new String(marker.getId())
                                .replace("m", "")))
                                .getIdBien()));

            }
        });



    }
}

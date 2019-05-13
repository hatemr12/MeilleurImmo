package com.example.hp.immob;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.immob.api.ApiInterface;
import com.example.hp.immob.config.ApiClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.hp.immob.LoginActivity.MY_PREFS_NAME;

public class UploadAnnActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private ImageView ChooseImg;
    private ImageView openCam;
    private TextView countimgtext;
    Boolean dragged = false;
    private int countimg = 0;
    private static final int IMG_REQUEST = 777;
    private static final int CAMERA_PIC_REQUEST = 2;
    private String region;
    private ArrayList<String> imagelist = new ArrayList<>();
    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Marker currentLocationMarker;
    MarkerOptions markerOptions;
    private ViewPager viewPager;
    CustomSwipeAdapter adapter;
    public static final int REQUEST_LOCATION_CODE = 99;
    private Button btnsend;
    private ApiInterface apiInterface = ApiClient.retrofit.create(ApiInterface.class);
    private double lat = 0;
    private double lon = 0;
    private LatLng latLngmarker;
    private LatLng latLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annonce_upload);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLoactionPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        final EditText edittitre = (EditText) findViewById(R.id.editTextTitre);
        final EditText editprix = (EditText) findViewById(R.id.editTextPrix);
        final EditText editville = (EditText) findViewById(R.id.editTextVille);
        final Spinner dropdown = (Spinner) findViewById(R.id.spinner1);
        final EditText editdesc = (EditText) findViewById(R.id.editTextDescription);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        countimgtext = (TextView) findViewById(R.id.countimg);

        Button btnsend = (Button) findViewById(R.id.btn_suiv);
        Button reset = (Button) findViewById(R.id.btn_reset);

        ChooseImg = (ImageView) findViewById(R.id.imageViewSelectImage);
        openCam = (ImageView) findViewById(R.id.cam);

        final String[] items =
                new String[]{"Ariana", "Béja", "Ben Arous", "Bizerte", "Gabès", "Gafsa",
                        "Jendouba", "Kairouan", "Kasserine", "Kébili", "Le Kef", "Mahdia", "La Manouba", "Médenine", "Monastir",
                        "Nabeul", "Sfax", "Sidi Bouzid", "Siliana", "Sousse", "Tataouine", "Tozeur", "Tunis", "Zaghouan"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        ChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(IMG_REQUEST);
            }
        });
        openCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(CAMERA_PIC_REQUEST);
            }
        });


        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                region = items[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String titre = edittitre.getText().toString();
                final String prix = editprix.getText().toString();
                final String ville = editville.getText().toString();
                final String description = editdesc.getText().toString();

                if (titre.equals("") && prix.equals("") && ville.equals("") && description.equals("")) {

                    edittitre.setError("Vide");
                    editprix.setError("Vide");
                    editville.setError("Vide");
                    editdesc.setError("Vide");


                } else {
                    SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                    String login = prefs.getString("login", "");
                    Call<String> call = apiInterface.addBien(login, titre, prix, region, ville, description, Double.toString(lat), Double.toString(lon));
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                            if (response.body() != "no") {
                                UploadImage(response.body(), imagelist);
                                Toast.makeText(getApplicationContext(), "Votre annonce a été déposée", Toast.LENGTH_LONG).show();

                                startActivity(new Intent(UploadAnnActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                            }


                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {


                        }
                    });
                }
            }
        });


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edittitre.setText("");
                editprix.setText("");
                editville.setText("");
                editdesc.setText("");
                imagelist.removeAll(imagelist);
                countimgtext.setText("0");
                mMap.clear();
                //  selectedImg.setImageBitmap(null);

                //ChooseImg.setVisibility(View.VISIBLE);

                //selectedImg.setVisibility(View.GONE);

            }
        });
    }


    private String imageToString(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }


    private void selectImage(int img) {
        if (img == 2)
            startActivityForResult(new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE), img);
        if (img == 777)
            startActivityForResult(new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT), img);

    }

    private void UploadImage(String idbb, ArrayList<String> imagelist) {
        for (String imglt : imagelist) {

            Call<String> calluploadimg = apiInterface.uploadImg(idbb, imglt);
            calluploadimg.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case IMG_REQUEST:
                    try {
                        imagelist.add(imageToString(MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData())));
                        countimg++;
                        countimgtext.setText("Nombre d'image sélectionner: " + countimg);
                        // selectedImg.setVisibility(View.VISIBLE);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case CAMERA_PIC_REQUEST:
                    imagelist.add(imageToString((Bitmap) data.getExtras().get("data")));
                    countimg++;
                    countimgtext.setText("Nombre d'image sélectionner: " + countimg);
                    // selectedImg.setVisibility(View.VISIBLE);

                    break;

            }
        }

    }

    //maps
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);

        }


        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                dragged = true;
                latLngmarker = marker.getPosition();
                lat = latLngmarker.latitude;
                lon = latLngmarker.longitude;
                Geocoder geocoder;
                List<Address> yourAddresses;

                geocoder = new Geocoder(UploadAnnActivity.this, Locale.getDefault());
                try {
                    yourAddresses = geocoder.getFromLocation(latLngmarker.latitude, latLngmarker.longitude, 1);
                    String yourAddress = yourAddresses.get(0).getAddressLine(0);
                    // String yourCity = yourAddresses.get(0).getAddressLine(1);
                    // Toast.makeText(getApplicationContext(),yourAddresses.size()+"",Toast.LENGTH_LONG).show();
                    marker.setSnippet(yourAddress);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                // marker.setTitle("Tapez pour afficher l'addresse");
                // Toast.makeText(UploadAnnActivity.this, lat + " " + lon, Toast.LENGTH_LONG).show();
            }
        });
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {

                mMap.clear();
                Geocoder geocoder;
                List<Address> yourAddresses;
                geocoder = new Geocoder(UploadAnnActivity.this, Locale.getDefault());

                try {
                    yourAddresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    String yourAddress = yourAddresses.get(0).getAddressLine(0);
                    //String yourCity = yourAddresses.get(0).getAddressLine(1);
                    // String yourCountry = yourAddresses.get(0).getAddressLine(2);

                    markerOptions = new MarkerOptions();
                    markerOptions.snippet(yourAddress);
                    markerOptions.position(latLng);
                    markerOptions.title("Déplacer le marqueur");
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.house));
                    markerOptions.draggable(true);
                    currentLocationMarker = mMap.addMarker(markerOptions);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.animateCamera(CameraUpdateFactory.zoomBy(5));


                } catch (IOException e) {
                    e.printStackTrace();
                }

                return false;
            }
        });

    }

    protected synchronized void buildGoogleApiClient() {

        client = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

        client.connect();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
        }


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lon = location.getLongitude();
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        if (currentLocationMarker != null) {
            currentLocationMarker.remove();
        }


        if (client != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(client, this);
        }
    }


    public boolean checkLoactionPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);

            }
            return false;
        }

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case REQUEST_LOCATION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //per is granted
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (client == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else //per denied
                {
                    Toast.makeText(this, "per denied", Toast.LENGTH_LONG).show();
                }
                return;
        }
    }


}

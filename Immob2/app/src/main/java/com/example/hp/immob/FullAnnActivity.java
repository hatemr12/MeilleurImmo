package com.example.hp.immob;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.immob.api.ApiInterface;
import com.example.hp.immob.config.ApiClient;
import com.example.hp.immob.config.Constant;
import com.example.hp.immob.model.Bien;
import com.example.hp.immob.model.Image;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FullAnnActivity extends AppCompatActivity {
    TextView titre, prix, dateAjout, region, nomprenom, ville, description;
    private Button btntel, btnsms;
    private TextView tel;

    private List<Bien> b;
    ViewPager viewPager;
    CustomSwipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_ann);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        titre = (TextView) findViewById(R.id.titre);
        ville = (TextView) findViewById(R.id.ville);
        description = (TextView) findViewById(R.id.description);
        prix = (TextView) findViewById(R.id.prix);
        dateAjout = (TextView) findViewById(R.id.date_ajout);
        region = (TextView) findViewById(R.id.region);
        nomprenom = (TextView) findViewById(R.id.nomprenom);
        tel = (TextView) findViewById(R.id.tel);
        btntel = (Button) findViewById(R.id.btntel);
        btnsms = (Button) findViewById(R.id.btnsms);


        ApiInterface apiInterface = ApiClient.retrofit.create(ApiInterface.class);
        Call<List<Bien>> call = apiInterface.getAnnById(getIntent().getStringExtra("id"));
        call.enqueue(new Callback<List<Bien>>() {
            @Override
            public void onResponse(Call<List<Bien>> call, Response<List<Bien>> response) {
                b = response.body();
                titre.setText(b.get(0).getTitre());
                prix.setText(b.get(0).getPrix());
                dateAjout.setText(b.get(0).getDateAjout());
                region.setText(b.get(0).getRegion());
                ville.setText(b.get(0).getVille());
                description.setText(b.get(0).getDescription());
                nomprenom.setText(b.get(0).getPrenom() + " " + b.get(0).getNom());
                tel.setText(b.get(0).getTel());


            }

            @Override
            public void onFailure(Call<List<Bien>> call, Throwable t) {

            }
        });
        ff();


        btntel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialContactPhone(b.get(0).getTel());


            }
        });

        btnsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("smsto:" + b.get(0).getTel());
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                intent.putExtra("sms_body", "");
                startActivity(intent);
            }
        });
    }

    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }

    public void ff() {


        ApiInterface apiInterface = ApiClient.retrofit.create(ApiInterface.class);
        Call<List<Image>> call = apiInterface.getImgById(getIntent().getStringExtra("id"));
        call.enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {
                List<Image> images;
                images = response.body();
                adapter = new CustomSwipeAdapter(getApplicationContext(), images);
                viewPager.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Image>> call, Throwable t) {

            }
        });
    }
}

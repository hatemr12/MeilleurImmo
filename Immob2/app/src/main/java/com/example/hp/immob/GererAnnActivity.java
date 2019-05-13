package com.example.hp.immob;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hp.immob.api.ApiInterface;
import com.example.hp.immob.config.ApiClient;
import com.example.hp.immob.model.Bien;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.hp.immob.LoginActivity.MY_PREFS_NAME;


public class GererAnnActivity extends AppCompatActivity {
    ArrayList<String> bien = new ArrayList<>();
    ArrayList<String> idbien = new ArrayList<>();
    List<Bien> resbien;
    ListView listview;
    private ApiInterface apiInterface;
    private ListAdapter listadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerer_ann);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String login = prefs.getString("login", "");
        apiInterface = ApiClient.retrofit.create(ApiInterface.class);
        Call<List<Bien>> call = apiInterface.getAnnByUserId(login);
        call.enqueue(new Callback<List<Bien>>() {
            @Override
            public void onResponse(Call<List<Bien>> call, Response<List<Bien>> response) {
                resbien = response.body();
                for (Bien b : resbien) {
                    bien.add(b.getTitre());
                    idbien.add(b.getIdBien());

                }
                listadapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, bien);

                listview = (ListView) findViewById(R.id.listvannsupp);
                listview.setAdapter(listadapter);
                listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        Call<String> call = apiInterface.deleteAnnById(idbien.get(position));
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                Toast.makeText(getApplicationContext(), "Votre Annonce a été supprimer", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });
                        return false;
                    }
                });


            }

            @Override
            public void onFailure(Call<List<Bien>> call, Throwable t) {

            }
        });


    }


}

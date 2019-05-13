package com.example.hp.immob;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hp.immob.api.ApiInterface;
import com.example.hp.immob.config.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.hp.immob.LoginActivity.MY_PREFS_NAME;

public class InscActivity extends AppCompatActivity {
    private Button insc, reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insc);

        final EditText editusername = (EditText) findViewById(R.id.username_insc);
        final EditText editnom = (EditText) findViewById(R.id.nom_insc);
        final EditText editprenom = (EditText) findViewById(R.id.prenom_insc);
        final EditText edittel = (EditText) findViewById(R.id.tel_insc);
        final EditText editmdp = (EditText) findViewById(R.id.mdp_insc);
        final EditText editmdp2 = (EditText) findViewById(R.id.mdpverif_insc);

        insc = (Button) findViewById(R.id.btn_insc);
        reset = (Button) findViewById(R.id.btn_insc_reset);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editusername.setText("");
                editnom.setText("");
                editprenom.setText("");
                edittel.setText("");
                editmdp.setText("");
                editmdp2.setText("");
            }
        });

        insc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String username = editusername.getText().toString();
                final String nom = editnom.getText().toString();
                final String prenom = editprenom.getText().toString();
                String tel = edittel.getText().toString();
                String mdp = editmdp.getText().toString();
                String mdp2 = editmdp2.getText().toString();


                if (username.equals(""))
                    editusername.setError("Le champ est vide.");
                if (nom.equals(""))
                    editnom.setError("Le champ est vide.");
                if (tel.equals(""))
                    edittel.setError("Le champ est vide.");
                if (prenom.equals(""))
                    editprenom.setError("Le champ est vide.");
                if (mdp.equals(""))
                    editmdp.setError("Le champ est vide.");
                if (mdp2.equals(""))
                    editmdp2.setError("Le champ est vide.");


                if (!username.isEmpty() && !nom.isEmpty() && !tel.isEmpty() && !prenom.isEmpty() && !mdp.isEmpty() && !mdp2.isEmpty()) {
                    final String login = username;
                    if (!mdp.equals(mdp2)) {
                        editmdp2.setError("Veuillez saisir une mot de passe identique.");
                    } else {

                        //begin call
                        ApiInterface apiInterface = ApiClient.retrofit.create(ApiInterface.class);
                        Call<String> call = apiInterface.addUser(username, nom, prenom, tel, mdp);
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {

                                if (response.body().equals("yes")) {
                                    Toast.makeText(getApplicationContext(), "Bienvenue " + prenom + " " + nom, Toast.LENGTH_SHORT).show();
                                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                    editor.putString("login", login);
                                    editor.apply();
                                    startActivity(new Intent(InscActivity.this, MainActivity.class));
                                } else {
                                    Toast.makeText(getApplicationContext(), "Nom d'utilisateur existant", Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Log.v("Erreur", t.getMessage());
                            }
                        });
                        //end call
                    }
                }


            }
        });


    }


}

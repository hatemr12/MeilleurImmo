package com.example.hp.immob;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.immob.api.ApiInterface;
import com.example.hp.immob.config.ApiClient;
import com.example.hp.immob.model.Client;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.hp.immob.LoginActivity.MY_PREFS_NAME;

public class AccountActivity extends AppCompatActivity {


    private EditText editnom, editprenom, edittel, editmdp, editmdp2;
    private ApiInterface apiInterface;
    private TextView user;
    private List<Client> cl;
    private Button btnupdate, btnsupp;

    private String nom, prenom, tel, mdp, mdp2;

    SharedPreferences prefs;
    String login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        editnom = (EditText) findViewById(R.id.update_nom);
        editprenom = (EditText) findViewById(R.id.update_prenom);
        edittel = (EditText) findViewById(R.id.update_tel);
        editmdp = (EditText) findViewById(R.id.update_mdp);
        editmdp2 = (EditText) findViewById(R.id.update_mdp2);
        user = (TextView) findViewById(R.id.user);
        btnsupp = (Button) findViewById(R.id.btnsupp);
        btnupdate = (Button) findViewById(R.id.btn_update_user);
        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        login = prefs.getString("login", "");
        user.setText(login);
        apiInterface = ApiClient.retrofit.create(ApiInterface.class);
        Call<List<Client>> call = apiInterface.getUserById(login);
        call.enqueue(new Callback<List<Client>>() {
            @Override
            public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                cl = response.body();
                editnom.setHint(cl.get(0).getNom());
                editprenom.setHint(cl.get(0).getPrenom());
                edittel.setHint(cl.get(0).getTel());
            }

            @Override
            public void onFailure(Call<List<Client>> call, Throwable t) {

            }
        });

        btnsupp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(AccountActivity.this)
                        .setMessage("Voulez-vous supprimer votre compte ?")
                        .setCancelable(false)
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Call<String> call = apiInterface.deleteUserById(login);
                                call.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        if (response.body().equals("yes")) {
                                            Toast.makeText(getApplicationContext(), "Votre compte a été supprimer", Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(AccountActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {

                                    }
                                });


                                startActivity(new Intent(AccountActivity.this, LoginActivity.class)
                                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            }
                        })
                        .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .create()
                        .show();


            }
        });

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nom = editnom.getText().toString();
                prenom = editprenom.getText().toString();
                tel = edittel.getText().toString();
                mdp = editmdp.getText().toString();
                mdp2 = editmdp2.getText().toString();

                if (nom.equals(""))
                    nom = (cl.get(0).getNom());
                if (tel.equals(""))
                    tel = cl.get(0).getTel();
                if (prenom.equals(""))
                    prenom = cl.get(0).getPrenom();
                if (mdp.equals(""))
                    mdp = cl.get(0).getPassword();
                if (mdp2.equals(""))
                    mdp2 = cl.get(0).getPassword();
                if (!(nom.isEmpty() && prenom.isEmpty() && tel.isEmpty() && mdp.isEmpty())) {
                    if (!mdp.equals(mdp2)) {
                        editmdp2.setError("Veuillez saisir une mot de passe identique.");
                    } else {
                        Call<String> call = apiInterface.updateUser(login, nom, prenom, tel, mdp);
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                Toast.makeText(getApplicationContext(), "Votre compte a été modifier", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);

                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });


                    }
                }

            }

        });


    }
}

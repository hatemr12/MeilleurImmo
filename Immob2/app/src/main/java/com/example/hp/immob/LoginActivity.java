package com.example.hp.immob;

import android.content.Intent;
import android.content.SharedPreferences;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }

        final EditText editlogin = (EditText) findViewById(R.id.email_login);
        final EditText editpw = (EditText) findViewById(R.id.mdp_login);
        Button btnconnexion = (Button) findViewById(R.id.btn_connexion_login);
        Button btnannuler = (Button) findViewById(R.id.btn_annuler_login);

        TextView textview_inscri = (TextView) findViewById(R.id.text_insc_login);

        btnconnexion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String login = editlogin.getText().toString();
                String pw = editpw.getText().toString();


                if (login.equals("") && pw.equals("")) {
                    editlogin.setError("Vide");
                    editpw.setError("Vide");
                } else {

                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString("login", login);
                    editor.apply();
                }


                ApiInterface apiInterface = ApiClient.retrofit.create(ApiInterface.class);
                Call<Client> call = apiInterface.login(login, pw);
                call.enqueue(new Callback<Client>() {
                    @Override
                    public void onResponse(Call<Client> call, Response<Client> response) {
                        if (response.body() != null) {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }
                    }

                    @Override
                    public void onFailure(Call<Client> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "problème de connexion", Toast.LENGTH_LONG).show();

                    }
                });

            }
        });

        btnannuler.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                editlogin.setText("");
                editpw.setText("");


            }
        });

        textview_inscri.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, InscActivity.class);
                startActivity(intent);
            }
        });

    }


}

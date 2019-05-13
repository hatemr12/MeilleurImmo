package com.example.hp.immob;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.hp.immob.api.ApiInterface;
import com.example.hp.immob.config.ApiClient;
import com.example.hp.immob.model.Bien;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends Fragment {
    private static final String TAG = "HomeActivity";
    private RecyclerView recyclerView;
    private EditText editrech;
    private Button btnrech;
    private Button btnreset;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter adapter;
    private List<Bien> biens;
    private ApiInterface apiInterface;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container , Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        return inflater.inflate(R.layout.activity_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listv();

    }


    private void listv() {
        apiInterface = ApiClient.retrofit.create(ApiInterface.class);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        editrech=(EditText)getActivity().findViewById(R.id.recherche);
        btnrech=(Button)getActivity().findViewById(R.id.btn_recherche);
        btnreset=(Button)getActivity().findViewById(R.id.btnreset);

        getAll();

        btnrech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mot =editrech.getText().toString();
                if(mot.equals("")){
                    getAll();

                }else{
                    Call<List<Bien>> call = apiInterface.getAnnByRegion(mot);
                    call.enqueue(new Callback<List<Bien>>() {
                        @Override
                        public void onResponse(Call<List<Bien>> call, Response<List<Bien>> response) {
                            biens = response.body();
                            adapter = new RecyclerAdapter(biens,getActivity());
                            recyclerView.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure(Call<List<Bien>> call, Throwable t) {

                        }
                    });
                }

            }
        });

        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editrech.getText().toString().isEmpty()){
                    editrech.setText("");
                    getAll();

                }
            }
        });

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // do whatever

                        //Toast.makeText(getApplicationContext(), "click"+position, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), FullAnnActivity.class);
                        intent.putExtra("id", biens.get(position).getIdBien());
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                        //Toast.makeText(getApplicationContext(), "long click", Toast.LENGTH_SHORT).show();

                    }
                })
        );

    }
    private void getAll(){
        Call<List<Bien>> call = apiInterface.getAllGoods();
        call.enqueue(new Callback<List<Bien>>() {
            @Override
            public void onResponse(Call<List<Bien>> call, Response<List<Bien>> response) {

                biens = response.body();
                adapter = new RecyclerAdapter(biens,getActivity());
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Bien>> call, Throwable t) {
            }
        });
    }
}

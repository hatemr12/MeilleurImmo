package com.example.hp.immob;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private List<Bien> biens;
    private List<Image> images;
    private Context c;


    public RecyclerAdapter(List<Bien> biens, Context context) {


        this.biens = biens;
        this.c = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);

        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.titre.setText(biens.get(position).getTitre());
        holder.prix.setText(biens.get(position).getPrix());
        holder.region.setText(biens.get(position).getRegion());
        holder.dateAjout.setText(biens.get(position).getDateAjout());


        ApiInterface apiInterface = ApiClient.retrofit.create(ApiInterface.class);
        Call<List<Image>> callimg = apiInterface.getImg(biens.get(position).getIdBien());
        callimg.enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {
                images = response.body();
                holder.Img.setImageBitmap(Constant.convert(images.get(0).getImg()));


            }

            @Override
            public void onFailure(Call<List<Image>> call, Throwable t) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return biens.size();


    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titre, prix, dateAjout, region;
        ImageView Img;

        public MyViewHolder(View itemView) {
            super(itemView);
            titre = (TextView) itemView.findViewById(R.id.titre);
            prix = (TextView) itemView.findViewById(R.id.prix);
            dateAjout = (TextView) itemView.findViewById(R.id.date_ajout);
            region = (TextView) itemView.findViewById(R.id.region);
            Img = (ImageView) itemView.findViewById(R.id.image_view);


        }

    }
}

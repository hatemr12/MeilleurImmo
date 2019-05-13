
package com.example.hp.immob;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.immob.api.ApiInterface;
import com.example.hp.immob.config.ApiClient;
import com.example.hp.immob.config.Constant;
import com.example.hp.immob.model.Bien;
import com.example.hp.immob.model.Image;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.example.hp.immob.LoginActivity.MY_PREFS_NAME;


public class CustomSwipeAdapter extends PagerAdapter {
    private Context ctx;
    private ImageView imageView;
    private TextView textView;
    private int pos;
    private List<Image> images;
    private LayoutInflater layoutInflater;

    public CustomSwipeAdapter(Context ctx, List<Image> images) {
        this.images = images;
        this.ctx = ctx;
    }


    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.swipe_layout, container, false);
        imageView = (ImageView) item_view.findViewById(R.id.image_view);
        textView = (TextView) item_view.findViewById(R.id.image_count);
        imageView.setImageBitmap(Constant.convert(images.get(position).getImg()));
        pos = position + 1;
        textView.setText(pos + "");
        container.addView(item_view);
        return item_view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }


}


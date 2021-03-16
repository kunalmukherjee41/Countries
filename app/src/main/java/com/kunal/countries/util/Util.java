package com.kunal.countries.util;

import android.content.Context;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.kunal.countries.R;
import com.kunal.countries.view.GlideApp;

public class Util {

    public static void loadImage(ImageView imageView, String url, CircularProgressDrawable progressDrawable) {
        RequestOptions options = new RequestOptions()
                .placeholder(progressDrawable)
                .error(R.mipmap.ic_launcher);

//        Glide.with(imageView.getContext())
//                .setDefaultRequestOptions(options)
//                .load(url)
//                .into(imageView);
        GlideApp.with(imageView.getContext()).load(url).apply(RequestOptions.centerCropTransform()).into(imageView);

    }

    public static CircularProgressDrawable getCircularDrawable(Context context) {
        CircularProgressDrawable cpd = new CircularProgressDrawable(context);
        cpd.setStrokeWidth(10f);
        cpd.setCenterRadius(50f);
        cpd.setColorSchemeColors(R.color.purple_200);
        cpd.start();
        return cpd;
    }

    @BindingAdapter("android:imageUrl")
    public static void getImageUrl(ImageView view, String url) {
        loadImage(view, url, getCircularDrawable(view.getContext()));
    }

}

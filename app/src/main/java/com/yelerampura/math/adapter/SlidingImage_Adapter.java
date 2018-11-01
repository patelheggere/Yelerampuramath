package com.yelerampura.math.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yelerampura.math.R;
import com.yelerampura.math.models.GalleryModel;

import java.util.List;

public class SlidingImage_Adapter extends PagerAdapter {

    private static final String TAG = "SlidingImage_Adapter";
    private List<GalleryModel> list;

    private LayoutInflater inflater;
    private Context context;


    public SlidingImage_Adapter(Context context, List<GalleryModel> IMAGES) {
        this.context = context;
        this.list=IMAGES;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        GalleryModel ob  = list.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.gallery_item, collection, false);

        TextView textViewTitle = layout.findViewById(R.id.image_title);
        if(ob.getTitle()!=null)
        textViewTitle.setText(ob.getTitle());

        TextView textViewDesc = layout.findViewById(R.id.image_desc);
        if(ob.getDesc()!=null)
        textViewDesc.setText(ob.getDesc());

        ImageView imageView = layout.findViewById(R.id.image);
        if(ob.getUrl()!=null) {
            Glide.with(context).load(ob.getUrl()).apply(RequestOptions.placeholderOf(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background)).into(imageView);
        }
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }



}
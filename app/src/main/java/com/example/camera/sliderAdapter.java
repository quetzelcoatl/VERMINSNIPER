package com.example.camera;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class sliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater inflater;


    public int[] imagesArray = {R.drawable.eagle,R.drawable.fortnite};
    public String[] title1 = {"EAGLE","ABORTNITE"};
    public String[] descrip = {"EAGLES FOR LIFE","LOL DONT PLAY"};

    public  sliderAdapter(Context context)
    {
        this.context = context;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return (view==object);
    }

    @Override
    public int getCount() {
       return title1.length;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView((LinearLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slide,container,false);

        ImageView imageView = view.findViewById(R.id.slideimg);
        TextView title = view.findViewById(R.id.txttitle);
        TextView desc = view.findViewById(R.id.txtdesc);

        imageView.setImageResource(imagesArray[position]);
        title.setText(title1[position]);
        desc.setText(descrip[position]);

        container.addView(view);
        return view;
    }
}

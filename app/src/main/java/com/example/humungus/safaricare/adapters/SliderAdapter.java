package com.example.humungus.safaricare.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.humungus.safaricare.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by humungus on 3/1/18.
 */

public class SliderAdapter extends PagerAdapter  {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    //image arrays
    public int[] slide_images = {
            R.drawable.speed,
            R.drawable.megaphone,
            R.drawable.bus
    };

    //title array
    public String [] slide_titles = {
            "Track Speed",
            "Report",
            "Drive Safe"
    };

    //description array
    public String [] slide_descriptions = {
            "Track the speed of your vehicle",
            "Report misbehaving drivers through this app",
            "drive safe knowing you can manage your journey with safaricare"
    };
    @Override
    public int getCount() {
        return slide_titles.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);

        CircleImageView circleImageView = view.findViewById(R.id.svimage);
        TextView svTitle = view.findViewById(R.id.svtitle);
        TextView svDescription = view.findViewById(R.id.svdescription);

        circleImageView.setImageResource(slide_images[position]);
        svTitle.setText(slide_titles[position]);
        svDescription.setText(slide_descriptions[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {


        container.removeView((RelativeLayout)object);
    }
}
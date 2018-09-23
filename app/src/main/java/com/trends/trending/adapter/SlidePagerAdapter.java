package com.trends.trending.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trends.trending.R;

import java.util.List;

/**
 * Created by USER on 3/7/2018.
 */

public class SlidePagerAdapter extends PagerAdapter {
    private Context context;
    private List<String> colorName;

    public SlidePagerAdapter(Context context, List<String> colorName) {
        this.context = context;
        this.colorName = colorName;
    }

    @Override
    public int getCount() {
        return colorName.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_slider, null);

        TextView textView = view.findViewById(R.id.textView);
        //LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);

        textView.setText(Html.fromHtml("&#34;<b>"+colorName.get(position)+"</b>&#34;"));
        //linearLayout.setBackgroundColor(color.get(position));

        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}

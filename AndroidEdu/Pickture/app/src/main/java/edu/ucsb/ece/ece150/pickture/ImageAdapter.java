package edu.ucsb.ece.ece150.pickture;

/**
 * Created by xuanwang on 1/5/16.
 */


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return mThumbIds[position];
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(360, 360));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(2,2,2,2);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // references to our images
    protected Integer[] mThumbIds = {
            R.drawable.bear,
            R.drawable.kyoto, R.drawable.library,
            R.drawable.chairs, R.drawable.roses,
            R.drawable.cross, R.drawable.sand,
            R.drawable.circles, R.drawable.trunk,
            R.drawable.japan, R.drawable.mosque,
            R.drawable.whitetower, R.drawable.deer,
            R.drawable.post
    };
}

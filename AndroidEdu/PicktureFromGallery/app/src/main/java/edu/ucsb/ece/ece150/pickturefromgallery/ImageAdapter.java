package edu.ucsb.ece.ece150.pickturefromgallery;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

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


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}

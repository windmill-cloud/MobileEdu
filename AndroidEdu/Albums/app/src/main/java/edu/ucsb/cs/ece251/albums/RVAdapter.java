package edu.ucsb.cs.ece251.albums;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by xuanwang on 1/21/17.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.AlbumViewHolder>
        implements View.OnClickListener{

    List<MainActivity.Album> albums;

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , String data);
    }
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    RVAdapter(List<MainActivity.Album> albums){
        this.albums = albums;
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        AlbumViewHolder pvh = new AlbumViewHolder(v);

        return pvh;
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder albumViewHolder, int i) {
        albumViewHolder.albumName.setText(albums.get(i).albumName);
        albumViewHolder.artist.setText(albums.get(i).artist);
        albumViewHolder.thumbnailPhoto.setImageDrawable(albums.get(i).thumbnail);
        albumViewHolder.albumPhoto.setImageDrawable(albums.get(i).albumPhoto);

        Button buyBtn = albumViewHolder.buyAlbum;
        buyBtn.setText(String.format("See %s on ALLMUSIC", albums.get(i).albumName));
        albumViewHolder.buyAlbum.setOnClickListener(this);
        buyBtn.setTag(albums.get(i).amazonUrl);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            // get tag
            mOnItemClickListener.onItemClick(v,(String)v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class AlbumViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView albumName;
        TextView artist;
        ImageView thumbnailPhoto;
        ImageView albumPhoto;
        Button buyAlbum;

        AlbumViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            albumName = (TextView) itemView.findViewById(R.id.album_name);
            artist = (TextView )itemView.findViewById(R.id.artist);
            thumbnailPhoto = (ImageView) itemView.findViewById(R.id.thumbnail_photo);
            albumPhoto = (ImageView) itemView.findViewById(R.id.album_photo);
            buyAlbum = (Button) itemView.findViewById(R.id.buy_button);
        }
    }
}
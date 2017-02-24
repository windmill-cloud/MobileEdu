package edu.ucsb.cs.ece251.albums;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv;
    RequestQueue mRequestQueue;
    RVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        //LinearLayoutManager llm = new LinearLayoutManager(this);
        //llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        mRequestQueue = SingletonRequestQueue.getInstance(this).getRequestQueue();
        getData();

    }

    class Album {
        String albumName;
        String artist;
        Drawable thumbnail;
        Drawable albumPhoto;
        String amazonUrl;

        Album(String albumName, String artist, String amazonUrl) {
            this.albumName = albumName;
            this.artist = artist;
            this.thumbnail = ResourcesCompat.getDrawable(
                    getResources(), R.drawable.placeholder, null);
            this.albumPhoto = ResourcesCompat.getDrawable(
                    getResources(), R.drawable.placeholder, null);
            this.amazonUrl = amazonUrl;
        }
    }

    private List<Album> albums = new ArrayList<>();

    private void getData(){

        // This directs to host machine, rather than the emulator
        String url = getString(R.string.url);

        // Formulate the request and handle the response.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with the response

                        try {
                            JSONArray albumsList = new JSONArray(response);
                            for(int i = 0; i < albumsList.length(); i++){
                                JSONObject album = albumsList.getJSONObject(i);
                                String albumName = album.getString("title");
                                String artist = album.getString("artist");
                                String thumbnailUrl = album.getString("thumbnail_image");
                                String imageUrl = album.getString("image");
                                String buyUrl = album.getString("url");

                                Album albumForDisplay = new Album(albumName, artist, buyUrl);
                                albums.add(albumForDisplay);
                                getAlbumPhotos(albumForDisplay, thumbnailUrl, imageUrl);
                            }
                            adapter = new RVAdapter(albums);
                            rv.setAdapter(adapter);
                            adapter.setOnItemClickListener(new RVAdapter.OnRecyclerViewItemClickListener(){
                                @Override
                                public void onItemClick(View view , String data){
                                    Uri uri = Uri.parse(data);
                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                    startActivity(intent);
                                }
                            });
                        } catch (JSONException e){
                            Log.e("error", e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Log.e("error", error.toString());
                    }
                });

        // Add the request to the RequestQueue.
        mRequestQueue.add(stringRequest);
    }

    private void getAlbumPhotos(final Album album, String thumbnailUrl, String imageUrl){
        ImageRequest thumbnailRequest = new ImageRequest(thumbnailUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                        album.thumbnail = drawable;
                        adapter.notifyDataSetChanged();
                    }
                }, 0, 0, ImageView.ScaleType.CENTER, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error", error.toString());
                        //mImageView.setImageResource(R.drawable.image_load_error);
                    }
                });
        mRequestQueue.add(thumbnailRequest);

        ImageRequest albumPhoto = new ImageRequest(imageUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                        album.albumPhoto = drawable;
                        adapter.notifyDataSetChanged();
                    }
                }, 0, 0, ImageView.ScaleType.CENTER, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error", error.toString());
                        //mImageView.setImageResource(R.drawable.image_load_error);
                    }
                });
        // Access the RequestQueue through your singleton class.
        mRequestQueue.add(albumPhoto);
    }

}

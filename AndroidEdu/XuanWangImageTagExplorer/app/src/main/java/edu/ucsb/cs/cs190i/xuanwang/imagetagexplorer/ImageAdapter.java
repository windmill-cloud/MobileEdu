package edu.ucsb.cs.cs190i.xuanwang.imagetagexplorer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import edu.ucsb.cs.cs190i.xuanwang.imagetagexplorer.models.ImageItem;

/**
 * Created by xuanwang on 5/7/17.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
  private List<ImageItem> content = new ArrayList<>();
  private Context mContext;

  public ImageAdapter(Context context){
    mContext = context;
  }

  public void addContent(ImageItem image){
    content.add(image);
    this.notifyDataSetChanged();
  }

  public void clearContent(){
    content.clear();
    this.notifyDataSetChanged();
  }

  public void setContent(List<ImageItem> list){
    content.clear();
    content.addAll(list);
    this.notifyDataSetChanged();
  }

  @Override
  public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.card_image, parent, false);

    return new ViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(ImageAdapter.ViewHolder holder, int position) {
    ImageItem item = content.get(position);
    String path = item.getPath();
    File file = new File(path);
    if(!file.exists()){
      String[] segments = path.split("/");
      String fileName = segments[segments.length - 1];
      file = new File(mContext.getExternalFilesDir(null), fileName);
    }
    Picasso.with(mContext).load(file).resize(800, 800).centerCrop().into(holder.pic);
  }

  @Override
  public int getItemCount() {
    return content.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder{
    public ImageView pic;

    public ViewHolder(View view) {
      super(view);
      pic = (ImageView) view.findViewById(R.id.card_image);
    }
  }
}

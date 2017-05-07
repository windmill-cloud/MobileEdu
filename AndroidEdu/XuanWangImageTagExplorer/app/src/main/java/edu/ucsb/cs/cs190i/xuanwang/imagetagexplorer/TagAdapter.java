package edu.ucsb.cs.cs190i.xuanwang.imagetagexplorer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuanwang on 5/7/17.
 */

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder>
    implements View.OnClickListener {

  Context mContext;
  List<String> tags = new ArrayList<>();
  private OnRecyclerViewItemClickListener mOnItemClickListener = null;

  /**
   * Item click
   */

  //define Item click interface
  public interface OnRecyclerViewItemClickListener {
    void onItemClick(View view, int position);
  }

  @Override
  public void onClick(View view) {
    if (mOnItemClickListener != null) {
      // get tag
      mOnItemClickListener.onItemClick(view, (int) view.getTag());
    }
  }

  public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
    this.mOnItemClickListener = listener;
  }

  public TagAdapter(Context context){
    mContext = context;
  }

  public void setData(List<String> tags){
    this.tags = tags;
    this.notifyDataSetChanged();
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.card_tag, parent, false);

    return new TagAdapter.ViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.text.setText(tags.get(position));
    holder.text.setTag(position);
    holder.text.setOnClickListener(this);
  }

  @Override
  public int getItemCount() {
    return tags.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder{
    public TextView text;

    public ViewHolder(View view) {
      super(view);
      text = (TextView) view.findViewById(R.id.card_tag);
    }
  }
}

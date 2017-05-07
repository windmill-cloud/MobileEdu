package edu.ucsb.cs.cs190i.xuanwang.imagetagexplorer;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.ucsb.cs.cs190i.xuanwang.imagetagexplorer.models.ImageItem;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditTagFragment extends DialogFragment {

  public static final int NEW_ENTRY = 0;
  public static final int EDIT_ENTRY = 1;

  @BindView(R.id.image_dialog)
  ImageView mImageView;

  @BindView(R.id.edit_tag)
  AutoCompleteTextView editTag;

  @BindView(R.id.tag_rv)
  RecyclerView tagRecycler;

  TagAdapter tagAdapter;

  Set<String> tagSet = new HashSet<>();
  List<String> tagList = new ArrayList<>();

  public EditTagFragment() {
    // Required empty public constructor
  }

  public static EditTagFragment newInstance(Uri uri) {
    EditTagFragment frag = new EditTagFragment();
    Bundle args = new Bundle();
    args.putInt("type", NEW_ENTRY);
    args.putParcelable("uri", uri);
    frag.setArguments(args);
    return frag;
  }

  public static EditTagFragment newInstance(ImageItem imgItem) {
    EditTagFragment frag = new EditTagFragment();
    Bundle args = new Bundle();
    args.putInt("type", EDIT_ENTRY);
    args.putParcelable("uri", Uri.parse(imgItem.getPath()));
    args.putString("id", imgItem.getId());
    frag.setArguments(args);
    return frag;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.fragment_edit_tag, container, false);
    ButterKnife.bind(this, rootView);
    return rootView;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    int type = getArguments().getInt("type");
    Uri uri = getArguments().getParcelable("uri");
    Picasso.with(getContext()).load(uri).resize(700, 700).centerCrop().into(mImageView);

    final String[] tags = ImageTagDatabaseHelper.getInstance().getAllTagsFromDb();
    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
        android.R.layout.simple_list_item_1, tags);
    editTag.setAdapter(adapter);

    tagAdapter = new TagAdapter(getContext());
    tagRecycler.setAdapter(tagAdapter);
    LinearLayoutManager layoutManager
        = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
    tagRecycler.setLayoutManager(layoutManager);
    tagAdapter.setOnItemClickListener(new TagAdapter.OnRecyclerViewItemClickListener() {
      @Override
      public void onItemClick(View view, int position) {
        String tag = tagList.get(position);
        tagSet.remove(tag);
        tagList.remove(position);
        tagAdapter.setData(tagList);
      }
    });

    switch (type){
      case NEW_ENTRY:
        break;
      case EDIT_ENTRY:
        String imageId = getArguments().getString("id");
        break;
    }

    tagAdapter.setData(tagList);
  }

  @OnClick(R.id.button_add_tag)
  public void addTag(View view) {
    String tag = editTag.getText().toString();
    String pattern = "^\\s*$";
    Pattern r = Pattern.compile(pattern);
    Matcher m = r.matcher(tag);

    if(!m.find()){
      tagSet.add(tag);
      tagList = new ArrayList<>(tagSet);
      tagAdapter.setData(tagList);
      editTag.getText().clear();
    }
  }
}

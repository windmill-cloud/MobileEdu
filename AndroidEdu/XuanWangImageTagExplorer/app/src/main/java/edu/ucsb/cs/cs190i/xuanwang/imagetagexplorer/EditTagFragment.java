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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.ucsb.cs.cs190i.xuanwang.imagetagexplorer.models.EditTagTracker;
import edu.ucsb.cs.cs190i.xuanwang.imagetagexplorer.models.ImageItem;
import edu.ucsb.cs.cs190i.xuanwang.imagetagexplorer.models.TagWithId;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditTagFragment extends DialogFragment {

  public static final int NEW_ENTRY = 0;
  public static final int EDIT_ENTRY = 1;
  public static final int CAMERA = 2;
  String path;

  @BindView(R.id.image_dialog)
  ImageView mImageView;

  @BindView(R.id.edit_tag)
  AutoCompleteTextView editTag;

  @BindView(R.id.tag_rv)
  RecyclerView tagRecycler;

  @BindView(R.id.button_post)
  Button postButton;

  TagAdapter tagAdapter;

  Set<String> tagSet = new HashSet<>();
  List<String> tagList = new ArrayList<>();
  Map<String, EditTagTracker> editTagTracker = new HashMap<>();
  Map<String, String> tagIdMap = new HashMap<>();

  public EditTagFragment() {
    // Required empty public constructor
  }

  public static EditTagFragment newInstance(String path) {
    EditTagFragment frag = new EditTagFragment();
    Bundle args = new Bundle();
    args.putInt("type", NEW_ENTRY);
    args.putString("uri", path);
    frag.setArguments(args);
    return frag;
  }

  public static EditTagFragment newInstance(Uri uri) {
    EditTagFragment frag = new EditTagFragment();
    Bundle args = new Bundle();
    args.putInt("type", CAMERA);
    args.putParcelable("uri", uri);
    frag.setArguments(args);
    return frag;
  }

  public static EditTagFragment newInstance(ImageItem imgItem) {
    EditTagFragment frag = new EditTagFragment();
    Bundle args = new Bundle();
    args.putInt("type", EDIT_ENTRY);
    args.putString("uri", imgItem.getPath());
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
    if(type != CAMERA) {
      path = getArguments().getString("uri");

      File file = new File(path);
      if(!file.exists()){
        String[] segments = path.split("/");
        String fileName = segments[segments.length - 1];
        file = new File(getActivity().getExternalFilesDir(null), fileName);
      }

      Picasso.with(getContext()).load(file).resize(700, 700).centerCrop().into(mImageView);
    }else {
      Uri uri = getArguments().getParcelable("uri");
      Picasso.with(getContext()).load(uri).resize(700, 700).centerCrop().into(mImageView);
    }
    //final String[] tags = ImageTagDatabaseHelper.getInstance().getAllTagsFromDb();

    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
        android.R.layout.simple_list_item_1, ((MainActivity)getActivity()).tags);
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
        EditTagTracker ett = editTagTracker.get(tag);
        switch (ett.getType()){
          case EditTagTracker.NEW:
            editTagTracker.remove(tag);
            break;
          case EditTagTracker.OLD:
            ett.setType(EditTagTracker.DELETE);
            editTagTracker.put(tag, ett);
            break;
        }
      }
    });

    editTag.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String tag = charSequence.toString();
        if(((MainActivity)getActivity()).tagSet.contains(tag) &&
            !tagSet.contains(tag)){
          tagSet.add(charSequence.toString());
          tagList = new ArrayList<>(tagSet);
          tagAdapter.setData(tagList);

          String id = UUID.randomUUID().toString();
          if(tagIdMap.containsKey(tag)){
            id = tagIdMap.get(tag);
          }

          editTagTracker.put(
              tag,
              new EditTagTracker(
                  id,
                  EditTagTracker.NEW
              )
          );
          editTag.getText().clear();
        }
      }

      @Override
      public void afterTextChanged(Editable editable) {

      }
    });

    tagIdMap = ImageTagDatabaseHelper.getInstance().getTagsIdMap();

    switch (type){
      case NEW_ENTRY:
        postButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            //String path = MainActivity.getRealPathFromURI(getActivity(), path);
            //File f = new File(path);
            //Uri absoluteUri = Uri.fromFile(f);

            ImageItem newImageItem = new ImageItem(UUID.randomUUID().toString(), path);
            ImageTagDatabaseHelper.getInstance().addTaggedImage(newImageItem, tagList);
            MainActivity ma = (MainActivity) getActivity();
            ma.tags = ImageTagDatabaseHelper.getInstance().getAllTagsFromDb();
            ma.tagSet.clear();
            for(String tag :ma.tags){
              tagSet.add(tag);
            }
            ma.imageList.add(newImageItem);
            ma.imageAdapter.notifyDataSetChanged();
            dismiss();
          }
        });
        break;
      case EDIT_ENTRY:
        String imageId = getArguments().getString("id");
        List<TagWithId> twis = ImageTagDatabaseHelper.getInstance().getTagsByImageId(imageId);

        for(TagWithId twi: twis) {
          String tag = twi.getTag();
          tagSet.add(tag);
          editTagTracker.put(tag, new EditTagTracker(twi.getId(), EditTagTracker.OLD));
        }
        tagList = new ArrayList<>(tagSet);
        postButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            dismiss();
          }
        });
        break;
      case CAMERA:
        postButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            dismiss();
          }
        });
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
      String id = UUID.randomUUID().toString();
      if(tagIdMap.containsKey(tag)){
        tagIdMap.get(tag);
      }

      editTagTracker.put(
          tag,
          new EditTagTracker(
              id,
              EditTagTracker.NEW
          )
      );
      tagList = new ArrayList<>(tagSet);
      tagAdapter.setData(tagList);
      editTag.getText().clear();
    }
  }
}

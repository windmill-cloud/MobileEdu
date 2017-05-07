package edu.ucsb.cs.cs190i.xuanwang.imagetagexplorer;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditTagFragment extends DialogFragment {
  @BindView(R.id.image_dialog) ImageView mImageView;

  public EditTagFragment() {
    // Required empty public constructor
  }

  public static EditTagFragment newInstance(Uri uri) {
    EditTagFragment frag = new EditTagFragment();
    Bundle args = new Bundle();
    args.putParcelable("uri", uri);
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

    // Fetch arguments from bundle and set title
    Uri uri = getArguments().getParcelable("uri");
    // Show soft keyboard automatically and request focus to field
    Picasso.with(getContext()).load(uri).into(mImageView);
    //mEditText.requestFocus();
    //getDialog().getWindow().setSoftInputMode(
     //   WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

  }
}

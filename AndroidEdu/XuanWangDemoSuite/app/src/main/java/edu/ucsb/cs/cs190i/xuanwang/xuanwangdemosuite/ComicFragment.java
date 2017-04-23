package edu.ucsb.cs.cs190i.xuanwang.xuanwangdemosuite;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ComicFragment extends SavableFragment {


  public ComicFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_comic, container, false);
  }

  @Override
  public void saveState(Bundle bundle) {

  }

  @Override
  public void restoreState(Bundle bundle) {

  }
}

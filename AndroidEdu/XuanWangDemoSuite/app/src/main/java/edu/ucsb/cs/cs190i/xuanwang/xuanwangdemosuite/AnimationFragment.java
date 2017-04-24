package edu.ucsb.cs.cs190i.xuanwang.xuanwangdemosuite;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import edu.ucsb.cs.cs190i.xuanwang.xuanwangdemosuite.bouncingball.BouncingBallView;
import edu.ucsb.cs.cs190i.xuanwang.xuanwangdemosuite.bouncingball.DrawingThread;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnimationFragment extends SavableFragment {


  public AnimationFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.fragment_animation, container, false);
    View bbv = new BouncingBallView(getContext());
    FrameLayout fl = (FrameLayout) rootView.findViewById(R.id.animationFragment);

    //BouncingBallView bbv = (BouncingBallView) rootView.findViewById(R.id.bouncingBall);
    fl.addView(bbv);
    DrawingThread drawingThread = new DrawingThread(bbv, 50);
    drawingThread.start();

    return rootView;
  }

  @Override
  public void saveState(Bundle bundle) {

  }

  @Override
  public void restoreState(Bundle bundle) {

  }
}

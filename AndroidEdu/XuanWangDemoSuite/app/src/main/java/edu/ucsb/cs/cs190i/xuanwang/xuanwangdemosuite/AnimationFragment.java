package edu.ucsb.cs.cs190i.xuanwang.xuanwangdemosuite;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

  private float x = 120f;
  private float y = 140f;

  private float speedx = 10f;
  private float speedy = 10f;

  private BouncingBallView bbv;

  public AnimationFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.fragment_animation, container, false);
    bbv = new BouncingBallView(getContext());
    bbv.setBallPosition(x, y);
    bbv.setBallSpeed(speedx, speedy);

    FrameLayout fl = (FrameLayout) rootView.findViewById(R.id.animationFragment);

    fl.addView(bbv);
    DrawingThread drawingThread = new DrawingThread(bbv, 50);
    drawingThread.start();

    return rootView;
  }

  @Override
  public void saveState(Bundle bundle) {
    float[] pos = bbv.getBallPosition();
    bundle.putFloat("x", pos[0]);
    bundle.putFloat("y", pos[1]);

    float[] speed = bbv.getBallSpeed();
    bundle.putFloat("speedx", speed[0]);
    bundle.putFloat("speedy", speed[1]);
  }

  @Override
  public void restoreState(Bundle bundle) {
    if(bundle != null){
      x = bundle.getFloat("x");
      y = bundle.getFloat("y");
      speedx = bundle.getFloat("speedx");
      speedy = bundle.getFloat("speedy");
    }
  }
}

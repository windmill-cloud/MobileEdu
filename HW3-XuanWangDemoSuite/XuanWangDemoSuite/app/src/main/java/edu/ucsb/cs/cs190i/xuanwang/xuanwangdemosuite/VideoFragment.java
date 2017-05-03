package edu.ucsb.cs.cs190i.xuanwang.xuanwangdemosuite;


import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;


/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends SavableFragment {
  VideoView bigBuckVideo;
  static final String POSITION = "POSITION";
  int position = 0;

  public VideoFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.fragment_video, container, false);

    bigBuckVideo = (VideoView) rootView.findViewById(R.id.videoView);

    getActivity().getWindow().setFormat(PixelFormat.TRANSLUCENT);
    bigBuckVideo.setMediaController(new MediaController(getContext()));
    Uri video = Uri.parse("android.resource://" + getActivity().getPackageName() + "/"
        + R.raw.bigbuck); //do not add any extension
    bigBuckVideo.setVideoURI(video);
    bigBuckVideo.seekTo(position);
    bigBuckVideo.start();

    return rootView;
  }

  @Override
  public void saveState(Bundle bundle) {
    bundle.putInt(POSITION, bigBuckVideo.getCurrentPosition());
  }

  @Override
  public void restoreState(Bundle bundle) {
    if (bundle != null) {
      position = bundle.getInt(POSITION);
    }
  }
}

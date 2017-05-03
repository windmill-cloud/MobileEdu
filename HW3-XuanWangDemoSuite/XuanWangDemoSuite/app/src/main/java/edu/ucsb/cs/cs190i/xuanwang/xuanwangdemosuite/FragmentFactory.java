package edu.ucsb.cs.cs190i.xuanwang.xuanwangdemosuite;

import static edu.ucsb.cs.cs190i.xuanwang.xuanwangdemosuite.FragmentType.ANIMATION;
import static edu.ucsb.cs.cs190i.xuanwang.xuanwangdemosuite.FragmentType.COMIC;
import static edu.ucsb.cs.cs190i.xuanwang.xuanwangdemosuite.FragmentType.SPEECH_TO_TEXT;
import static edu.ucsb.cs.cs190i.xuanwang.xuanwangdemosuite.FragmentType.TEXT_TO_SPEECH;
import static edu.ucsb.cs.cs190i.xuanwang.xuanwangdemosuite.FragmentType.VIDEO;

/**
 * Created by Samuel on 4/20/2017.
 */

public class FragmentFactory {
  public static SavableFragment createFragment(String fragment) {
    switch (fragment) {
      case SPEECH_TO_TEXT:
        return new SpeechToTextFragment();
      case TEXT_TO_SPEECH:
        return new TextToSpeechFragment();
      case COMIC:
        return new ComicFragment();
      case VIDEO:
        return new VideoFragment();
      case ANIMATION:
        return new AnimationFragment();
      default:
        return null;
    }
  }
}

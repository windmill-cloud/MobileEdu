package edu.ucsb.ece.ece150.demos.demosuite;

import static edu.ucsb.ece.ece150.demos.demosuite.FragmentType.SPEECH_TO_TEXT;
import static edu.ucsb.ece.ece150.demos.demosuite.FragmentType.TEXT_TO_SPEECH;

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
      default:
        return null;
    }
  }
}

package edu.ucsb.cs.cs190i.xuanwang.xuanwangdemosuite;


import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class TextToSpeechFragment extends SavableFragment {
  TextToSpeech textToSpeech;

  public TextToSpeechFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment

    View rootView = inflater.inflate(R.layout.fragment_text_to_speech, container, false);

    final EditText editText = (EditText) rootView.findViewById(R.id.editTextToSpeech);

    textToSpeech = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
      @Override
      public void onInit(int status) {
        if (status != TextToSpeech.ERROR) {
          textToSpeech.setLanguage(Locale.UK);
        }
      }
    });

    ImageButton speakButton = (ImageButton) rootView.findViewById(R.id.buttonTextToSpeech);
    speakButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String toSpeak = editText.getText().toString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
          textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, null);
        }else{
          textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
        }
      }
    });

    return rootView;
  }

  @Override
  public void saveState(Bundle bundle) {

  }

  @Override
  public void restoreState(Bundle bundle) {

  }
}

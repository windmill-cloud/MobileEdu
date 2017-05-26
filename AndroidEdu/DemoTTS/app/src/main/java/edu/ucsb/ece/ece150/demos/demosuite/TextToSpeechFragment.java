package edu.ucsb.ece.ece150.demos.demosuite;


import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class TextToSpeechFragment extends SavableFragment {
  private TextToSpeech textToSpeech;
  private EditText editText;
  private String savedText = "";
  private boolean ttsReady = false;

  public TextToSpeechFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment

    View rootView = inflater.inflate(R.layout.fragment_text_to_speech, container, false);

    editText = (EditText) rootView.findViewById(R.id.editTextToSpeech);
    editText.setText(savedText);
    textToSpeech = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
      @Override
      public void onInit(int status) {
        if (status != TextToSpeech.ERROR) {
          textToSpeech.setLanguage(Locale.getDefault());
          ttsReady = true;
        }
      }
    });

    ImageButton speakButton = (ImageButton) rootView.findViewById(R.id.buttonTextToSpeech);
    speakButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String toSpeak = editText.getText().toString();
        if(ttsReady) {
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, null);
          }else{
            textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
          }
        }
      }
    });

    return rootView;
  }

  @Override
  public void saveState(Bundle bundle) {
    bundle.putString("savedText", editText.getText().toString());
  }

  @Override
  public void restoreState(Bundle bundle) {
    if(bundle != null) {
      savedText = bundle.getString("savedText");
    }
  }

  @Override
  public void onDestroy() {
    // Close the Text to Speech Library
    // to prevent service leak
    if(textToSpeech != null) {

      textToSpeech.stop();
      textToSpeech.shutdown();
    }
    super.onDestroy();
  }
}

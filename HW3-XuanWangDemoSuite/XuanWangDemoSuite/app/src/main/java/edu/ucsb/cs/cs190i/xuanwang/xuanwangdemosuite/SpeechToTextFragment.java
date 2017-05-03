package edu.ucsb.cs.cs190i.xuanwang.xuanwangdemosuite;


import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class SpeechToTextFragment extends SavableFragment {
  private static final String TextExtra = "Text";
  private TextView recognizedText;
  private String savedText = "";


  public SpeechToTextFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.fragment_speech_to_text, container, false);

    recognizedText = (TextView) rootView.findViewById(R.id.textSpeechToText);

    if (recognizedText != null && !savedText.equals("")) {
      recognizedText.setText(savedText);
    }

    ImageButton recogButton = (ImageButton) rootView.findViewById(R.id.buttonSpeechToText);
    recogButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        displaySpeechRecognizer();
      }
    });

    return rootView;
  }

  private static final int SPEECH_REQUEST_CODE = 0;

  // Create an intent that can start the Speech Recognizer activity
  private void displaySpeechRecognizer() {
    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
  // Start the activity, the intent will be populated with the speech text
    startActivityForResult(intent, SPEECH_REQUEST_CODE);
  }

  // This callback is invoked when the Speech Recognizer returns.
  // This is where you process the intent and extract the speech text from the intent.
  @Override
  public void onActivityResult(int requestCode, int resultCode,
                                  Intent data) {
    if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
      List<String> results = data.getStringArrayListExtra(
          RecognizerIntent.EXTRA_RESULTS);
      savedText = results.get(0);

      recognizedText.setText(savedText);
    }
    super.onActivityResult(requestCode, resultCode, data);
  }

  @Override
  public void saveState(Bundle bundle) {
    bundle.putString(TextExtra, recognizedText.getText().toString());
  }

  @Override
  public void restoreState(Bundle bundle) {
    if (bundle != null) {
      savedText = bundle.getString(TextExtra);
    }
  }
}

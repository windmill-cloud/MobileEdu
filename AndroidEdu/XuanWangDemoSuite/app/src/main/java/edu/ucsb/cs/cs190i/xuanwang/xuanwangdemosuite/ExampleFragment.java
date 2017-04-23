package edu.ucsb.cs.cs190i.xuanwang.xuanwangdemosuite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by Samuel on 4/21/2017.
 */

public class ExampleFragment extends SavableFragment {
    private static final String TextExtra = "Text";
    private String text;
    private EditText textField;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        textField = new EditText(getActivity());
        if (text != null) {
            textField.setText(text);
        }
        return textField;
    }

    @Override
    public void saveState(Bundle bundle) {
        bundle.putString(TextExtra, textField.getText().toString());
    }

    @Override
    public void restoreState(Bundle bundle) {
        if (bundle != null) {
            text = bundle.getString(TextExtra);
        }
    }
}
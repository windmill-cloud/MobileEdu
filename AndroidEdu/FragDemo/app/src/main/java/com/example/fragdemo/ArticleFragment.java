package com.example.fragdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ArticleFragment extends Fragment implements View.OnClickListener{

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button:
                Button btn = (Button) view;
                btn.setBackgroundColor(Color.BLUE);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.article_view, container, false);
        Button b = (Button) v.findViewById(R.id.button);
        b.setOnClickListener(this);

        return v;
    }
}
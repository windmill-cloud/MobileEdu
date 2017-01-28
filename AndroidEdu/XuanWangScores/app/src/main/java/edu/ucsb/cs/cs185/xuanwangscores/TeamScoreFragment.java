package edu.ucsb.cs.cs185.xuanwangscores;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TeamScoreFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TeamScoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeamScoreFragment extends Fragment {

    AutoCompleteTextView mTextView;
    EditText mScoreView;
    ArrayAdapter<String> adapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TEAM = "Team";

    // TODO: Rename and change types of parameters
    private String mParam;

    private OnFragmentInteractionListener mListener;

    public TeamScoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param Parameter.
     * @return A new instance of fragment TeamScoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeamScoreFragment newInstance(String param) {
        TeamScoreFragment fragment = new TeamScoreFragment();
        Bundle args = new Bundle();
        args.putString(TEAM, param);
        fragment.setString(param);
        return fragment;
    }

    void setString(String str){
        this.mParam = str;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_team_score, container, false);
        mTextView = (AutoCompleteTextView) view.findViewById(R.id.editTeam);
        mScoreView = (EditText) view.findViewById(R.id.editScore);
        if (getArguments() != null) {
            mParam = getArguments().getString(TEAM);
        }
        mTextView.setHint(mParam);
        mTextView.setAdapter(adapter);
        return view;
    }

    public void setContext(Context context, String[] teamNames){
         adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_list_item_1, teamNames);

    }

    public void changeTextProperties() {
        mTextView.setText("");
        mScoreView.setText("");
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

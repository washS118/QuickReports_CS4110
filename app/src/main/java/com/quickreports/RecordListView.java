package com.quickreports;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.quickreports.Managers.DatabaseManager;
import com.quickreports.Models.ReportModel;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecordListView.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecordListView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecordListView extends Fragment {
    private OnFragmentInteractionListener mListener;
    private Button btnCreateReport;
    DatabaseManager db;
    ReportModel[] reportModelArray;
    ListView lstReport;

    public RecordListView() {
        // Required empty public constructor

    }

    // TODO: Rename and change types and number of parameters
    public static RecordListView newInstance() {
        RecordListView fragment = new RecordListView();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public void onStart(){
        db = new DatabaseManager(getActivity());
        reportModelArray = db.getAllReports();
        btnCreateReport = getView().findViewById(R.id.btnCreateReport);
        btnCreateReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadRecordEditView();
            }
        });


        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_record_list_view, container, false);
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

    private void LoadRecordEditView(){
        ((MainActivity)getActivity()).LoadRecordEditView(0);
    }
}

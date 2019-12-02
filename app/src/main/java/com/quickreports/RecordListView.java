package com.quickreports;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
public class RecordListView extends QuickReportsView implements AdapterView.OnItemClickListener, View.OnClickListener{
    private static final String LogTag = "QuickReports-List";

    private Button btnCreateReport;
    private DatabaseManager db;
    private ReportModel[] reportModelArray;
    private ListView lstReport;

    //region Constructors
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
    //endregion

    //region Lifecycle
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_record_list_view, container, false);
    }

    //endregion

    //region Main Logic
    protected void SetupFragment() {
        context = getActivity();

        //region Get Form Elements
        View v = getView();
        btnCreateReport = v.findViewById(R.id.btnCreateReport);
        lstReport = v.findViewById(R.id.lstReports);
        //endregion

        //region Get Data
        db = new DatabaseManager(context);
        reportModelArray = db.getAllReports();
        Log.println(Log.DEBUG, LogTag, "Report Count = " + reportModelArray.length);
        //endregion

        //region Set Click Listeners
        lstReport.setOnItemClickListener(this);
        btnCreateReport.setOnClickListener(this);
        //endregion

        //region Configure List
        ArrayAdapter<ReportModel> adapter = new ArrayAdapter<ReportModel>(context, R.layout.adapter_view_layout, R.id.txtContent, reportModelArray);
        lstReport.setAdapter(adapter);
        //endregion
    }

    private void LoadRecordEditView(int id){
        ((MainActivity)getActivity()).LoadRecordEditView(id);
    }

    /**
     * Handles button clicks on the screen
     * @param v
     */
    @Override
    public void onClick(View v) {
        LoadRecordEditView(0);
    }

    /**
     * Handles clicking an item in the list view
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ReportModel item = (ReportModel) lstReport.getItemAtPosition(position);
        Log.println(Log.DEBUG, LogTag, "Report Selected\n" +
                "\tTitle = " + item.title + "\n" +
                "\tId = " + item.id);

        LoadRecordEditView(item.id);
    }
    //endregion
}

package com.quickreports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.net.Uri;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements QuickReportsView.OnFragmentInteractionListener {
    private static String LogTag = "QuickReports-MainActivity";
    private FragmentManager fragmentManager;
    private QuickReportsView currentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        LoadRecordListView();
    }

    public void LoadRecordListView(){
        SetFragment(RecordListView.newInstance());
    }

    public void LoadRecordEditView(int reportId){
        SetFragment(RecordEditView.newInstance(reportId));
    }

    private void SetFragment(QuickReportsView fragment){
        try {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
            currentView = fragment;
        } catch (Exception e) {
            Log.println(Log.ERROR, LogTag, "Set Fragment Error\n" + e.getMessage());
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

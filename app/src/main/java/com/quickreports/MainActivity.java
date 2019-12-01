package com.quickreports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.net.Uri;
import android.database.Cursor;
import android.net.Uri;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.quickreports.Managers.DatabaseManager;
import com.quickreports.Models.ReportModel;

public class MainActivity extends AppCompatActivity implements RecordEditView.OnFragmentInteractionListener, RecordListView.OnFragmentInteractionListener {
    private static String LogTag = "QuickReports-MainActivity";
    private FragmentManager fragmentManager;

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

    private void SetFragment(Fragment fragment){
        try {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        } catch (Exception e) {
            Log.println(Log.ERROR, LogTag, "Set Fragment Error\n" + e.getMessage());
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

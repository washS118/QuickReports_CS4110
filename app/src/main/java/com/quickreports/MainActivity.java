package com.quickreports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.quickreports.Managers.DatabaseManager;

public class MainActivity extends AppCompatActivity implements RecordEditView.OnFragmentInteractionListener, RecordListView.OnFragmentInteractionListener {
    private static String LogTag = "QuickReports-MainActivity";
    FragmentManager fragmentManager;
    Button btnCreateReport;
    Button viewReports;
    DatabaseManager reportDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        SetFragment(new RecordEditView());
        reportDB = new DatabaseManager(this);
    }

    public void LoadRecordListView(){
        SetFragment(new RecordListView());
    }

    public void LoadRecordEditView(){
        SetFragment(new RecordEditView());
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

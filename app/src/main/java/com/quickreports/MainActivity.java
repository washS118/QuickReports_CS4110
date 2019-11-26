package com.quickreports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

    public void addReportData() {
        btnCreateReport.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //reportDB.addReport(reportTitle.getText(), reportDescription.getText(), getTimeInString(), getPathOfPhoto());
                        //reportDB.addWeatherReport(getWeatherCondition(), getTemperature());
                    }
                }
        );
    }

    public void viewReportData() {
        viewReports.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       Cursor res = reportDB.getAllReports();
                       if(res.getCount() == 0) {
                           //show message
                           return;
                       }
                       StringBuffer buffer = new StringBuffer();
                       while(res.moveToNext()) {
                           buffer.append("Report ID: "+ res.getString(0)+"\n");
                           buffer.append("Title: "+res.getString(1)+"\n");
                           buffer.append("Description: "+res.getString(2)+"\n");
                           buffer.append("Time Submitted: "+res.getString(3)+"\n");
                           buffer.append("Photo: "+res.getString(4)+"\n");
                           buffer.append("WeatherId: "+res.getString(5)+"\n");
                           buffer.append("Weather Condition: "+res.getString(6)+"\n");
                           buffer.append("Temperature: "+res.getString(7)+"\n");
                       }
                    }
                }
        );
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

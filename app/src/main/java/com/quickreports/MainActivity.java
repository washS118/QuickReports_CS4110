package com.quickreports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Button;

import com.quickreports.Managers.DatabaseManager;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    Button btnCreateReport;
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
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}

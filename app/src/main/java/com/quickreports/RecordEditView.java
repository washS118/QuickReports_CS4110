package com.quickreports;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.quickreports.Managers.ApiError;
import com.quickreports.Managers.ApiSuccess;
import com.quickreports.Managers.CameraManager;
import com.quickreports.Managers.WeatherManager;
import com.quickreports.Models.WeatherModel;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static androidx.core.content.ContextCompat.checkSelfPermission;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecordEditView.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecordEditView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecordEditView extends Fragment {
    private static String LogTag = "QuickReports-Edit";
    private OnFragmentInteractionListener mListener;
    private Context context;
    CameraManager camera;

    private ImageView imgPhoto;

    public RecordEditView() {
        // Required empty public constructor
    }

    public static RecordEditView newInstance() {
        RecordEditView fragment = new RecordEditView();
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
        context = getActivity();
        camera = new CameraManager(this);

        if (checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 123);

            if (checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                super.onStart();
                return;
            }


        }

        imgPhoto = (ImageView) getView().findViewById(R.id.imgPhoto);
        imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    camera.TakePicture();
                } catch (IOException e){
                    Log.println(Log.ERROR, LogTag, e.getStackTrace().toString());
                }
            }
        });

        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        WeatherManager weather = new WeatherManager();

        weather.SetSuccessFunction(new ApiSuccess() {
            @Override
            public void success(WeatherModel model) {
                Log.println(Log.DEBUG, LogTag, "Weather Success");
            }
        });

        weather.SetErrorFunction(new ApiError() {
            @Override
            public void error(String message) {
                Log.println(Log.ERROR, LogTag, "Weather Error:" + message);
            }
        });

<<<<<<< Updated upstream
        Log.println(Log.ERROR, LogTag, "Start Request");
        weather.GetWeatherData(location);
=======
        Log.println(Log.ERROR, "Edit", "Start Request");
        weather.GetWeatherData(location);
>>>>>>> Stashed changes

        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_record_edit_view, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            //throw new RuntimeException(context.toString()
            //        + " must implement OnFragmentInteractionListener");
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

    private void LoadRecordListView(){
        ((MainActivity)getActivity()).LoadRecordListView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == camera.GetRequestCode() && resultCode == RESULT_OK) {
            Log.println(Log.DEBUG, LogTag, "Recieved Camera Result");
            camera.galleryAddPic();
            Log.println(Log.DEBUG, LogTag, "Posted pic to gallery");
            Bundle extras = data.getExtras();
            if (extras == null) {
                Log.println(Log.ERROR, LogTag, "Image data empty");
                return;
            }

            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgPhoto.setImageBitmap(imageBitmap);

        }
    }
}

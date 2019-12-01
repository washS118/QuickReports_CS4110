package com.quickreports;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.quickreports.Managers.ApiError;
import com.quickreports.Managers.ApiSuccess;
import com.quickreports.Managers.CameraManager;
import com.quickreports.Managers.DatabaseManager;
import com.quickreports.Managers.WeatherManager;
import com.quickreports.Models.ReportModel;
import com.quickreports.Models.WeatherModel;

import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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

    //region Managers
    private CameraManager camera;
    private DatabaseManager database;
    private WeatherManager weather;
    //endregion

    //region Views
    private ImageView imgPhoto;
    private Button btnSave, btnBack;
    private EditText txtDate, txtTime;
    private SimpleDateFormat dateFormat, timeFormat;
    //endregion

    private int reportId;
    private ReportModel model = new ReportModel();

    //region Constructors
    public RecordEditView() {
        // Required empty public constructor
    }

    public static RecordEditView newInstance(int reportId) {
        RecordEditView fragment = new RecordEditView();
        Bundle args = new Bundle();
        args.putInt("reportId", reportId);
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
    public void onStart(){
        context = getActivity();
        camera = new CameraManager(this);


        txtDate = getView().findViewById(R.id.txtDate);
        txtTime = getView().findViewById(R.id.txtTime);
        txtDate.setEnabled(false);
        txtTime.setEnabled(false);

        dateFormat = new SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault());
        timeFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());

        String date = dateFormat.format(Calendar.getInstance().getTime());
        String time = timeFormat.format(Calendar.getInstance().getTime());

        txtDate.setText(date);
        txtTime.setText(time);

        btnSave = getView().findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "New Report is Saved.", Toast.LENGTH_SHORT).show();
                LoadRecordListView();
            }
        });

        btnBack = getView().findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadRecordListView();
            }
        });

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

        SetupWeather();
        GetWeather();

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
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    //endregion

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
        try {
            ((MainActivity)getActivity()).LoadRecordListView();
        } catch (Exception e) {
            Log.println(Log.ERROR, LogTag, "Load List View Fail.\n" + e.getMessage());
        }

    }

    //region Main Logic
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == camera.GetRequestCode() && resultCode == RESULT_OK) {
            Log.println(Log.DEBUG, LogTag, "Recieved Camera Result");

            camera.galleryAddPic();
            Log.println(Log.DEBUG, LogTag, "Posted pic to gallery");

            setPic();
            Log.println(Log.DEBUG, LogTag, "Loaded pic in app");

        }
    }

    private void setPic() {
        Log.println(Log.DEBUG, LogTag, "Set Picture");
        // Get the dimensions of the View
        int targetW = imgPhoto.getWidth();
        int targetH = imgPhoto.getHeight();

        Log.println(Log.DEBUG, LogTag, "Get bitmap size");
        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        Log.println(Log.DEBUG, LogTag, "calculate bitmap scale");
        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        Log.println(Log.DEBUG, LogTag, "Set bitmap size");
        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Log.println(Log.DEBUG, LogTag, "Load pic from device");
        Bitmap bitmap = BitmapFactory.decodeFile(camera.GetCurrentPhotoPath(), bmOptions);
        imgPhoto.setImageBitmap(bitmap);
    }

    private void SetupWeather() {

        weather = new WeatherManager();

        weather.SetSuccessFunction(new ApiSuccess() {
            @Override
            public void success(WeatherModel newModel) {
                Log.println(Log.DEBUG, LogTag, "Weather Success");
                model.weather = newModel;
            }
        });

        weather.SetErrorFunction(new ApiError() {
            @Override
            public void error(String message) {
                Log.println(Log.ERROR, LogTag, "Weather Error:" + message);
            }
        });
    }

    private void GetWeather(){
        Location location = GetLocation();
        if (location == null) return;

        Log.println(Log.ERROR, LogTag, "Start Request");
        weather.GetWeatherData(location);
        Log.println(Log.ERROR, "Edit", "Start Request");
        weather.GetWeatherData(location);
    }

    private Location GetLocation(){
        boolean hasPermission = true;

        Log.println(Log.DEBUG, LogTag, "Requesting Location Permission");
        if (checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 123);

            if (checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                hasPermission = false;
            }
        }

        if (!hasPermission){
            Log.println(Log.ERROR, LogTag, "Location Permission Denied");
        } else {
            Log.println(Log.DEBUG, LogTag, "Location Permission Granted");
        }

        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }
    //endregion
}

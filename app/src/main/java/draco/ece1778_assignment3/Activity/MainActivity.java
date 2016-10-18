package draco.ece1778_assignment3.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import draco.ece1778_assignment3.Classes.GridViewAdapter;
import draco.ece1778_assignment3.Fragment.ImageViewFragment;
import draco.ece1778_assignment3.R;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, ConnectionCallbacks, OnConnectionFailedListener{

    public final String TAG = "Main_Activity";

    public Context context = this;

    private GridView gridView;
    private GridViewAdapter gridAdapter;
    public static ArrayList<Uri> fileList = new ArrayList<>();
    public static int pos;

    private File FileDirectory;
    static final int CAMERA_REQUEST = 1;
    public String mCurrentPhotoPath;

    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    public String mLongitude;
    public String mLatitude;

    FloatingActionButton btn_camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FileDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath());

        for (File file:FileDirectory.listFiles()){
            if(file.getName().toLowerCase().endsWith(".jpg")){
                fileList.add(Uri.fromFile(file));
            }
        }


        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(this, R.layout.grid_view_cell, fileList);
        gridView.setAdapter(gridAdapter);

        btn_camera = (FloatingActionButton) findViewById(R.id.btn_camera);
        btn_camera.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getPackageManager()) != null){
                    File photoFile = null;
                    try{
                        photoFile = createImageFile();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(photoFile != null){
                        //Uri photoUri = FileProvider.getUriForFile(context, "draco.ece1778_assignment3.fileprovider",photoFile);
                        Uri photoUri = Uri.fromFile(photoFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                        startActivityForResult(intent, CAMERA_REQUEST);
                    }
                }

            }
        });

        gridView.setOnItemClickListener(this);
        buildGoogleApiClient();

    }

    protected  synchronized void buildGoogleApiClient(){
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CAMERA_REQUEST){
            File photoFile = null;
            Toast.makeText(this, mLongitude + " " + mLatitude, Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        pos = position;
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ImageViewFragment ivf = new ImageViewFragment();
        fragmentTransaction.add(R.id.activity_main, ivf);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() != 0)
            getFragmentManager().popBackStackImmediate();
        else
            super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
    }



    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            mLongitude = String.format("%.2f", mLastLocation.getLongitude());
            mLatitude = String.format("%.2f", mLastLocation.getLatitude());
        }else{
            Toast.makeText(this, R.string.no_location_detected, Toast.LENGTH_LONG).show();
            mLongitude = Double.toString(-79.10);
            mLatitude = Double.toString(43.10);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    private File createImageFile() throws IOException{
        //create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imgName = mLatitude + "_" + mLongitude + "_" + timeStamp;
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath());
        File image = new File(storageDir, imgName + ".jpg");
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

}

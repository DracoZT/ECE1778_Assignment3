package draco.ece1778_assignment3.Activity;

import android.content.Intent;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import java.io.File;
import draco.ece1778_assignment3.Classes.GridViewAdapter;
import draco.ece1778_assignment3.R;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private File[] fileList;
    private File FileDirectory;
    FloatingActionButton btn_camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FileDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath());
        fileList = this.getFilesDir().listFiles();
        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(this, R.layout.grid_view_cell, fileList);
        gridView.setAdapter(gridAdapter);

        btn_camera = (FloatingActionButton) findViewById(R.id.btn_camera);
        btn_camera.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivity(intent);
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}

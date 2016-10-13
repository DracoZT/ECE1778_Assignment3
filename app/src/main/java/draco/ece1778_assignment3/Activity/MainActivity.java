package draco.ece1778_assignment3.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import java.util.ArrayList;
import draco.ece1778_assignment3.Classes.GridViewAdapter;
import draco.ece1778_assignment3.Classes.ImageItem;
import draco.ece1778_assignment3.R;

public class MainActivity extends AppCompatActivity {
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    public ArrayList<ImageItem> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.gridView);
        //gridAdapter = new GridViewAdapter(this, R.layout.grid_view_cell, data);
        //gridView.setAdapter(gridAdapter);
    }
}

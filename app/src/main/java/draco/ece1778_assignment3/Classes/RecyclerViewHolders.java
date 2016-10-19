package draco.ece1778_assignment3.Classes;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.util.ArrayList;

import draco.ece1778_assignment3.Activity.ImageViewActivity;
import draco.ece1778_assignment3.Activity.MainActivity;
import draco.ece1778_assignment3.R;

/**
 * Created by Draco on 2016-10-19.
 */

public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView Location;
    public ImageView Photo;
    public ArrayList<Uri> filelist;

    public RecyclerViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        Location = (TextView)itemView.findViewById(R.id.location);
        Photo = (ImageView)itemView.findViewById(R.id.img);
    }


    @Override
    public void onClick(View v) {
        filelist = MainActivity.fileList;
        String photoUri_pos = Integer.toString(getLayoutPosition());
        Intent img_view = new Intent(v.getContext(), ImageViewActivity.class).putExtra("photo_pos", photoUri_pos);
        v.getContext().startActivity(img_view);
    }
}

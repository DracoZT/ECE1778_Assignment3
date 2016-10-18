package draco.ece1778_assignment3.Classes;

/**
 * Created by Draco on 2016-10-12.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import draco.ece1778_assignment3.R;
import java.io.File;
import java.util.ArrayList;

public class GridViewAdapter extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    private ArrayList<Uri> data;

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList<Uri> data){
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row= convertView;
        ViewHolder holder = null;
        File file = new File(data.get(position).getPath());
        ImageItem photo = null;


        if(row == null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageLocation = (TextView) row.findViewById(R.id.image_location);
            holder.image = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        }else
            holder = (ViewHolder) row.getTag();

        String path = file.getPath();
        Bitmap bitmap = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(path), 200, 200);
        holder.image.setImageBitmap(bitmap);
        return row;
    }

    class ViewHolder{
        TextView imageLocation;
        ImageView image;
    }

    public String fetchLocation(ImageItem photo){
        return (String.format("%f, %f", photo.latitude, photo.longitude));
    }

}

package draco.ece1778_assignment3.Classes;

/**
 * Created by Draco on 2016-10-12.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import draco.ece1778_assignment3.R;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class GridViewAdapter extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    private File[] data;

    public GridViewAdapter(Context context, int layoutResourceId, File[] data){
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row= convertView;
        ViewHolder holder = null;
        File file = data[position];
        ImageItem photo = null;

        try{
            FileInputStream fs = (getContext()).openFileInput(file.getName());
            ObjectInputStream os = new ObjectInputStream(fs);
            photo = (ImageItem) os.readObject();
            os.close();
            fs.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(row == null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageLocation = (TextView) row.findViewById(R.id.image_location);
            holder.image = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        }else
            holder = (ViewHolder) row.getTag();

        holder.image.setImageBitmap(fetchBitmap(photo));
        holder.imageLocation.setText(fetchLocation(photo));

        return row;
    }

    class ViewHolder{
        TextView imageLocation;
        ImageView image;
    }

    public String fetchLocation(ImageItem photo){
        return (String.format("%f, %f", photo.latitude, photo.longitude));
    }

    public Bitmap fetchBitmap(ImageItem photo){
        Bitmap imageBitmap;

        imageBitmap = BitmapFactory.decodeByteArray(photo.image, 0, photo.image.length);
        return imageBitmap;
    }
}

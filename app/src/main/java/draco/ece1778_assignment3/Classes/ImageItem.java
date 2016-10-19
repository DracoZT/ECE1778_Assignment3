package draco.ece1778_assignment3.Classes;

import android.net.Uri;

import java.io.File;
import java.io.Serializable;

/**
 * Created by Draco on 2016-10-13.
 */

public class ImageItem implements Serializable{
    public String file_name;
    public String latitude;
    public String longitude;

    public ImageItem(Uri photoUri){
        File photo = new File(photoUri.getPath());
        String name = photo.getName().substring(0, photo.getName().lastIndexOf('.'));
        String[] name_array = name.split("_");
        this.file_name = name_array[0];
        this.latitude = name_array[1];
        this.longitude = name_array[2];
    }

}

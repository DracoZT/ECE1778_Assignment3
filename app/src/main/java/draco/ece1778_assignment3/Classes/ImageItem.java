package draco.ece1778_assignment3.Classes;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Draco on 2016-10-13.
 */

public class ImageItem implements Serializable{
    public byte[] image;
    public double latitude;
    public double longitude;

    public ImageItem(byte[] image, double latitude, double longitude){
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}

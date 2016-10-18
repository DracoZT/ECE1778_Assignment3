package draco.ece1778_assignment3.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import draco.ece1778_assignment3.Activity.MainActivity;
import draco.ece1778_assignment3.R;

/**
 * Created by Draco on 2016-10-13.
 */

public class ImageViewFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View resultView = inflater.inflate(R.layout.image_view_fragment, container, false);

        ImageView imageView = (ImageView) resultView.findViewById(R.id.full_screen_img);
        imageView.setImageURI(MainActivity.fileList.get(MainActivity.pos));

        return resultView;
    }
}

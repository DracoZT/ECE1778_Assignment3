package draco.ece1778_assignment3.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import draco.ece1778_assignment3.R;

/**
 * Created by Draco on 2016-10-13.
 */

public class ImageViewFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View resultView = inflater.inflate(R.layout.image_view_fragment, container, false);

        
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}

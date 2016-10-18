package draco.ece1778_assignment3.Fragment;

import android.app.Fragment;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

import draco.ece1778_assignment3.Activity.MainActivity;
import draco.ece1778_assignment3.R;

/**
 * Created by Draco on 2016-10-13.
 */

public class ImageViewFragment extends Fragment{
    public Uri photo;
    public FirebaseStorage storage = null;
    public StorageReference storageRef = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View resultView = inflater.inflate(R.layout.image_view_fragment, container, false);

        ImageView imageView = (ImageView) resultView.findViewById(R.id.full_screen_img);
        imageView.setImageURI(MainActivity.fileList.get(MainActivity.pos));

        setHasOptionsMenu(true);

        photo = MainActivity.fileList.get(MainActivity.pos);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://assignment3-2dcbb.appspot.com");

        return resultView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuInflater mf = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Do you also wish to delete this photo from the cloud?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        File delete = new File(photo.getPath());
                        StorageReference delRef = storageRef.child("images/" + delete.getName());
                        delRef.delete();
                        delete.delete();
                        Toast.makeText(getActivity(), "Photo deleted.", Toast.LENGTH_SHORT);
                        getFragmentManager().popBackStackImmediate();
                    }
                });
                builder.setNegativeButton("NO,DELETE LOCAL COPY ONLY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        File delete = new File(photo.getPath());
                        delete.delete();
                        Toast.makeText(getActivity(), "Photo deleted.", Toast.LENGTH_SHORT);
                        getFragmentManager().popBackStackImmediate();
                    }
                });
                builder.setCancelable(true);
                builder.create().show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

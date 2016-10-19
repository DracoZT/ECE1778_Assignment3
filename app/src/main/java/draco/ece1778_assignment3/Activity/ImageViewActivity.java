package draco.ece1778_assignment3.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

import draco.ece1778_assignment3.R;

/**
 * Created by Draco on 2016-10-19.
 */

public class ImageViewActivity extends AppCompatActivity{

    private Uri photo;
    private ImageView imgView;
    public FirebaseStorage storage = null;
    public StorageReference storageRef = null;
    private Context ctx = this;
    public static boolean deleted = false;
    private int photo_pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_view);

        Bundle extras = getIntent().getExtras();
        if(extras != null && extras.containsKey("photo_pos")) {
            photo_pos = Integer.parseInt(extras.getString("photo_pos"));
            photo = MainActivity.fileList.get(photo_pos);
        }

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://assignment3-2dcbb.appspot.com");

        imgView = (ImageView) findViewById(R.id.fullsize_image);
        imgView.setImageURI(photo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mf = this.getMenuInflater();
        mf.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                final File delete = new File(photo.getPath());
                final String[] filename = delete.getName().split("_");
                if (!filename[0].equals("IMG")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                    builder.setMessage("Do you also wish to delete this photo from the cloud?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            StorageReference delRef = storageRef.child("images/" + delete.getName());
                            delRef.delete();
                            delete.delete();
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                            mDatabase.child(filename[0]).removeValue();
                            MainActivity.fileList.remove(photo_pos);
                            Toast.makeText(ctx, "Photo deleted.", Toast.LENGTH_SHORT);
                            finish();
                        }
                    });
                    builder.setNegativeButton("NO,DELETE LOCAL COPY ONLY", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            File delete = new File(photo.getPath());
                            delete.delete();
                            MainActivity.fileList.remove(photo_pos);
                            Toast.makeText(ctx, "Photo deleted.", Toast.LENGTH_SHORT);
                            finish();
                        }
                    });

                    builder.setCancelable(true);
                    builder.create().show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                    builder.setMessage("Local file cannot be deleted");
                    builder.setPositiveButton("Ok", null);
                    builder.setCancelable(true);
                    builder.show();
                    deleted = false;
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

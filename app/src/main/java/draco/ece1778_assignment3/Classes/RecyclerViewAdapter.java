package draco.ece1778_assignment3.Classes;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;

import draco.ece1778_assignment3.R;

/**
 * Created by Draco on 2016-10-19.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {
    private ArrayList<Uri> data;
    private Context context;

    public RecyclerViewAdapter(Context context, ArrayList<Uri> data){
        this.data = data;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_layout, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        holder.Photo.setImageURI(data.get(position));
        File file = new File(data.get(position).getPath());
        String name = file.getName();
        String new_name = name.substring(0, name.lastIndexOf('.'));
        String[] location = new_name.split("_");
        if(!location[0].equals("IMG"))
            holder.Location.setText("Lat: " + location[1] + " Long: " + location[2]);
        else
            holder.Location.setText(" ");
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }
}

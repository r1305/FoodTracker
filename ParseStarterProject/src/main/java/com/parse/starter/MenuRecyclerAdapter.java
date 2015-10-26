package com.parse.starter;

import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rogger on 26/10/2015.
 */
public class MenuRecyclerAdapter extends RecyclerView.Adapter<MenuRecyclerAdapter.ViewHolder>{


    List<ParseObject> list;

    View.OnClickListener listener;

    public MenuRecyclerAdapter(List<ParseObject> list) {
        this.list = list;
    }

    @Override
    public MenuRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false));
    }

    @Override
    public void onBindViewHolder(final MenuRecyclerAdapter.ViewHolder holder, int position) {

        ParseObject po = list.get(position);
        holder.title.setText(po.getString("menu"));
        holder.precio.setText(po.getString("precio"));
        ParseFile applicantResume = (ParseFile)po.get("foto");

        applicantResume.getDataInBackground(new GetDataCallback() {
            public void done(byte[] data, ParseException e) {
                if (e == null) {

                    holder.image.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));

                } else {
                    // something went wrong

                }
            }
        });
        holder.itemView.setTag(po);
        holder.itemView.setOnClickListener(listener);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.image_menu)
        ImageView image;
        @Bind(R.id.title_menu)
        TextView title;
        @Bind(R.id.precio)
        TextView precio;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

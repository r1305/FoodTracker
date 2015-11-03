package com.parse.starter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rogger on 02/11/2015.
 */
public class CuponesRecyclerAdapter  extends RecyclerView.Adapter<CuponesRecyclerAdapter.ViewHolder> {

    List<ParseObject> list=new ArrayList<>();

    View.OnClickListener listener;


    @Override
    public CuponesRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(CuponesRecyclerAdapter.ViewHolder holder, int position) {
        
        ParseObject po = list.get(position);
        holder.title.setText(po.getString("Truck"));
        holder.desc.setText(po.getString("Descuento"));
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

    }

    @Override
    public int getItemCount() {
        if(list==null){
            return 0;
        }else {
            return list.size();
        };
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.image_cupones)
        ImageView image;
        @Bind(R.id.title_cupon)
        TextView title;
        @Bind(R.id.descuento)
        TextView desc;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}

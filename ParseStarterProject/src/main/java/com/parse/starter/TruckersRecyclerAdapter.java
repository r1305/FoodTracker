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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TruckersRecyclerAdapter extends RecyclerView.Adapter<TruckersRecyclerAdapter.ViewHolder> {

    List<ParseObject> list=new ArrayList<>();

    View.OnClickListener listener;


    public TruckersRecyclerAdapter(List<ParseObject> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_truckers, parent, false));
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ParseObject po = list.get(position);
        holder.title.setText(po.getString("name"));
        holder.type.setText(po.getString("tipo"));
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
        if(list==null){
            return 0;
        }else {
            return list.size();
        }
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.image)
        ImageView image;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.type)
        TextView type;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}

package com.example.chaldea.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chaldea.R;
import com.example.chaldea.model.Servant;

import java.util.ArrayList;

public class ServantAdapter extends RecyclerView.Adapter<ServantAdapter.ServantViewHolder> {
    private ArrayList<Servant> listServant;
    private boolean isGrid = false;
    private static final int TYPE_LIST = 0;
    private static final int TYPE_GRID = 1;

    public ServantAdapter(ArrayList<Servant> list) {
        this.listServant = list;
    }

    public void setList(ArrayList<Servant> list) {
        this.listServant = list;
    }

    public void setViewType(boolean isGrid) {
        this.isGrid = isGrid;
    }

    @Override
    public int getItemViewType(int position) {
        return isGrid ? TYPE_GRID : TYPE_LIST;
    }

    @NonNull
    @Override
    public ServantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_GRID) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_servant, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_servant, parent, false);
        }
        return new ServantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServantViewHolder holder, int position) {
        Servant servant = listServant.get(position);
        
        Glide.with(holder.itemView.getContext())
                .load(servant.getPhoto())
                .placeholder(R.drawable.chulainn) // Fallback placeholder
                .into(holder.imgPhoto);

        holder.tvName.setText(servant.getName());
        holder.tvClass.setText(servant.getServantClass());
        if (holder.tvDescription != null) {
            holder.tvDescription.setText(Html.fromHtml(servant.getDescription(), Html.FROM_HTML_MODE_COMPACT));
        }
    }

    @Override
    public int getItemCount() {
        return listServant.size();
    }

    public static class ServantViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvName, tvDescription, tvClass;

        public ServantViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvClass = itemView.findViewById(R.id.tv_item_class);
            tvDescription = itemView.findViewById(R.id.tv_item_description);
        }
    }
}

package com.alex.gamecrush.zombiekiller;


import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.alex.gamecrush.R;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<ListElement> playerData;
    private LayoutInflater layoutInflater;
    private Context context;

    public ListAdapter(List<ListElement> itemList, Context context) {

        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.playerData = itemList;

    }

    @Override
    public int getItemCount() {
        return playerData.size();
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_element, null);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position) {
        holder.bindData(playerData.get(position));
    }

    public void setItems(List<ListElement> items) {
        playerData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView nombre, score;

        ViewHolder(View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            nombre = itemView.findViewById(R.id.nombreJugadorTextView);
            score = itemView.findViewById(R.id.scoreJugadorTextView);

        }

        void bindData(final ListElement item) {
            iconImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            nombre.setText(item.getName());
            score.setText(item.getScore());
        }
    }


}

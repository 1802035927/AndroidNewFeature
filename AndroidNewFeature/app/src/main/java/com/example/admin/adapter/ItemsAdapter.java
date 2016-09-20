package com.example.admin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.androidnewfeature.R;
import com.example.admin.model.Items;

import java.util.List;

/**
 * Created by admin on 2016/9/20.
 */
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<Items> itemsList;
    private ItemsOnclickListener itemsOnclickListener;

    public ItemsAdapter(Context context, List<Items> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.items_layout, parent, false);
        ItemsViewHolder viewHolder = new ItemsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ItemsViewHolder holder, final int position) {
        Items items = itemsList.get(position);
        holder.title.setText(items.getTitle());
        holder.content.setText(items.getContent());

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemsOnclickListener.onclick(v, position);
            }
        });
        holder.content.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                itemsOnclickListener.setOnLongClick(v, position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }


    public interface ItemsOnclickListener {
        void onclick(View view, int position);

        void setOnLongClick(View view, int position);
    }

    public void setItemsOnclickListener(ItemsOnclickListener itemsOnclickListener) {
        this.itemsOnclickListener = itemsOnclickListener;
    }

    public class ItemsViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView content;

        public ItemsViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.items_title);
            content = (TextView) itemView.findViewById(R.id.items_content);
        }

    }
}

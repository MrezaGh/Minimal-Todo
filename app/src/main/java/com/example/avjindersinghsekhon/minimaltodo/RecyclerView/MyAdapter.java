package com.example.avjindersinghsekhon.minimaltodo.RecyclerView;


import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.avjindersinghsekhon.minimaltodo.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class MyAdapter extends SelectableAdapter<MyAdapter.ViewHolder> {
    @SuppressWarnings("unused")
    private static final String TAG = MyAdapter.class.getSimpleName();

    private static final int TYPE_INACTIVE = 0;
    private static final int TYPE_ACTIVE = 1;

    private List<Item> items;

    private ViewHolder.ClickListener clickListener;

    public MyAdapter(ViewHolder.ClickListener clickListener, ArrayList<Item> items) {
        super();

        this.clickListener = clickListener;

        this.items = items;
    }

    public void addItem(Item item){
        items.add(item);
    }

    public void removeItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    public void removeItems(List<Integer> positions) {
        // Reverse-sort the list
        Collections.sort(positions, new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {
                return rhs - lhs;
            }
        });

        // Split the list in ranges
        while (!positions.isEmpty()) {
            if (positions.size() == 1) {
                removeItem(positions.get(0));
                positions.remove(0);
            } else {
                int count = 1;
                while (positions.size() > count && positions.get(count).equals(positions.get(count - 1) - 1)) {
                    ++count;
                }

                if (count == 1) {
                    removeItem(positions.get(0));
                } else {
                    removeRange(positions.get(count - 1), count);
                }

                for (int i = 0; i < count; ++i) {
                    positions.remove(0);
                }
            }
        }
    }

    private void removeRange(int positionStart, int itemCount) {
        for (int i = 0; i < itemCount; ++i) {
            items.remove(positionStart);
        }
        notifyItemRangeRemoved(positionStart, itemCount);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final int layout = R.layout.item_active_;

        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(v, clickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Item item = items.get(position);

        holder.title.setText(item.getTitle());
        holder.subtitle.setText(item.getSubtitle() + ", which is " + (item.isActive() ? "active" : "inactive"));

        // Span the item if active
        final ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams sglp = (StaggeredGridLayoutManager.LayoutParams) lp;
            sglp.setFullSpan(item.isActive());
            holder.itemView.setLayoutParams(sglp);
        }

        // Highlight the item if it's selected
        holder.selectedOverlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        final Item item = items.get(position);

        return item.isActive() ? TYPE_ACTIVE : TYPE_INACTIVE;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {
        @SuppressWarnings("unused")
        private static final String TAG = ViewHolder.class.getSimpleName();

        TextView title;
        TextView subtitle;
        View selectedOverlay;

        private ClickListener listener;

        public ViewHolder(View itemView, ClickListener listener) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            subtitle = (TextView) itemView.findViewById(R.id.subtitle);
            selectedOverlay = itemView.findViewById(R.id.selected_overlay);

            this.listener = listener;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClicked(getLayoutPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (listener != null) {
                return listener.onItemLongClicked(getLayoutPosition());
            }

            return false;
        }

        public interface ClickListener {
            void onItemClicked(int position);
            boolean onItemLongClicked(int position);
        }
    }
}
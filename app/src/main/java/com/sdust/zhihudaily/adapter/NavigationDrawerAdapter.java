package com.sdust.zhihudaily.adapter;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdust.zhihudaily.R;
import com.sdust.zhihudaily.interfaces.NavigationDrawerCallbacks;
import com.sdust.zhihudaily.data.model.Theme;
import com.sdust.zhihudaily.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * NavigationDrawer RecycleView的适配器
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = NavigationDrawerAdapter.class.getSimpleName();

    private NavigationDrawerCallbacks mDrawerCallbacks;//NavigationFragment的引用
    private int mSelectedPosition = -1;
    private List<Theme> mThemes;

    public static final class Type {
        public static final int TYPE_HEADER = 0;
        public static final int TYPE_ITEM = 1;
    }

    public NavigationDrawerAdapter() {
        mThemes = new ArrayList<Theme>();
    }

    public void setThemes(List<Theme> themes) {
        mThemes.clear();
        mThemes.addAll(themes);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return Type.TYPE_HEADER;
        } else {
            return Type.TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        switch (viewType) {
            case Type.TYPE_HEADER:
                itemView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.nav_drawer_header, parent, false);
                return new HeaderViewHolder(itemView);
            case  Type.TYPE_ITEM:
                itemView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.nav_drawer_item,parent,false);
                final ItemViewHolder holder = new ItemViewHolder(itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectPosition(holder.getLayoutPosition() - 1);
                    }
                });
                return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);

        switch (viewType) {
            case Type.TYPE_HEADER:
                HeaderViewHolder headerViewHolder = (HeaderViewHolder)holder;
                bindHeaderData(headerViewHolder,position);
                break;
            case  Type.TYPE_ITEM:
                ItemViewHolder itemViewHolder = (ItemViewHolder)holder;
                bindItemData(itemViewHolder, position);
                break;
            default:
                throw new IllegalArgumentException(TAG + " error view type!");

        }
    }

    public void selectPosition(int position) {
        int realPosition = position + 1;
        int lastPosition = mSelectedPosition;
        if(lastPosition != -1 && realPosition != lastPosition) {
            notifyItemChanged(lastPosition);//将之前的Item恢复
        }

        if(realPosition != lastPosition) {
            mSelectedPosition = realPosition;
            notifyItemChanged(mSelectedPosition);
        }

        if(mDrawerCallbacks != null)
            mDrawerCallbacks.onNavigationDrawerItemSelected(position);
    }

    private void bindHeaderData(HeaderViewHolder viewHolder, int position) {
    }

    private void bindItemData(ItemViewHolder viewHolder, int position) {
        Resources resources = viewHolder.itemView.getContext().getResources();
        if (position == 1) {
            viewHolder.imageView.setVisibility(View.VISIBLE);
            viewHolder.imageView.setBackgroundResource(R.drawable.menu_home);

            viewHolder.tvItemName.setText(resources.getString(R.string.title_activity_main));
        } else {
            viewHolder.imageView.setVisibility(View.GONE);
            viewHolder.tvItemName.setText(mThemes.get(position - 2).getName());
        }

        if (mSelectedPosition == position) {
            LogUtils.i(TAG, "selected item = " + position);
            viewHolder.itemView.setBackgroundColor(resources.getColor(R.color.navigation_item_selected));
            viewHolder.tvItemName.setTextColor(resources.getColor(R.color.navdrawer_text_color_selected));
        } else if (position != 0) {
            viewHolder.itemView.setBackgroundColor(android.R.attr.selectableItemBackground);
            viewHolder.tvItemName.setTextColor(resources.getColor(R.color.navdrawer_text_color));
        }
    }
    @Override
    public int getItemCount() {
        return mThemes != null ? mThemes.size() + 2 : 2;//Header和首页item
    }

    public void setNavigationCallbacks(NavigationDrawerCallbacks callback) {
        this.mDrawerCallbacks = callback;
    }

    /**
     * 知乎日报图标
     */
    public static class HeaderViewHolder extends RecyclerView.ViewHolder{
        ImageView mIvHeader;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            mIvHeader = (ImageView) itemView.findViewById(R.id.ivHeader);
        }
    }

    /**
     * Item
     */
    
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName;
        ImageView imageView;

        public ItemViewHolder(View itemView) {
            super(itemView);

            tvItemName = (TextView)itemView.findViewById(R.id.tvItemName);
            imageView = (ImageView)itemView.findViewById(R.id.ivItemIcon);
        }
    }

}

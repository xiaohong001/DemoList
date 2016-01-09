package me.honge.demo05_mvp.ui.goods;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import javax.inject.Inject;

import me.honge.demo05_mvp.R;
import me.honge.demo05_mvp.data.model.GoodsModel;

/**
 * Created by hong on 16/1/5.
 */
public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.GoodsHolder> {
    private ArrayList<GoodsModel> androidGoods;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private boolean mShowFooter = true;
    private OnGoodsClickListener onGoodsClickListener;

    public void setOnGoodsClickListener(OnGoodsClickListener onGoodsClickListener) {
        this.onGoodsClickListener = onGoodsClickListener;
    }

    @Inject
    public GoodsAdapter() {
        this.androidGoods = new ArrayList<>();
    }

    @Override
    public GoodsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        boolean isItem = true;
        if (viewType == TYPE_ITEM) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_goods_item, parent, false);
            isItem = true;
        } else if (viewType == TYPE_FOOTER) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_bottom_progress, parent, false);
            isItem = false;
        }

        return new GoodsHolder(itemView, isItem);
    }

    @Override
    public int getItemViewType(int position) {
        if (!mShowFooter) {
            return TYPE_ITEM;
        }
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return androidGoods.size() + (mShowFooter ? 1 : 0);
    }

    @Override
    public void onBindViewHolder(GoodsHolder holder, final int position) {
        if (holder.isItem) {
            holder.tvGoods.setText(androidGoods.get(position).desc);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onGoodsClickListener != null) {
                        onGoodsClickListener.onClick(v, androidGoods.get(position));
                    }
                }
            });

        }
    }


    public void setShowFooter(boolean showFooter) {
        this.mShowFooter = showFooter;
    }

    public boolean isShowFooter() {
        return this.mShowFooter;
    }


    public void setAndroidGoods(ArrayList<GoodsModel> androidGoods, boolean needClear) {
        if (needClear) {
            this.androidGoods.clear();
        }
        this.androidGoods.addAll(androidGoods);
    }


    class GoodsHolder extends RecyclerView.ViewHolder {
        boolean isItem;
        TextView tvGoods;

        public GoodsHolder(View itemView, boolean isItem) {
            super(itemView);
            this.isItem = isItem;
            if (isItem) {
                tvGoods = (TextView) itemView.findViewById(R.id.tvGoods);
            }
        }
    }

    public interface OnGoodsClickListener {
        void onClick(View view, GoodsModel goodsModel);
    }
}

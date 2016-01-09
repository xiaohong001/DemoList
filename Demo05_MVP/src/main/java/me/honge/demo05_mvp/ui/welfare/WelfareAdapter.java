package me.honge.demo05_mvp.ui.welfare;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.socks.library.KLog;

import java.util.ArrayList;

import javax.inject.Inject;

import me.honge.demo05_mvp.R;
import me.honge.demo05_mvp.data.model.GoodsModel;

/**
 * Created by hong on 16/1/5.
 */
public class WelfareAdapter extends RecyclerView.Adapter<WelfareAdapter.WelfareHolder> {
    private ArrayList<GoodsModel> welfares;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private boolean mShowFooter = true;
    private OnGoodsClickListener onGoodsClickListener;

    public void setOnGoodsClickListener(OnGoodsClickListener onGoodsClickListener) {
        this.onGoodsClickListener = onGoodsClickListener;
    }
    @Inject
    public WelfareAdapter() {
        this.welfares = new ArrayList<>();
    }

    @Override
    public WelfareHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        boolean isItem = true;
        if (viewType == TYPE_ITEM) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_welfare_item, parent, false);
            isItem = true;
        } else if (viewType == TYPE_FOOTER) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_bottom_progress, parent, false);
            isItem = false;
        }

        return new WelfareHolder(itemView, isItem);
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
        return welfares.size() + (mShowFooter ? 1 : 0);
    }

    @Override
    public void onBindViewHolder(final WelfareHolder holder, final int position) {
        if (holder.isItem) {
            Glide.with(holder.ivWelfare.getContext())
                    .load(welfares.get(position).url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.ivWelfare);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onGoodsClickListener != null) {
                        onGoodsClickListener.onClick(v, welfares.get(position));
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


    public void setWelfares(ArrayList<GoodsModel> welfares, boolean needClear) {
        if (needClear) {
            this.welfares.clear();
        }
        this.welfares.addAll(welfares);
    }


    class WelfareHolder extends RecyclerView.ViewHolder {
        boolean isItem;
        ImageView ivWelfare;

        public WelfareHolder(View itemView, boolean isItem) {
            super(itemView);
            this.isItem = isItem;
            if (isItem) {
                ivWelfare = (ImageView) itemView.findViewById(R.id.ivWelfare);
            }
        }
    }

    public interface OnGoodsClickListener {
        void onClick(View view, GoodsModel goodsModel);
    }
}

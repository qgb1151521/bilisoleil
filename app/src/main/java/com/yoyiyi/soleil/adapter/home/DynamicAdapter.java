package com.yoyiyi.soleil.adapter.home;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yoyiyi.soleil.R;
import com.yoyiyi.soleil.bean.dynamic.Dynamic;
import com.yoyiyi.soleil.bean.dynamic.MulDynamic;
import com.yoyiyi.soleil.utils.AppUtils;
import com.yoyiyi.soleil.utils.EmptyUtils;
import com.yoyiyi.soleil.utils.NumberUtils;
import com.yoyiyi.soleil.utils.SpanUtils;
import com.yoyiyi.soleil.utils.TimeUtils;
import com.yoyiyi.soleil.utils.time.FormatUtils;
import com.yoyiyi.soleil.widget.CircleImageView;

import java.util.List;

/**
 * @author zzq  作者 E-mail:   soleilyoyiyi@gmail.com
 * @date 创建时间：2017/6/13 16:09
 * 描述:动态Adapter
 */

public class DynamicAdapter extends BaseMultiItemQuickAdapter<MulDynamic, BaseViewHolder> {
    public DynamicAdapter(@Nullable List<MulDynamic> data) {
        super(data);
        addItemType(MulDynamic.TYPE_LV0, R.layout.item_home_dynamic);
        addItemType(MulDynamic.TYPE_LV1, R.layout.item_home_dynamic);
    }

    @Override
    protected void convert(BaseViewHolder holder, MulDynamic mulDynamic) {
        switch (holder.getItemViewType()) {
            case MulDynamic.TYPE_LV0:
                Dynamic.ItemBean itemBean = mulDynamic.group;
                holder.setVisible(R.id.fl_recent, itemBean.isRecent == 1 ? true : false);
                holder.setText(R.id.tv_recent, "还有" + itemBean.recent_count + "个视频被隐藏");
                if (EmptyUtils.isNotEmpty(mulDynamic.child)) {
                    holder.setVisible(R.id.fl_recent, true);
                    holder.setText(R.id.tv_recent, "还有" + itemBean.recent_count + "个视频被隐藏");
                } else {
                    holder.setVisible(R.id.fl_recent, false);
                }
                holder.getView(R.id.fl_recent)
                        .setOnClickListener(view -> {
                            int pos = holder.getAdapterPosition();
                            expand(pos, true);
                            holder.setVisible(R.id.fl_recent, false);
                        });
                switch (itemBean.type) {
                    case 0://关注up
                        holder.setVisible(R.id.iv_avatar, true)
                                .setVisible(R.id.tv_tag, false)
                                .setText(R.id.tv_title, new SpanUtils()
                                        .append(itemBean.name)
                                        .setFontSize(14)
                                        .setForegroundColor(AppUtils.getColor(R.color.black_alpha_30))
                                        .appendSpace(5)
                                        .append(FormatUtils.getDescriptionTimeFromDateString(itemBean.ctime + ""))
                                        .setFontSize(12)
                                        .setForegroundColor(AppUtils.getColor(R.color.font_gray))
                                        .create())
                                .setText(R.id.tv_video_title, itemBean.title)
                                .setText(R.id.tv_duration, TimeUtils.long2String(itemBean.duration + ""))
                                .setVisible(R.id.tv_duration, true)
                                .setVisible(R.id.iv_video_play_num, true)
                                .setVisible(R.id.tv_video_play_num, true)
                                .setVisible(R.id.tv_video_favourite, true)
                                .setVisible(R.id.iv_video_online_region, true)
                                .setText(R.id.tv_video_play_num, " " + NumberUtils.format(itemBean.play + ""))
                                .setText(R.id.tv_video_favourite, " " + NumberUtils.format(itemBean.favorite + ""))
                                .setVisible(R.id.iv_tag_video_play_num, false)
                                .setVisible(R.id.tv_tag_video_favourite, false)
                                .setVisible(R.id.iv_tag_video_online_region, false)
                                .setText(R.id.tv_tag_video_play_num, itemBean.tname + (itemBean.tag == null ? "" : " · " + itemBean.tag.tag_name));
                        Glide.with(mContext)
                                .load(itemBean.face)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .placeholder(R.drawable.bili_default_avatar)
                                .dontAnimate()
                                .into((CircleImageView) holder.getView(R.id.iv_avatar));
                        Glide.with(mContext)
                                .load(itemBean.cover)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .placeholder(R.drawable.bili_default_image_tv)
                                .dontAnimate()
                                .into((ImageView) holder.getView(R.id.iv_preview));
                        break;
                    case 2://国产动画
                        holder.setVisible(R.id.iv_avatar, false)
                                .setVisible(R.id.tv_duration, false)
                                .setVisible(R.id.tv_tag, true)
                                .setText(R.id.tv_tag, new SpanUtils()
                                        .setBackgroundColor(AppUtils.getColor(R.color.yellow_30))
                                        .append("国产动画").create())
                                .setText(R.id.tv_title, new SpanUtils()
                                        .append(FormatUtils.getDescriptionTimeFromDateString(itemBean.ctime + ""))
                                        .setFontSize(12)
                                        .setForegroundColor(AppUtils.getColor(R.color.font_gray))
                                        .create())
                                .setText(R.id.tv_video_title, itemBean.title)
                                .setVisible(R.id.iv_video_play_num, false)
                                .setVisible(R.id.tv_video_play_num, true)
                                .setVisible(R.id.tv_video_favourite, false)
                                .setVisible(R.id.iv_video_online_region, false)
                                .setText(R.id.tv_video_play_num, "第" + itemBean.index + "话" + " " + itemBean.index_title)
                                .setText(R.id.tv_tag_video_play_num, NumberUtils.format(itemBean.play + ""))
                                .setVisible(R.id.iv_tag_video_play_num, true)
                                .setVisible(R.id.tv_tag_video_play_num, true)
                                .setVisible(R.id.iv_tag_video_online_region, true)
                                .setVisible(R.id.tv_tag_video_favourite, true)
                                .setText(R.id.tv_tag_video_favourite, NumberUtils.format(itemBean.danmaku + ""));
                        Glide.with(mContext)
                                .load(itemBean.cover)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .placeholder(R.drawable.bili_default_image_tv)
                                .dontAnimate()
                                .into((ImageView) holder.getView(R.id.iv_preview));
                        break;

                    case 1://可能是番剧 不知道参数意思
                        holder.setVisible(R.id.iv_avatar, false)
                                .setVisible(R.id.tv_tag, true)
                                .setVisible(R.id.tv_duration, false)
                                .setText(R.id.tv_tag, new SpanUtils()
                                        .setBackgroundColor(AppUtils.getColor(R.color.yellow_30))
                                        .append("国产动画").create())
                                .setText(R.id.tv_title, new SpanUtils()
                                        .append(FormatUtils.getDescriptionTimeFromDateString(itemBean.ctime + ""))
                                        .setFontSize(12)
                                        .setForegroundColor(AppUtils.getColor(R.color.font_gray))
                                        .create())
                                .setText(R.id.tv_video_title, itemBean.title)
                                .setVisible(R.id.iv_video_play_num, false)
                                .setVisible(R.id.tv_video_play_num, true)
                                .setVisible(R.id.tv_video_favourite, false)
                                .setVisible(R.id.iv_video_online_region, false)
                                .setText(R.id.tv_video_play_num, "第" + itemBean.index + "话" + " " + itemBean.index_title)
                                .setText(R.id.tv_tag_video_play_num, NumberUtils.format(itemBean.play + ""))
                                .setVisible(R.id.iv_tag_video_play_num, true)
                                .setVisible(R.id.tv_tag_video_play_num, true)
                                .setVisible(R.id.iv_tag_video_online_region, true)
                                .setVisible(R.id.tv_tag_video_favourite, true)
                                .setText(R.id.tv_tag_video_favourite, NumberUtils.format(itemBean.danmaku + ""));
                        Glide.with(mContext)
                                .load(itemBean.cover)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .placeholder(R.drawable.bili_default_image_tv)
                                .dontAnimate()
                                .into((ImageView) holder.getView(R.id.iv_preview));
                        break;

                }
                break;
            case MulDynamic.TYPE_LV1:
                Dynamic.ItemBean.RecentBean recent = mulDynamic.recent;
                holder.setVisible(R.id.iv_avatar, true)
                        .setVisible(R.id.tv_tag, false)
                        .setText(R.id.tv_title, new SpanUtils()
                                .append(recent.name)
                                .setFontSize(14)
                                .setForegroundColor(AppUtils.getColor(R.color.black_alpha_30))
                                .appendSpace(5)
                                .append(FormatUtils.getDescriptionTimeFromDateString(recent.ctime + ""))
                                .setFontSize(12)
                                .setForegroundColor(AppUtils.getColor(R.color.font_gray))
                                .create())
                        .setText(R.id.tv_video_title, recent.title)
                        .setVisible(R.id.iv_video_play_num, true)
                        .setVisible(R.id.tv_video_play_num, true)
                        .setVisible(R.id.tv_video_favourite, true)
                        .setVisible(R.id.iv_video_online_region, true)
                        .setText(R.id.tv_video_play_num, NumberUtils.format(recent.play + ""))
                        .setText(R.id.tv_video_favourite, NumberUtils.format(recent.favorite + ""))
                        .setVisible(R.id.iv_tag_video_play_num, false)
                        .setVisible(R.id.tv_tag_video_favourite, false)
                        .setVisible(R.id.tv_tag_video_favourite, false)
                        .setVisible(R.id.fl_recent, false)
                        .setText(R.id.tv_tag_video_play_num, recent.tname
                                + recent.tag == null ? "" : " · " + recent.tag.tag_name);
                Glide.with(mContext)
                        .load(recent.face)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.bili_default_avatar)
                        .dontAnimate()
                        .into((CircleImageView) holder.getView(R.id.iv_avatar));
                Glide.with(mContext)
                        .load(recent.cover)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.bili_default_image_tv)
                        .dontAnimate()
                        .into((ImageView) holder.getView(R.id.iv_preview));
                break;
        }
    }
}
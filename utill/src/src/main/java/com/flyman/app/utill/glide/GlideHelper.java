package com.flyman.app.util.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flyman.app.util.R;


/**
 * @author Flyman
 * @ClassName GlideHelper
 * @description 图片加载了类助手 使用glide
 * @date 2017-4-11 23:41
 */
public class GlideHelper {

    /**
     * 加载静态图
     *
     * @param
     * @return nothing
     */
    public static void loadBitmap(Context mContext, ImageView view, String url) {
        Glide.with(mContext).load(url).asBitmap().error(R.drawable.drawable_pic_placeholder).placeholder(R.drawable.drawable_pic_placeholder).into(view);
    }

    /**
     * 加载圆角静态图
     *
     * @param
     * @return nothing
     */
    public static void loadRoundBitmap(Context mContext, ImageView view, String url, int roundDp) {
        Glide.with(mContext).load(url).asBitmap().error(R.drawable.drawable_pic_placeholder).transform(new GlideRoundTransform(mContext, roundDp)).into(view);
    }

    /**
     * 加载圆形静态图
     *
     * @param
     * @return nothing
     */
    public static void loadCircleBitmap(Context mContext, ImageView view, String url) {
        Glide.with(mContext).load(url).asBitmap().error(R.drawable.drawable_pic_circle_placeholder).placeholder(R.drawable.drawable_pic_circle_placeholder).transform(new GlideCircleTransform(mContext)).into(view);
    }

    /**
     * 加载动态图
     * 需要设置缓存diskCacheStrategy(DiskCacheStrategy.NONE)
     * 那应该是因为你没有配置diskCacheStrategy，加载gif图一定要把diskCacheStrategy设置成NONE，或者是SOURCE，
     * 不配不行，因为不配默认就是ALL，这种情况下会把GIF图的每一帧都去压缩然后缓存，时间极长，可能要几分钟gif图才会显示出来
     *
     * @param
     * @return nothing
     */
    public static void loadGif(Context mContext, ImageView view, String url) {
        Glide.with(mContext).load(url).asGif().diskCacheStrategy(DiskCacheStrategy.NONE).into(view);
    }
}

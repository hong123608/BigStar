/**
 * 
 */
package com.caozan.bigstar.imageload;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.caozan.bigstar.R;
import com.caozan.bigstar.application.MyApplication;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
/**
 * 图片异步加载工具
 * 源码与示例：http://blog.csdn.net/xu5603/article/details/38822843
 * 
 * ImageLoaderConfiguration是针对图片缓存的全局配置，主要有线程类�?�缓存大小�?�磁盘大小�?�图片下载与解析、日志方面的配置�?
 * ImageLoader是具体下载图片
 * ，缓存图片，显示图片的具体执行类，它有两个具体的方法displayImage(...)、loadImage(...)�?
 * 但是其实�?终他们的实现都是displayImage(...)�?
 * DisplayImageOptions用于指导每一个Imageloader根据网络图片的状�?
 * （空白�?�下载错误�?�正在下载）显示对应的图片，是否将缓存加载到磁盘上，下载完后对图片进行�?�么样的处理�?
 * 
 * @author li.li
 * 
 */
public class EasyImageLoader {
	private static final int DELAY_TIME = 0;
	private static final int DISK_CACHE_SIZE = 50 * 1024 * 1024;
	// private static final int FADE_IN_DISPLAYER = 500;

	static {
		Log.i("after", "静态代码块");
		MyApplication ctx = MyApplication.getContext();
		File cacheDir = new File(ctx.getImgCachePath());
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				ctx)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.threadPoolSize(8)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new MyMd5FileNameGenerator())
				.memoryCache(new WeakMemoryCache())
				// 防止内存OOM
				.memoryCacheSize(2 * 1024 * 1024)
				.diskCacheSize(DISK_CACHE_SIZE)
				.diskCache(
						new UnlimitedDiskCache(cacheDir, null,
								new MyMd5FileNameGenerator()))
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs()
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.build();
		ImageLoader.getInstance().init(config);
	}
	public static void clearImageCache() {
		ImageLoader.getInstance().clearDiskCache();
		ImageLoader.getInstance().clearMemoryCache();
	}

	public static void show(String imageUrl, ImageView imageView,
			DisplayImageOptions options, ImageLoadingListener listener) {
		ImageLoader.getInstance().displayImage(imageUrl, imageView, options,
				listener);
	}

	/**
	 * 图片加载
	 * 
	 * @param imageUrl
	 *            图片url
	 * @param imageView
	 *            图片组件
	 */
	public static void show(String imageUrl, View imageView) {
		show(imageUrl, imageView, R.mipmap.ic_launcher, DisplayType.def);
	}

	/**
	 * 图片加载
	 * 
	 * @param imageUrl
	 *            图片url
	 * @param imageView
	 *            图片组件
	 * @param displayType
	 *            显示类型
	 */
	public static void show(String imageUrl, View imageView,
			DisplayType displayType) {
		show(imageUrl, imageView, R.mipmap.ic_launcher, displayType);
	}

	/**
	 * 图片加载
	 * 
	 * @param imageUrl
	 *            图片url
	 * @param imageView
	 *            图片组件
	 * @param defImageRes
	 *            默认�?
	 */
	public static void show(String imageUrl, View imageView, int defImageRes) {
		show(imageUrl, imageView, defImageRes, DisplayType.def);
	}

	/**
	 * 图片加载
	 * 
	 * @param imageUrl
	 *            图片url
	 * @param imageView
	 *            图片组件
	 * @param defImageRes
	 *            默认�?
	 * @param displayType
	 *            显示类型
	 */
	public static void show(String imageUrl, final View imageView,
			final int defImageRes, final DisplayType displayType) {
		try {
			if (imageView instanceof ImageView) {
				ImageLoader.getInstance().displayImage(imageUrl,
						(ImageView) imageView,
						createOptions(defImageRes, displayType));
			} else {
				ImageLoader.getInstance().loadImage(imageUrl,
						createOptions(defImageRes, displayType),
						new ImageLoadingListener() {
							@Override
							public void onLoadingStarted(String arg0, View arg1) {
								imageView.setBackgroundResource(defImageRes);
							}

							@Override
							public void onLoadingFailed(String arg0, View arg1,
									FailReason arg2) {
							}

							@SuppressWarnings("deprecation")
							@Override
							public void onLoadingComplete(String arg0,
									View arg1, Bitmap arg2) {
								if (arg2 != null) {
									if (DisplayType.boxBlurFilter
											.equals(displayType)) {
										imageView
												.setBackgroundDrawable(new BitmapDrawable(
														ImageUtil
																.BoxBlurFilter(arg2)));
									} else {
										imageView
												.setBackgroundDrawable(new BitmapDrawable(
														arg2));
									}
								}
							}

							@Override
							public void onLoadingCancelled(String arg0,
									View arg1) {
							}
						});
			}
		} catch (Exception e) {
			LogUtils.error(e.getMessage(), e);
		}
	}

	/**
	 * ImageSize mImageSize = new ImageSize(100, 100);
	 * 
	 * @param imageview
	 * @param url
	 * @param mImageSize
	 */
	public static void showActorPic(final ImageView imageview, String url,
			ImageSize mImageSize, int defaultImage) {
		// String imageUrl =
		// "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg";

		// 显示图片的配�?
		// DisplayImageOptions options = new
		// DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565)
		// .build();
		// DisplayType.def
		ImageLoader.getInstance().loadImage(url, mImageSize,
				createOptions(defaultImage, DisplayType.def),
				new SimpleImageLoadingListener() {

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						super.onLoadingComplete(imageUri, view, loadedImage);
						imageview.setImageBitmap(loadedImage);
					}

				});
	}


	/**
	 * 生成自定义DisplayImageOptions
	 * 
	 * @param defImageRes
	 * @return
	 */
	private static DisplayImageOptions createOptions(int defImageRes,
			DisplayType displayType) {

		DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder()
				.showImageOnLoading(defImageRes)// 设置图片在下载期间显示的图片
				.showImageForEmptyUri(defImageRes) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(defImageRes) // 设置图片加载/解码过程中错误时候显示的图片
				.resetViewBeforeLoading(true) // 设置图片在下载前是否重置，复�?
				.delayBeforeLoading(DELAY_TIME)// int
												// delayInMillis为你设置的下载前的延迟时�?
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存�?
				.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
				.considerExifParams(false) // 是否考虑JPEG图像EXIF参数（旋转，翻转�?
				.bitmapConfig(Bitmap.Config.RGB_565) // 设置图片的解码类�?,默认值�?��?�Bitmap.Config.ARGB_8888
				.handler(new Handler());

		if (DisplayType.circle.equals(displayType)) {
			builder.displayer(new CircleBitmapDisplayer());
		} else if (DisplayType.circleWhiteLoop.equals(displayType)) {
			// builder.displayer(new CircleWhiteLoopBitmapDisplayer());
		} else if (DisplayType.boxBlurFilter.equals(displayType)) {
			// builder.displayer(new BoxBlurFilterDisplayer());
		}
		return builder.build();
	}

	private static final class MyMd5FileNameGenerator implements
			FileNameGenerator {
		@Override
		public String generate(String imgUri) {
			return EncodeUtils.md5(imgUri);
		}
	}

	/**
	 * 显示类型
	 * 
	 */
	public enum DisplayType {
		def, // 默认
		circle, // 圆形
		circleWhiteLoop, // 圆形带圆白环
		boxBlurFilter, // 高斯模糊
	}

}

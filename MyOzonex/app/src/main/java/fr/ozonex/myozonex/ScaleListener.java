package fr.ozonex.myozonex;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class ScaleListener {
    private HorizontalScrollView horizontalScrollView;
    private ScrollView verticalScrollView;
    private AbsoluteLayout layout;

    ScaleListener(HorizontalScrollView horizontalScrollView, ScrollView verticalScrollView, AbsoluteLayout layout) {
        this.horizontalScrollView = horizontalScrollView;
        this.verticalScrollView = verticalScrollView;
        this.layout = layout;
        scale();
    }

    private void scale() {
        final float mScaleFactor;
        Display display = MainActivity.instance().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealSize(size);
        } else {
            display.getSize(size);
        }
        final float screenWidth = convertPixelsToDp(size.x);
        final float screenHeight = convertPixelsToDp(size.y);
        final float diff_width = 1280 - screenWidth;
        final float diff_height = 800 - screenHeight;

        if (diff_height < diff_width) {
            mScaleFactor = screenWidth / 1280;
        } else {
            mScaleFactor = screenHeight / 800;
        }

        layout.setScaleX(mScaleFactor);
        layout.setScaleY(mScaleFactor);
        layout.setTranslationX(-convertDpToPixel(diff_width / 2));
        layout.setTranslationY(-convertDpToPixel((800 - 800 * mScaleFactor) / 2));

        verticalScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (diff_height < diff_width) {
                    if (convertPixelsToDp(verticalScrollView.getScrollY()) >= (800 * mScaleFactor - screenHeight)) {
                        verticalScrollView.smoothScrollTo(0, (int) convertDpToPixel(800 * mScaleFactor - screenHeight));
                    }
                } else {
                    verticalScrollView.smoothScrollTo(0, 0);
                }
            }
        });

        horizontalScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (diff_height < diff_width) {
                    horizontalScrollView.smoothScrollTo(0, 0);
                } else {
                    if (convertPixelsToDp(horizontalScrollView.getScrollX()) >= (1280 * mScaleFactor - screenWidth)) {
                        horizontalScrollView.smoothScrollTo((int) convertDpToPixel(1280 * mScaleFactor - screenWidth), 0);
                    }
                }
            }
        });
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp){
        Resources resources = MainActivity.instance().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px){
        return px / ((float) MainActivity.instance().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}

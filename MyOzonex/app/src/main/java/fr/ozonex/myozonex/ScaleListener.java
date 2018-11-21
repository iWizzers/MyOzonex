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
        final float screenWidth = Convertisseur.convertPixelsToDp(size.x);
        final float screenHeight = Convertisseur.convertPixelsToDp(size.y);
        final float diff_width = 1280 - screenWidth;
        final float diff_height = 800 - screenHeight;

        if (diff_height < diff_width) {
            mScaleFactor = screenWidth / 1280;
        } else {
            mScaleFactor = screenHeight / 800;
        }

        layout.setScaleX(mScaleFactor);
        layout.setScaleY(mScaleFactor);
        layout.setTranslationX(-Convertisseur.convertDpToPixel(diff_width / 2));
        layout.setTranslationY(-Convertisseur.convertDpToPixel((800 - 800 * mScaleFactor) / 2));

        verticalScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (diff_height < diff_width) {
                    if (Convertisseur.convertPixelsToDp(verticalScrollView.getScrollY()) >= (800 * mScaleFactor - screenHeight)) {
                        verticalScrollView.smoothScrollTo(0, (int) Convertisseur.convertDpToPixel(800 * mScaleFactor - screenHeight));
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
                    if (Convertisseur.convertPixelsToDp(horizontalScrollView.getScrollX()) >= (1280 * mScaleFactor - screenWidth)) {
                        horizontalScrollView.smoothScrollTo((int) Convertisseur.convertDpToPixel(1280 * mScaleFactor - screenWidth), 0);
                    }
                }
            }
        });
    }
}

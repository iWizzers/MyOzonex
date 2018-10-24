package fr.ozonex.myozonex;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.ScaleGestureDetector;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;

public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
    private float mScaleFactor = 1.0f;
    private AbsoluteLayout layout;

    ScaleListener(AbsoluteLayout layout) {
        this.layout = layout;
    }

    @Override
    public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
        /*Display display = MainActivity.instance().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        float screenWidth = size.x;
        float screenHeight = size.y;
        Log.d("TEST", String.valueOf(screenWidth));
        Log.d("TEST", String.valueOf(screenHeight));
        screenWidth = convertPixelsToDp(screenWidth, MainActivity.instance());
        screenHeight = convertPixelsToDp(screenHeight, MainActivity.instance());
        Log.d("TEST", String.valueOf(screenWidth));
        Log.d("TEST", String.valueOf(screenHeight));*/

        mScaleFactor *= scaleGestureDetector.getScaleFactor();
        mScaleFactor = Math.max(0.1f,
                Math.min(mScaleFactor, 10.0f));

        if (mScaleFactor > 1) {
            mScaleFactor = 1;
        }

        layout.setScaleX(mScaleFactor);
        layout.setScaleY(mScaleFactor);
        return true;
    }

    public static float convertPixelsToDp(float px, Context context){
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}

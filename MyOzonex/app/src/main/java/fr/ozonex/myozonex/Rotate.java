package fr.ozonex.myozonex;

import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class Rotate implements Animation.AnimationListener {
    private ImageView imageView;

    private RotateAnimation animation;
    private boolean animationWorks = false;
    private boolean animationStopped = false;
    private int currentRpm = 0;
    private int maxRpm;

    public Rotate(ImageView imageView, int rpm) {
        this.imageView = imageView;
        maxRpm = rpm;

        // Setup animation with desired properties
        animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setRepeatCount(0);

        // Set animation listener
        animation.setAnimationListener(this);
    }

    public void startAnimation() {
        if (!animationWorks) {
            if (currentRpm < maxRpm) {
                animationStopped = false;
                currentRpm = maxRpm;
                animation.setInterpolator(new AccelerateInterpolator());
                animation.setDuration(2000);
            } else if (animationStopped) {
                currentRpm = 0;
                animation.setInterpolator(new DecelerateInterpolator());
                animation.setDuration(2000);
            } else {
                animation.setInterpolator(new LinearInterpolator());
                animation.setDuration((long)((1.0 / (maxRpm / 60.0)) * 1000));
            }

            if (!animationStopped) {
                animationWorks = true;
            }

            // Start the animation
            imageView.startAnimation(animation);
        }
    }

    public void stopAnimation() {
        animationStopped = true;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        // check for rotate animation
        if (animation == this.animation) {
            if (animationWorks) {
                animationWorks = false;
                startAnimation();
            } else {
                imageView.clearAnimation();
                // Later.. stop the animation
                //imageView.setAnimation(null);
            }
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}

package fr.ozonex.myozonex;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class AnimationInjection {
    RelativeLayout container;
    View view;
    boolean startTop;
    Donnees.Equipement equipement;
    int couleur;

    boolean isRunning;
    private Handler attenteInjection;

    public AnimationInjection(RelativeLayout container, View view, boolean startTop, Donnees.Equipement equipement, int couleur) {
        this.container = container;
        this.view = view;
        this.startTop = startTop;
        this.equipement = equipement;
        this.couleur = couleur;

        attenteInjection = new Handler();
    }

    private Runnable timer = new Runnable() {
        @Override
        public void run() {
            isRunning = false;
            if ((Donnees.instance().obtenirModeFonctionnement(equipement) == Donnees.AUTO_MARCHE)
                    || (Donnees.instance().obtenirModeFonctionnement(equipement) == Donnees.MARCHE)) {
                ajouterInjection();
            }
        }
    };

    public void demarrerTimer() {
        isRunning = true;
        attenteInjection.postDelayed(timer, Global.TEMPO_INJECTION_TUYAU);
    }

    public void arreterTimer() {
        isRunning = false;
        attenteInjection.removeCallbacks(timer);
    }

    public void ajouterInjection() {
        if (!isRunning) {
            demarrerTimer();
        } else {
            return;
        }

        int width = view.getWidth();
        final int height = view.getHeight();
        int x = (int) view.getX();

        final View injection = new View(MainActivity.instance());
        injection.setBackgroundColor(MainActivity.instance().getResources().getColor(couleur));
        injection.setX(x);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                width,
                width,
                1.0f
        );
        injection.setLayoutParams(params);
        container.addView(injection);

        ValueAnimator animation = ValueAnimator.ofFloat(startTop ? -width : height, startTop ? height : -width);
        animation.setDuration(10 * height);
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = (float) animation.getAnimatedValue();
                injection.setTranslationY(progress);
            }
        });
        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                container.removeView(injection);
            }
        });
        animation.start();
    }
}

package fr.ozonex.myozonex;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class AnimationRefoulement {
    RelativeLayout container;

    boolean isRunning;
    private Handler attenteRefoulement;

    public AnimationRefoulement(RelativeLayout container) {
        this.container = container;

        attenteRefoulement = new Handler();
    }

    private Runnable timer = new Runnable() {
        @Override
        public void run() {
            isRunning = false;
            if ((Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.PompeFiltration) == Donnees.AUTO_MARCHE)
                    || (Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.PompeFiltration) == Donnees.MARCHE)) {
                ajouterInjection();
            }
        }
    };

    public void demarrerTimer() {
        isRunning = true;
        if ((Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.Ozone) == Donnees.AUTO_MARCHE)
                || (Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.Ozone) == Donnees.MARCHE)) {
            attenteRefoulement.postDelayed(timer, Generator.nombreAleatoire(100, 200));
        } else {
            attenteRefoulement.postDelayed(timer, Generator.nombreAleatoire(300, 500));
        }
    }

    public void arreterTimer() {
        isRunning = false;
        attenteRefoulement.removeCallbacks(timer);
    }

    public void ajouterInjection() {
        if (!isRunning) {
            demarrerTimer();
        } else {
            return;
        }

        int size = (int) Convertisseur.convertDpToPixel(8);
        int width = container.getWidth();
        int height = container.getHeight();
        int y = height / 2 - size / 2;

        if ((width == 0)
                && (height == 0)) {
            return;
        }

        final View refoulement = new View(MainActivity.instance());
        refoulement.setBackgroundResource(R.drawable.bulle);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                size,
                size,
                1.0f
        );
        refoulement.setLayoutParams(params);
        container.addView(refoulement);

        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("translationX", width, Generator.nombreAleatoire(0, width - 40));
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("translationY", y, Generator.nombreAleatoire(-height / 2 + size / 2, height / 2 - size / 2));
        ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(refoulement, pvhX, pvhY);
        animation.setDuration(Generator.nombreAleatoire(1000, 2000));
        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                container.removeView(refoulement);
            }
        });
        animation.start();
    }
}

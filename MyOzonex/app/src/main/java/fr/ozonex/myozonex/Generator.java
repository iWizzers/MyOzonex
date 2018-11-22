package fr.ozonex.myozonex;

import java.util.Random;

public class Generator {
    public static int nombreAleatoire(int valeurMin, int valeurMax) {
        Random r = new Random();
        return valeurMin + r.nextInt(valeurMax - valeurMin);
    }
}

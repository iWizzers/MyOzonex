package fr.ozonex.myozonex;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class RenduPlage extends View {
    Donnees.Equipement equipement;

    public RenduPlage(Context context, Donnees.Equipement equipement) {
        super(context);
        this.equipement = equipement;
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawRGB(255, 255, 255);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(4);

        if (equipement != Donnees.Equipement.HeuresCreuses) {
            if ((equipement == Donnees.Equipement.PompeFiltration) && Donnees.instance().obtenirPlagesAuto()) {
                dessinerPlageAuto(equipement, canvas, paint);
            } else {
                for (int i = 0; i < Global.MAX_PLAGES_EQUIPEMENTS; i++) {
                    if (Donnees.instance().obtenirEtatPlage(equipement, i)) {
                        dessinerPlage(equipement, i, canvas, paint);
                    } else {
                        break;
                    }
                }
            }

            for (int i = 0; i < Global.MAX_PLAGES_HEURES_CREUSES; i++) {
                if (Donnees.instance().obtenirEtatPlage(Donnees.Equipement.HeuresCreuses, i)) {
                    dessinerPlageTrait(Donnees.Equipement.HeuresCreuses, i, 1, canvas, paint);
                } else {
                    break;
                }
            }

            if ((equipement == Donnees.Equipement.Surpresseur)
                    || (equipement == Donnees.Equipement.Chauffage)) {
                if (Donnees.instance().obtenirPlagesAuto()) {
                    dessinerPlageTraitAuto(Donnees.Equipement.PompeFiltration, 2, canvas, paint);
                } else {
                    for (int i = 0; i < Global.MAX_PLAGES_EQUIPEMENTS; i++) {
                        if (Donnees.instance().obtenirEtatPlage(Donnees.Equipement.PompeFiltration, i)) {
                            dessinerPlageTrait(Donnees.Equipement.PompeFiltration, i, 2, canvas, paint);
                        } else {
                            break;
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < Global.MAX_PLAGES_HEURES_CREUSES; i++) {
                if (Donnees.instance().obtenirEtatPlage(equipement, i)) {
                    dessinerPlage(equipement, i, canvas, paint);
                } else {
                    break;
                }
            }
        }
    }

    private void dessinerPlage(Donnees.Equipement equipement, int index, Canvas canvas, Paint paint) {
        String plage = Donnees.instance().obtenirPlage(equipement, index);
        int offsetHeure = getWidth() / 25;
        int offsetMinute = offsetHeure / 60;
        int offset = offsetHeure / 2;
        String[] split = plage.split(" - ");
        String[] splitDebut = split[0].split("h");
        String[] splitFin = split[1].split("h");

        paint.setColor(obtenirCouleur(equipement));

        canvas.drawRect(new Rect(offset + Integer.parseInt(splitDebut[0]) * offsetHeure + Integer.parseInt(splitDebut[1]) * offsetMinute, 0,
                offset + Integer.parseInt(splitFin[0]) * offsetHeure + Integer.parseInt(splitFin[1]) * offsetMinute, getHeight()), paint);
    }

    private void dessinerPlageTrait(Donnees.Equipement equipement, int index, int decalage, Canvas canvas, Paint paint) {
        String plage = Donnees.instance().obtenirPlage(equipement, index);
        int offsetHeure = getWidth() / 25;
        int offsetMinute = offsetHeure / 60;
        int offset = offsetHeure / 2;
        int yOffset = getHeight() / 3;
        String[] split = plage.split(" - ");
        String[] splitDebut = split[0].split("h");
        String[] splitFin = split[1].split("h");

        paint.setColor(obtenirCouleur(equipement));

        canvas.drawLine(offset + Integer.parseInt(splitDebut[0]) * offsetHeure + Integer.parseInt(splitDebut[1]) * offsetMinute, decalage * yOffset,
                offset + Integer.parseInt(splitFin[0]) * offsetHeure + Integer.parseInt(splitFin[1]) * offsetMinute, decalage * yOffset, paint);
    }

    private void dessinerPlageAuto(Donnees.Equipement equipement, Canvas canvas, Paint paint) {
        String plage = Donnees.instance().obtenirPlageAuto();
        int offsetHeure = getWidth() / 25;
        int offsetMinute = offsetHeure / 60;
        int offset = offsetHeure / 2;
        String[] split = plage.split(" - ");
        String[] splitDebut = split[0].split("h");
        String[] splitFin = split[1].split("h");

        paint.setColor(obtenirCouleur(equipement));

        canvas.drawRect(new Rect(offset + Integer.parseInt(splitDebut[0]) * offsetHeure + Integer.parseInt(splitDebut[1]) * offsetMinute, 0,
                offset + Integer.parseInt(splitFin[0]) * offsetHeure + Integer.parseInt(splitFin[1]) * offsetMinute, getHeight()), paint);
    }

    private void dessinerPlageTraitAuto(Donnees.Equipement equipement, int decalage, Canvas canvas, Paint paint) {
        String plage = Donnees.instance().obtenirPlageAuto();
        int offsetHeure = getWidth() / 25;
        int offsetMinute = offsetHeure / 60;
        int offset = offsetHeure / 2;
        int yOffset = getHeight() / 3;
        String[] split = plage.split(" - ");
        String[] splitDebut = split[0].split("h");
        String[] splitFin = split[1].split("h");

        paint.setColor(obtenirCouleur(equipement));

        canvas.drawLine(offset + Integer.parseInt(splitDebut[0]) * offsetHeure + Integer.parseInt(splitDebut[1]) * offsetMinute, decalage * yOffset,
                offset + Integer.parseInt(splitFin[0]) * offsetHeure + Integer.parseInt(splitFin[1]) * offsetMinute, decalage * yOffset, paint);
    }

    private int obtenirCouleur(Donnees.Equipement equipement) {
        int couleur = R.color.couleurHeuresPleines;

        if (equipement == Donnees.Equipement.PompeFiltration) {
            couleur = R.color.couleurPompeFiltration;
        } else if (equipement == Donnees.Equipement.Surpresseur) {
            couleur = R.color.couleurSurpresseur;
        } else if (equipement == Donnees.Equipement.Chauffage) {
            couleur = R.color.couleurChauffage;
        } else if (equipement == Donnees.Equipement.Eclairage) {
            couleur = R.color.couleurEclairage;
        } else if (equipement == Donnees.Equipement.HeuresCreuses) {
            couleur = R.color.couleurHeuresCreuses;
        }

        return getResources().getColor(couleur);
    }
}

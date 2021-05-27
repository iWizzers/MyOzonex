package fr.ozonex.myozonex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class EventsAdapter extends ArrayAdapter<StructureEvenement> {
    private static final int BLANC = 0;
    private static final int JAUNE = 1;
    private static final int ORANGE = 2;
    private static final int ROUGE = 3;

    public EventsAdapter(Context context, List<StructureEvenement> tweets) {
        super(context, 0, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_events,parent, false);
        }

        EventsAdapter.ViewHolder viewHolder = (EventsAdapter.ViewHolder) convertView.getTag();
        if(viewHolder == null) {
            viewHolder = new EventsAdapter.ViewHolder();
            viewHolder.backgroundEvent = convertView.findViewById(R.id.background_event);
            viewHolder.texteEvent = convertView.findViewById(R.id.texte_event);
            viewHolder.dateHeure = convertView.findViewById(R.id.date_heure);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        StructureEvenement event = getItem(position);
        viewHolder.texteEvent.setText(event.getTexte());
        if (event.getCouleur() == BLANC) {
            viewHolder.backgroundEvent.setBackgroundColor(MainActivity.instance().getResources().getColor(R.color.couleurBlanc));
        } else if (event.getCouleur() == JAUNE) {
            viewHolder.backgroundEvent.setBackgroundColor(MainActivity.instance().getResources().getColor(R.color.couleurJaune));
        } else if (event.getCouleur() == ORANGE) {
            viewHolder.backgroundEvent.setBackgroundColor(MainActivity.instance().getResources().getColor(R.color.couleurOrange));
        } else if (event.getCouleur() == ROUGE) {
            viewHolder.backgroundEvent.setBackgroundColor(MainActivity.instance().getResources().getColor(R.color.couleurRouge));
        }
        viewHolder.dateHeure.setText(event.getDateHeure());

        return convertView;
    }

    private class ViewHolder {
        public RelativeLayout backgroundEvent;
        public TextView texteEvent;
        public TextView dateHeure;
    }
}

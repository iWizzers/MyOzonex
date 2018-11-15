package fr.ozonex.myozonex;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

public class FragmentEvents extends Fragment implements View.OnClickListener {
    View view = null;

    List<Event> events = new ArrayList<Event>();
    EventsAdapter eventsAdapter;

    // Orientation portrait
    LinearLayout globalLayoutPortrait;

    // Orientation paysage
    HorizontalScrollView globalLayoutPaysage;
    ImageButton boutonRetour;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.events_layout, container, false);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            globalLayoutPortrait = view.findViewById(R.id.global_layout);
        } else {
            new ScaleListener((HorizontalScrollView) view.findViewById(R.id.horizontal_scroll),
                    (ScrollView) view.findViewById(R.id.vertical_scroll),
                    (AbsoluteLayout) view.findViewById(R.id.layout));

            globalLayoutPaysage = view.findViewById(R.id.horizontal_scroll);
            boutonRetour = (ImageButton) view.findViewById(R.id.bouton_retour);

            boutonRetour.setOnClickListener(this);
        }

        ListView listView = (ListView) view.findViewById(R.id.listView);
        eventsAdapter = new EventsAdapter(MainActivity.instance(), events);
        listView.setAdapter(eventsAdapter);


        update();


        return view;
    }

    public void update() {
        if ((view != null) && isAdded()) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                globalLayoutPortrait.setBackgroundResource(Donnees.instance().obtenirBackground());
            } else {
                globalLayoutPaysage.setBackgroundResource(Donnees.instance().obtenirBackground());
            }

            events.clear();
            for (int i = 0; i < Donnees.instance().obtenirListeEvents().size(); i++) {
                events.add(new Event(Donnees.instance().obtenirListeEvents().get(i).getTexte(),
                        Donnees.instance().obtenirListeEvents().get(i).getCouleur(),
                        Donnees.instance().obtenirListeEvents().get(i).getDateHeure()));
            }
            eventsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bouton_retour:
                if (Donnees.instance().obtenirPageSource() == Donnees.PAGE_SYNOPTIQUE) {
                    MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_synoptique_layout));
                } else {
                    MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_menu_layout));
                }
                break;
            default:
                break;
        }
    }
}

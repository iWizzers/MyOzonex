package fr.ozonex.myozonex;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FragmentEvents extends Fragment {
    View view = null;

    int tailleListe = 0;
    ListView listView;
    EventsAdapter eventsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.events_layout, container, false);

        listView = view.findViewById(R.id.listView);
        eventsAdapter = new EventsAdapter(MainActivity.instance(), Donnees.instance().obtenirListeEvents());
        listView.setAdapter(eventsAdapter);

        update();

        return view;
    }

    public void update() {
        if ((view != null) && isAdded()) {
            if (tailleListe != Donnees.instance().obtenirListeEvents().size()) {
                tailleListe = Donnees.instance().obtenirListeEvents().size();
                eventsAdapter.notifyDataSetChanged();
                listView.smoothScrollToPositionFromTop(0, 0, 500);
            }
        }
    }
}

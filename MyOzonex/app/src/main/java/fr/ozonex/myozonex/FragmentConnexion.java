package fr.ozonex.myozonex;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class FragmentConnexion extends Fragment {
    View view = null;

    private EditText editID;
    private EditText editPassword;

    private LinearLayout viewAccesRapide;
    private ListView listView;
    private ArrayAdapter<String> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_layout, container, false);

        editID = view.findViewById(R.id.editID);
        editPassword = view.findViewById(R.id.editPassword);

        final Button connexion = view.findViewById(R.id.boutonConnexion);
        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Donnees.setPreferences(Donnees.ID_SYSTEME, editID.getText().toString().toUpperCase());
                MainActivity.instance().masquerClavier();
                Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Get, StructureHttp.PageHTTP.PageLogin, "password=" + editPassword.getText().toString(), false);
            }
        });

        viewAccesRapide = view.findViewById(R.id.layout_acces_rapide);
        listView = view.findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(MainActivity.instance(), R.layout.row_connexion) {
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                if(convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_connexion,parent, false);
                }

                // Initialize a Layout for ListView each Item
                LinearLayout view = convertView.findViewById(R.id.layout);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Donnees.setPreferences(Donnees.ID_SYSTEME, getItem(position));
                        MainActivity.instance().masquerClavier();
                        Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Get, StructureHttp.PageHTTP.PageLogin, "admin", false);
                    }
                });

                // Initialize a TextView for ListView each Item
                TextView textView = convertView.findViewById(R.id.texte_identifiant);
                textView.setText(getItem(position));

                // Initialize a ImageButton for ListView each Item
                ImageButton imgButton = convertView.findViewById(R.id.bouton_supprimer);
                imgButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = position + 1 ; i < Donnees.instance().obtenirListeAppareils().size(); i++) {
                            Donnees.setPreferences(Donnees.ID_SYSTEME_X.replace("x", String.valueOf(i)), Donnees.getPreferences(Donnees.ID_SYSTEME_X.replace("x", String.valueOf(i+1))));
                        }

                        Donnees.setPreferences(Donnees.ID_SYSTEME_X.replace("x", String.valueOf(Donnees.instance().obtenirListeAppareils().size())), "");
                        Donnees.instance().supprimerAppareil(getItem(position));

                        update();
                    }
                });

                // Generate ListView Item using TextView
                return convertView;
            }
        };
        listView.setAdapter(adapter);

        update();

        return view;
    }

    public void update() {
        if (view != null) {
            editID.getText().clear();
            editPassword.getText().clear();

            viewAccesRapide.setVisibility(Donnees.instance().obtenirListeAppareils().size() > 0 ? View.VISIBLE : View.GONE);

            adapter.clear();

            for (int i = 0; i < Donnees.instance().obtenirListeAppareils().size(); i++) {
                adapter.add(Donnees.instance().obtenirListeAppareils().get(i));
            }

            setWifiLayoutHeight();
        }
    }

    private void setWifiLayoutHeight() {
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listView.getCount() - 1));
        listView.setLayoutParams(params);
    }
}

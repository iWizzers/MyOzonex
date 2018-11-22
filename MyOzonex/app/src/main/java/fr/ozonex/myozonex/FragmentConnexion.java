package fr.ozonex.myozonex;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class FragmentConnexion extends Fragment {
    private EditText editID;
    private EditText editPassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_layout, container, false);

        editID = (EditText) view.findViewById(R.id.editID);
        editPassword = (EditText) view.findViewById(R.id.editPassword);

        editID.setText(Donnees.getPreferences(Donnees.ID_SYSTEME));
        editPassword.setText(Donnees.getPreferences(Donnees.MOTDEPASSE));

        final Button connexion = (Button) view.findViewById(R.id.boutonConnexion);
        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Donnees.setPreferences(Donnees.ID_SYSTEME, editID.getText().toString().toUpperCase());
                Donnees.setPreferences(Donnees.MOTDEPASSE, editPassword.getText().toString());

                MainActivity.instance().connexion();
            }
        });

        return view;
    }
}

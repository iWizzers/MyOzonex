package fr.ozonex.myozonex;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ClavierActivity extends AppCompatActivity {
    public static final String EXTRA_QUESTION = "QUESTION";
    public static final String EXTRA_INDICE = "INDICE";
    public static final String EXTRA_AUTRE = "AUTRE";
    public static final String EXTRA_RESULTAT = "RESULTAT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clavier_layout);

        final Intent intent = getIntent();

        TextView texteQuestion = findViewById(R.id.texte_question);
        TextView texteIndice = findViewById(R.id.texte_indice);
        final EditText editValeur = findViewById(R.id.edit_valeur);
        Button boutonValider = findViewById(R.id.bouton_valider);
        Button boutonAnnuler = findViewById(R.id.bouton_annuler);

        texteQuestion.setText(intent.getStringExtra(EXTRA_QUESTION));
        texteIndice.setText(intent.getStringExtra(EXTRA_INDICE));

        boutonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra(EXTRA_AUTRE, intent.getStringExtra(EXTRA_AUTRE));
                returnIntent.putExtra(EXTRA_RESULTAT, editValeur.getText().toString());
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

        boutonAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });
    }
}

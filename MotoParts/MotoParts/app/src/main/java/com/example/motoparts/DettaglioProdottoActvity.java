package com.example.motoparts;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DettaglioProdottoActvity extends AppCompatActivity {
   private ImageView fotoProdotto;
   private Button pulsanteBack;
   private TextView testoProdotto;
   private TextView descrizioneProdotto;
   private TextView prezzoProdotto;
   private Button pulsanteCarrello;
    public static final String IMAGE = "foto_prodotto";
    public static final String TITOLO = "titolo_prodotto";
    public static final String DESCRIZIONE = "descrizione_prodotto";
    public static final String PREZZO = "prezzo_prodotto";

    protected void onCreate(Bundle instance){
       super.onCreate(instance);
       setContentView(R.layout.dettagli_prodotto);
       fotoProdotto = findViewById(R.id.fotoProdotto);
       pulsanteBack = findViewById(R.id.pulsanteBack);
       testoProdotto = findViewById(R.id.testoProdotto);
       descrizioneProdotto = findViewById(R.id.descrizioneProdotto);
       prezzoProdotto = findViewById(R.id.prezzoProdotto);
       pulsanteCarrello = findViewById(R.id.pulsanteCarrello);
       int imageId = getIntent().getIntExtra(IMAGE, 0);
       if (imageId != 0){
           fotoProdotto.setImageResource(imageId);
       }
       String descrizione = getIntent().getStringExtra(DESCRIZIONE);
       testoProdotto.setText(descrizione);
   }

}

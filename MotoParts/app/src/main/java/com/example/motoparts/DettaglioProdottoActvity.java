package com.example.motoparts;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DettaglioProdottoActvity extends AppCompatActivity {

   private ImageView fotoProdotto;
   private Button pulsanteBack;
   private TextView nomeProdotto;
   private TextView descrizioneProdotto;
   private TextView prezzoProdotto;
   private Button pulsanteCarrello;

   public static final String IMAGE = "foto_prodotto";
   public static final String NOME = "nome_prodotto";
   public static final String DESCRIZIONE = "descrizione_prodotto";
   public static final String PREZZO = "prezzo_prodotto";

   private String nome;
   private String descrizione;
   private String prezzo;
   private int imageId;

   @Override
   protected void onCreate(Bundle instance) {
      super.onCreate(instance);
      setContentView(R.layout.dettagli_prodotto);

      initViews();
      loadData();
      setListeners();
   }

   // ---------------- INIT VIEW ----------------
   private void initViews() {
      fotoProdotto = findViewById(R.id.fotoProdotto);
      pulsanteBack = findViewById(R.id.pulsanteBack);
      nomeProdotto = findViewById(R.id.testoProdotto);
      descrizioneProdotto = findViewById(R.id.descrizioneProdotto);
      prezzoProdotto = findViewById(R.id.prezzoProdotto);
      pulsanteCarrello = findViewById(R.id.pulsanteCarrello);
   }

   // ---------------- CARICA DATI ----------------
   private void loadData() {

      imageId = getIntent().getIntExtra(IMAGE, 0);
      nome = getIntent().getStringExtra(NOME);
      descrizione = getIntent().getStringExtra(DESCRIZIONE);
      prezzo = getIntent().getStringExtra(PREZZO);

      if (imageId != 0) {
         fotoProdotto.setImageResource(imageId);
      }

      nomeProdotto.setText(nome);
      descrizioneProdotto.setText(descrizione);
      prezzoProdotto.setText("Prezzo: " + prezzo);

      pulsanteCarrello.setText("AGGIUNGI AL CARRELLO");
   }

   // ---------------- LISTENER ----------------
   private void setListeners() {

      pulsanteBack.setOnClickListener(v -> finish());

      pulsanteCarrello.setOnClickListener(v -> aggiungiAlCarrello());
   }

   // ---------------- AGGIUNGI CARRELLO ----------------
   private void aggiungiAlCarrello() {

      SharedPreferences pref = getSharedPreferences("APP_PREF", MODE_PRIVATE);
      String email = pref.getString("loggedEmail", null);

      if (email == null) {
         Toast.makeText(this, "Devi effettuare il login per aggiungere al carrello", Toast.LENGTH_SHORT).show();
         return;
      }

      // serializzazione semplice prodotto
      String prodotto =
              nome + ";" +
                      descrizione + ";" +
                      prezzo + ";" +
                      imageId;

      String key = "cart_" + email;

      String vecchioCarrello = pref.getString(key, "");
      String nuovoCarrello = vecchioCarrello + prodotto + "|";

      pref.edit().putString(key, nuovoCarrello).apply();

      Toast.makeText(this, "Aggiunto al carrello", Toast.LENGTH_SHORT).show();
   }
}
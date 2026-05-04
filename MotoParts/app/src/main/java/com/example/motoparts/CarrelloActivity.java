package com.example.motoparts;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CarrelloActivity extends AppCompatActivity {

    // ================= UI =================
    private TextView testoCarrello;
    private Button btnSvuota;
    private Button btnAcquistaCarrello;
    private Button btnVediScontrini;

    // ================= STORAGE =================
    private SharedPreferences pref;
    private List<String> prodotti = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrello);

        // ================= INIT VIEW =================
        testoCarrello = findViewById(R.id.testoCarrello);
        btnSvuota = findViewById(R.id.btnSvuotaCarrello);
        btnAcquistaCarrello = findViewById(R.id.btnAcquistaCarrello);
        btnVediScontrini = findViewById(R.id.btnVediScontrini);

        // ================= PREF =================
        pref = getSharedPreferences("APP_PREF", MODE_PRIVATE);

        caricaCarrello();
        mostraCarrello();

        // ================= LISTENER =================
        btnSvuota.setOnClickListener(v -> svuotaCarrello());

        btnVediScontrini.setOnClickListener(v -> mostraScontrini());

        btnAcquistaCarrello.setOnClickListener(v -> {

            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Conferma acquisto")
                    .setMessage("Vuoi confermare l'acquisto del carrello?")
                    .setPositiveButton("Sì", (dialog, which) -> acquistaCarrello())
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    // ================= CARICA CARRELLO =================
    private void caricaCarrello() {

        prodotti.clear(); // 🔥 FIX DUPLICATI

        String email = pref.getString("loggedEmail", null);

        if (email == null) {
            testoCarrello.setText("Devi effettuare il login");
            return;
        }

        String dati = pref.getString("cart_" + email, "");

        if (dati.isEmpty()) return;

        String[] items = dati.split("\\|");

        for (String i : items) {
            if (!i.trim().isEmpty()) {
                prodotti.add(i);
            }
        }
    }

    // ================= MOSTRA CARRELLO =================
    private void mostraCarrello() {

        if (prodotti.isEmpty()) {
            testoCarrello.setText("Carrello vuoto");
            return;
        }

        StringBuilder builder = new StringBuilder();

        for (String p : prodotti) {

            String[] dati = p.split(";");

            if (dati.length >= 3) {
                builder.append("🛒 ")
                        .append(dati[0])
                        .append("\nPrezzo: ")
                        .append(dati[2])
                        .append("€\n\n");
            }
        }

        testoCarrello.setText(builder.toString());
    }

    // ================= SVUOTA CARRELLO =================
    private void svuotaCarrello() {

        String email = pref.getString("loggedEmail", null);

        if (email != null) {
            pref.edit().remove("cart_" + email).apply();
        }

        prodotti.clear();
        mostraCarrello();

        Toast.makeText(this, "Carrello svuotato", Toast.LENGTH_SHORT).show();
    }

    // ================= ACQUISTA CARRELLO =================
    private void acquistaCarrello() {

        String email = pref.getString("loggedEmail", null);

        if (email == null) {
            Toast.makeText(this, "Utente non loggato", Toast.LENGTH_SHORT).show();
            return;
        }

        if (prodotti.isEmpty()) {
            Toast.makeText(this, "Carrello vuoto", Toast.LENGTH_SHORT).show();
            return;
        }

        try {

            StringBuilder csv = new StringBuilder();
            csv.append("Nome;Tipo;Prezzo\n");

            double totale = 0;

            for (String p : prodotti) {

                String[] dati = p.split(";");

                if (dati.length >= 3) {
                    csv.append(dati[0]).append(";")
                            .append(dati[1]).append(";")
                            .append(dati[2]).append("\n");

                    totale += Double.parseDouble(dati[2]);
                }
            }

            csv.append("\nTOTALE;;").append(totale).append("\n");

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm", Locale.getDefault());
            String data = sdf.format(new Date());

            String emailPulita = email.replace("@", "_").replace(".", "_");

            String fileName = "scontrino_" + emailPulita + "_" + data + ".csv";

            FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE);
            fos.write(csv.toString().getBytes());
            fos.close();

            // svuota tutto
            pref.edit().remove("cart_" + email).apply();
            prodotti.clear();

            mostraCarrello();

            testoCarrello.setText("Acquisto completato!\nScontrino creato");

            Toast.makeText(this, "Acquisto completato", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Errore durante acquisto", Toast.LENGTH_SHORT).show();
        }
    }

    // ================= LISTA SCONTRINI =================
    private List<String> listaScontrini() {

        List<String> lista = new ArrayList<>();

        String email = pref.getString("loggedEmail", null);

        if (email == null) return lista;

        String emailPulita = email.replace("@", "_").replace(".", "_");

        String[] files = fileList();

        for (String f : files) {
            if (f.startsWith("scontrino_" + emailPulita)) {
                lista.add(f);
            }
        }

        return lista;
    }

    // ================= MOSTRA SCONTRINI =================
    private void mostraScontrini() {

        List<String> scontrini = listaScontrini();

        if (scontrini.isEmpty()) {
            Toast.makeText(this, "Nessuno scontrino trovato", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] array = scontrini.toArray(new String[0]);

        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Scontrini salvati")
                .setItems(array, (dialog, which) -> {

                    String fileSelezionato = array[which];

                    String contenuto = formattaScontrino(fileSelezionato);

                    new androidx.appcompat.app.AlertDialog.Builder(this)
                            .setTitle("Scontrino")
                            .setMessage(contenuto)
                            .setPositiveButton("OK", null)
                            .show();
                })
                .show();
    }

    // ================= FORMATTA SCONTRINO =================
    private String formattaScontrino(String fileName) {

        StringBuilder builder = new StringBuilder();

        try {

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(openFileInput(fileName))
            );

            builder.append("🧾 SCONTRINO\n");
            builder.append("----------------------\n\n");

            String line;
            boolean primaRiga = true;
            double totale = 0;

            while ((line = br.readLine()) != null) {

                if (primaRiga) {
                    primaRiga = false;
                    continue;
                }

                if (line.trim().isEmpty()) continue;

                String[] dati = line.split(";");

                if (dati.length >= 3) {

                    String nome = dati[0];
                    String tipo = dati[1];
                    double prezzo = Double.parseDouble(dati[2]);

                    builder.append("📦 ").append(nome).append("\n");
                    builder.append("   Tipo: ").append(tipo).append("\n");
                    builder.append("   Prezzo: ").append(prezzo).append("€\n\n");

                    totale += prezzo;
                }
            }

            builder.append("----------------------\n");
            builder.append("💰 TOTALE: ").append(totale).append("€\n");

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.toString();
    }
}
package com.example.motoparts;

import com.example.motoparts.adapter.ComponentAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.motoparts.model.Prodotto;

public class MainActivity extends AppCompatActivity {

    // ================= UI =================
    private RecyclerView recyclerView;
    private TextView titoloSezione;

    private TextView tabMotore;
    private TextView tabScarichi;

    private ImageButton bottoneMenu;
    private DrawerLayout drawerMenu;

    private TextView titoloMenu;
    private TextView tvStatoLogIn;
    private TextView tvTitoloLogin;

    private EditText etEmailUtente;
    private EditText etPasswordUtente;
    private EditText etConfermaPassword;

    private Button btnLogin;
    private Button btnRegistrati;
    private Button btnVaiaRegistrati;
    private Button btnVaiaLogin;
    private Button btnLogout;

    private Button bottoneCarrello;

    // ================= LISTE =================
    private final List<TextView> listaCategorie = new ArrayList<>();
    private final List<Prodotto> listaProdotti = new ArrayList<>();
    private final List<Prodotto> listaProdottiFiltrati = new ArrayList<>();

    private ComponentAdapter componentAdapter;
    private SharedPreferences preferenze;

    // ================= LOGIN =================
    boolean modalitaRegistrazione = false;

    // ================= ON CREATE =================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferenze = getSharedPreferences("APP_PREF", MODE_PRIVATE);

        initViews();              // collega XML
        initListaProdotti();      // carica prodotti da file
        initReciclerView();       // setup lista prodotti
        initListaCategorie();     // setup categorie
        creaMenu();               // bottoni menu
        aggiornaMenu();
    }

    // ================= INIT VIEWS =================
    private void initViews() {

            recyclerView = findViewById(R.id.recyclerView);
            titoloSezione = findViewById(R.id.titoloSezione);

            tabMotore = findViewById(R.id.tabMotore);
            tabScarichi = findViewById(R.id.tabScarichi);

            bottoneMenu = findViewById(R.id.bottoneMenu);
            drawerMenu = findViewById(R.id.drawerMenu);

            tvStatoLogIn = findViewById(R.id.tvStatoLogIn);
            tvTitoloLogin = findViewById(R.id.tvTitoloLogin);

            etEmailUtente = findViewById(R.id.etEmailUtente);
            etPasswordUtente = findViewById(R.id.etPasswordUtente);
            etConfermaPassword = findViewById(R.id.etConfermaPassword);

            btnLogin = findViewById(R.id.btnLogin);
            btnRegistrati = findViewById(R.id.btnRegistrati);

            btnVaiaRegistrati = findViewById(R.id.btnVaiaRegistrati);
            btnVaiaLogin = findViewById(R.id.btnVaiaLogin);

            btnLogout = findViewById(R.id.btnLogout);

            bottoneCarrello = findViewById(R.id.bottoneCarrello);

            // ================= CARRELLO =================
            bottoneCarrello.setOnClickListener(v -> {

                String email = preferenze.getString("loggedEmail", null);

                Intent intent = new Intent(MainActivity.this, CarrelloActivity.class);
                startActivity(intent);
            });
    }

    // ================= RECYCLER VIEW =================
    private void initReciclerView() {

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        componentAdapter = new ComponentAdapter(listaProdottiFiltrati, this::visualizzaDettagliProdotto);

        recyclerView.setAdapter(componentAdapter);
    }

    // ================= CATEGORIE =================
    private void initListaCategorie() {

        listaCategorie.addAll(Arrays.asList(tabMotore, tabScarichi));

        for (TextView tab : listaCategorie) {
            tab.setOnClickListener(view -> selezionaCategoria((TextView) view));
        }

        selezionaCategoria(tabMotore);
    }

    // ================= FILTRO CATEGORIE =================
    private void selezionaCategoria(TextView tabSelezionata) {

        for (TextView tab : listaCategorie) {

            boolean selezionato = (tab == tabSelezionata);

            tab.setTextColor(getColor(R.color.white));
        }

        String categoriaSelezionata = tabSelezionata.getText().toString();

        if (categoriaSelezionata.equalsIgnoreCase("Motore")) {
            titoloSezione.setText("Componenti motore disponibili");
        } else {
            titoloSezione.setText("Componenti scarico disponibili");
        }

        filtraProdottiPerCategoria(categoriaSelezionata);
    }

    // ================= FILTRO PRODOTTI =================
    private void filtraProdottiPerCategoria(String categoriaSelezionata) {

        listaProdottiFiltrati.clear();

        for (Prodotto prodotto : listaProdotti) {
            if (prodotto.getTipo().equalsIgnoreCase(categoriaSelezionata)) {
                listaProdottiFiltrati.add(prodotto);
            }
        }

        componentAdapter.notifyDataSetChanged();
    }

    // ================= DETTAGLIO PRODOTTO =================
    private void visualizzaDettagliProdotto(Prodotto selezionato) {

        Intent intent = new Intent(this, DettaglioProdottoActvity.class);

        intent.putExtra(DettaglioProdottoActvity.NOME, selezionato.getNome());
        intent.putExtra(DettaglioProdottoActvity.DESCRIZIONE, selezionato.getDescrizione());
        intent.putExtra(DettaglioProdottoActvity.PREZZO, selezionato.getPrezzo());
        intent.putExtra(DettaglioProdottoActvity.IMAGE, selezionato.getIdImmagine());

        startActivity(intent);
    }

    // ================= CARICA PRODOTTI =================
    private void initListaProdotti() {

        try {

            InputStream stream = getResources().openRawResource(R.raw.listaprodotti);
            BufferedReader lettore = new BufferedReader(new InputStreamReader(stream));

            String riga;

            while ((riga = lettore.readLine()) != null) {

                String[] dati = riga.split(";");

                listaProdotti.add(new Prodotto(
                        dati[0], dati[1], dati[2],
                        dati[3], dati[4],
                        getResources().getIdentifier(dati[5], "drawable", getPackageName()),
                        Integer.parseInt(dati[6]),
                        dati[7], dati[8]
                ));
            }

            lettore.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= MENU =================
    private void creaMenu() {

        // apertura menu laterale
        bottoneMenu.setOnClickListener(v -> drawerMenu.openDrawer(GravityCompat.START));
        btnLogout.setOnClickListener(v -> logoutUtente());

        // bottone login/registrazione (switch modalità)
        btnLogin.setOnClickListener(v -> {

            if (modalitaRegistrazione) {
                registraUtente();
            } else {
                loginUtente();
            }
        });

        // vai a registrazione
        btnVaiaRegistrati.setOnClickListener(v -> {
            modalitaRegistrazione = true;
            aggiornaModalitaUI();
        });

        // torna a login
        btnVaiaLogin.setOnClickListener(v -> {
            modalitaRegistrazione = false;
            aggiornaModalitaUI();
        });
    }
    // ================= LOGIN =================
    private void loginUtente() {

        String email = etEmailUtente.getText().toString().trim();
        String password = etPasswordUtente.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Inserisci email e password", Toast.LENGTH_SHORT).show();
            return;
        }

        String utentiSalvati = preferenze.getString("utenti", "");

        String[] utenti = utentiSalvati.split("\\|");

        for (String u : utenti) {

            if (u.isEmpty()) continue;

            String[] dati = u.split(";");

            if (dati.length == 2) {

                if (email.equalsIgnoreCase(dati[0]) && password.equals(dati[1])) {

                    preferenze.edit().putString("loggedEmail", email).apply();

                    aggiornaMenu();
                    drawerMenu.closeDrawer(GravityCompat.START);

                    Toast.makeText(this, "Login effettuato", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }

        Toast.makeText(this, "Credenziali errate", Toast.LENGTH_SHORT).show();
        resetCampiLogin();
    }

    // ================= REGISTRAZIONE =================
    private void registraUtente() {

        String email = etEmailUtente.getText().toString().trim();
        String password = etPasswordUtente.getText().toString().trim();
        String conferma = etConfermaPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty() || conferma.isEmpty()) {
            Toast.makeText(this, "Compila tutti i campi", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(conferma)) {
            Toast.makeText(this, "Le password non coincidono", Toast.LENGTH_SHORT).show();
            return;
        }

        // controllo se utente esiste già
        String utentiSalvati = preferenze.getString("utenti", "");

        if (utentiSalvati.contains(email + ";")) {
            Toast.makeText(this, "Utente già registrato", Toast.LENGTH_SHORT).show();
            return;
        }

        // salva nuovo utente (email;password|email;password|...)
        String nuovoUtente = utentiSalvati + email + ";" + password + "|";

        preferenze.edit().putString("utenti", nuovoUtente).apply();

        modalitaRegistrazione = false;
        aggiornaModalitaUI();

        Toast.makeText(this, "Registrazione completata", Toast.LENGTH_SHORT).show();
    }


    // ================= UI LOGIN / REGISTER =================
    private void aggiornaModalitaUI() {

        etConfermaPassword.setVisibility(modalitaRegistrazione ? View.VISIBLE : View.GONE);

        btnLogin.setText(modalitaRegistrazione ? "Registrati" : "Login");

        btnVaiaRegistrati.setVisibility(modalitaRegistrazione ? View.GONE : View.VISIBLE);
        btnVaiaLogin.setVisibility(modalitaRegistrazione ? View.VISIBLE : View.GONE);
    }

    // ================= MENU LOGIN STATUS =================
    private void aggiornaMenu() {

        String loggedEmail = preferenze.getString("loggedEmail", null);

        if (loggedEmail != null) {

            tvStatoLogIn.setText("Benvenuto " + loggedEmail);

            etEmailUtente.setVisibility(View.GONE);
            etPasswordUtente.setVisibility(View.GONE);
            etConfermaPassword.setVisibility(View.GONE);

            btnLogin.setVisibility(View.GONE);
            btnLogout.setVisibility(View.VISIBLE);

            btnVaiaRegistrati.setVisibility(View.GONE);
            btnVaiaLogin.setVisibility(View.GONE);


        } else {

            tvStatoLogIn.setText("Non sei loggato");

            etEmailUtente.setVisibility(View.VISIBLE);
            etPasswordUtente.setVisibility(View.VISIBLE);

            btnLogin.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.GONE);

            aggiornaModalitaUI();
        }
    }
    // ================= LOGOUT =================
    private void logoutUtente() {

        // elimina login salvato
        preferenze.edit().remove("loggedEmail").apply();

        // reset UI
        aggiornaMenu();
        drawerMenu.closeDrawer(GravityCompat.START);

        Toast.makeText(this, "Logout effettuato", Toast.LENGTH_SHORT).show();
        resetCampiLogin();
    }
    private void resetCampiLogin() {
        etEmailUtente.setText("");
        etPasswordUtente.setText("");
        etConfermaPassword.setText("");
    }
}
package com.example.motoparts;
import com.example.motoparts.adapter.ComponentAdapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.example.motoparts.model.Prodotto;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView titoloSezione;
    private TextView tabMotore;
    private TextView tabScarichi;
    private final List<TextView> listaCategorie = new ArrayList<>();
    private final List<Prodotto> listaProdotti = new ArrayList<>();
    private final List<Prodotto> listaProdottiFiltrati = new ArrayList<>();
    private ComponentAdapter componentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initListaProdotti();
        initReciclerView();
        initListaCategorie();

    }
    private void initViews(){
        recyclerView = findViewById(R.id.recyclerView);
        titoloSezione = findViewById(R.id.titoloSezione);
        tabMotore = findViewById(R.id.tabMotore);
        tabScarichi = findViewById(R.id.tabScarichi);
    }
    private void initReciclerView(){
        recyclerView.setLayoutManager(new GridLayoutManager(this ,2));
        componentAdapter = new ComponentAdapter(listaProdottiFiltrati);
        recyclerView.setAdapter(componentAdapter);
    }
    private void initListaCategorie(){
        listaCategorie.addAll(Arrays.asList(tabMotore, tabScarichi));
        for(TextView tab : listaCategorie){
            tab.setOnClickListener(view -> selezionaCategoria((TextView) view));
        }
        selezionaCategoria(tabMotore);
    }
    private void selezionaCategoria(TextView tabSelezionata){
        for(TextView tab : listaCategorie){
            boolean selezionato = false;
            if(tab == tabSelezionata){
                selezionato = true;
                tab.setTextColor(getColor(selezionato? R.color.white : R.color.white));
            }
        }
        String categoriaSelezionata  = tabSelezionata.getText().toString();
        if(categoriaSelezionata.equalsIgnoreCase("Motore")){
            titoloSezione.setText("Componenti motore disponibili");
        }
        else{
            titoloSezione.setText("Componenti scarico disponibili");
        }
        filtraProdottiPerCategoria(categoriaSelezionata);
    }
    private void filtraProdottiPerCategoria(String categoriaSelezionata){
        listaProdottiFiltrati.clear();
        for(Prodotto prodotto : listaProdotti){
            if(prodotto.getTipo().equalsIgnoreCase(categoriaSelezionata)){
               listaProdottiFiltrati.add(prodotto);
            }
        }
        componentAdapter.notifyDataSetChanged();
    }
    private void initListaProdotti(){
        listaProdotti.addAll(Arrays.asList(new Prodotto("Athena", "84.7cc", 464, "Alluminio", "Cilindro yz85", R.drawable.cilindro_yz_85, 0, "DESCRIZIONE", "Motore"),
        new Prodotto("Athena", "124.8cc", 550, "Alluminio", "Cilindro yz125", R.drawable.cilindro_yz_125, 0, "DESCRIZIONE", "Motore"),
        new Prodotto("Athena", "249cc", 600, "Alluminio", "Cilindro yz250", R.drawable.cilindro_yz_250, 0, "DESCRIZIONE", "Motore"),

        new Prodotto("Yamaha", "Scomponibile", 160, "Alluminio", "Testa yz85", R.drawable.testa_yz_85, 0, "DESCRIZIONE", "Motore"),
        new Prodotto("Yamaha", "Scomponibile", 200, "Alluminio", "Testa yz125", R.drawable.testa_yz_125, 0 , "DESCRIZIONE", "Motore"),
        new Prodotto("Yamaha", "Scomponibile", 190, "Alluminio", "Testa yz250", R.drawable.testa_yz_250, 0 , "DESCRIZIONE", "Motore"),

        new Prodotto("NGK", "BR10EG", 18, "", "Candela yz85",R.drawable.candela_yz_85, 0, "DESCRIZIONE", "Motore"),
        new Prodotto("NGK", "BR10EG", 18, "", "Candela yz85",R.drawable.candela_yz_125, 0, "DESCRIZIONE", "Motore"),
        new Prodotto("NGK", "BR9EG", 21, "", "Candela yz250",R.drawable.candela_yz_250, 0, "DESCRIZIONE", "Motore"),

        new Prodotto("Keihin", "PWK 28", 180, "Alluminio", "Carburatore yz85" , R.drawable.carburatore_yz_85,  0, "DESCRIZIONE", "Motore"),
        new Prodotto("Keihin", "PWK 38", 210, "Alluminio", "Carburatore yz125" , R.drawable.carburatore_yz_125,  0, "DESCRIZIONE", "Motore"),
        new Prodotto("Keihin", "PWK 38", 210, "Alluminio", "Carburatore yz250" , R.drawable.carburatore_yz_250,  0, "DESCRIZIONE", "Motore"),

        new Prodotto("FMF", "", 250, "Titanio", "Pancia yz85", R.drawable.pancia_yz85, 0, "DESCRIZIONE", "Scarichi" ),
        new Prodotto("FMF", "", 280, "Titanio", "Pancia yz125", R.drawable.pancia_yz85, 0, "DESCRIZIONE" , "Scarichi"),
        new Prodotto("FMF", "", 320, "Titanio", "Pancia yz250", R.drawable.pancia_yz250, 0, "DESCRIZIONE" , "Scarichi"),

        new Prodotto("FMF", "Shorty", 230, "Titanio", "Silenziatore yz85", R.drawable.scarico_yz85, 0, "DESCRIZIONE" , "Scarichi"),
        new Prodotto("FMF", "Shorty", 260, "Titanio", "Silenziatore yz125", R.drawable.scarico_yz125, 0, "DESCRIZIONE" , "Scarichi"),
        new Prodotto("FMF", "Shorty", 290, "Titanio", "Silenziatore yz250", R.drawable.scarico_yz250, 0, "DESCRIZIONE" , "Scarichi"),



        new Prodotto("Yamaha", "250cc", 700, "Alluminio", "Cilindro yzf250", R.drawable.cilindro_yzf250, 1, "DESCRIZIONE", "Motore"),
        new Prodotto("Yamaha", "4500cc", 900, "Alluminio", "Cilindro yzf450", R.drawable.cilindro_yzf450, 1, "DESCRIZIONE", "Motore"),

        new Prodotto("Athena", "Monoblocco", 520, "Alluminio", "Testa yzf250", R.drawable.testa_yzf250, 1, "DESCRIZIONE", "Motore"),
        new Prodotto("Athena", "Monoblocco", 710, "Alluminio", "Testa yzf450", R.drawable.testa_yzf450, 1, "DESCRIZIONE", "Motore"),

        new Prodotto("NGK", "CR8E", 20, "", "Candela yzf250", R.drawable.candela_yzf250, 1, "DESCRIZIONE", "Motore"),
        new Prodotto("NGK", "CR8E", 20, "", "Candela yzf450", R.drawable.candela_yzf450, 1, "DESCRIZIONE", "Motore"),

        new Prodotto("Keihin", "Iniezione Elettronica", 340, "", "Iniettori yzf250" , R.drawable.iniettori_yzf250,  1, "DESCRIZIONE", "Motore"),
        new Prodotto("Keihin", "Iniezione Elettronica", 400, "", "Iniettori yzf450" , R.drawable.iniettori_yzf450,  1, "DESCRIZIONE", "Motore"),

        new Prodotto("FMF", "Megabomb", 500, "Titanio", "Collettore yzf250", R.drawable.colletore_yzf250, 1, "DESCRIZIONE" , "Scarichi"),
        new Prodotto("FMF", "Megabomb", 600, "Titanio", "Collettore yzf450", R.drawable.colletore_yzf450, 1, "DESCRIZIONE", "Scarichi" ),

        new Prodotto("FMF", "Powercore", 500, "Titanio", "Silenziatore yzf250", R.drawable.scarico_yzf250, 1, "DESCRIZIONE" , "Scarichi"),
        new Prodotto("FMF", "Powercore", 800, "Titanio", "Silenziatore yzf450", R.drawable.scarico_yzf450, 1, "DESCRIZIONE" , "Scarichi")));
    }

}
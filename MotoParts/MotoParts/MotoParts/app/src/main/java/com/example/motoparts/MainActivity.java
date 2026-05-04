package com.example.motoparts;
import com.example.motoparts.adapter.ComponentAdapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import java.util.ArrayList;
import java.util.List;
import com.example.motoparts.model.Prodotto;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<String> titlesMotore = new ArrayList<>();
        titlesMotore.add("Pistoni-Cilindro");
        titlesMotore.add("Testa");
        titlesMotore.add("Candela");
        titlesMotore.add("Carburatore");

        List<Integer> images = new ArrayList<>();
        // inserire immagini pezzi reali

        List<Prodotto> tuttiProdotti = new ArrayList<>();


        List<Integer> imagesScarichi = new ArrayList<>();
        //inserire immagini scarichi reali


        ComponentAdapter adapter = new ComponentAdapter(tuttiProdotti);
        recyclerView.setAdapter(adapter);
    }
}
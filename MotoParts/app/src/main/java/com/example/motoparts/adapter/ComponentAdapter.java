package com.example.motoparts.adapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.motoparts.model.Prodotto;
import java.util.List;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.motoparts.R;



public class ComponentAdapter extends RecyclerView.Adapter<ComponentAdapter.ViewHolder> {
    public interface OnProductClickListener {
        void onProductClick(Prodotto prodotto);
    }
    List<Prodotto> prodotti;
    private final OnProductClickListener listener;

    public ComponentAdapter( List<Prodotto> prodotti, OnProductClickListener listener) {
        this.prodotti = prodotti;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prodotto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       Prodotto prodotto = prodotti.get(position);
       holder.nome.setText(prodotto.getNome());
       holder.image.setImageResource(prodotto.getIdImmagine());
       //holder.itemView.setOnClickListener(v -> Toast.makeText(v.getContext(),prodotto.getNome(),Toast.LENGTH_SHORT).show());
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onProductClick(prodotto);
            }
        });
    }

    @Override
    public int getItemCount() {
        return prodotti.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nome;
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.nomeProdotto);
            image = itemView.findViewById(R.id.immagineProdotto);
        }
    }
}

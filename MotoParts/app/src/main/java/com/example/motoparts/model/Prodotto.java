package com.example.motoparts.model;

import java.util.Map;

public class Prodotto {
    private String marca;
    private String modello;
    private int prezzo;
    private String materiale;
    private String nome;
    private int idImmagine;

    private int categoria;
    private String descrizione;
    private String tipo;
    public Prodotto(String marca, String modello, int prezzo, String materiale, String nome, int idImmagine, int categoria,String descrizione, String tipo){
        this.marca = marca;
        this.modello = modello;
        this.prezzo = prezzo;
        this.materiale = materiale;
        this.nome = nome;
        this.idImmagine = idImmagine;
        this.categoria = categoria;
        this.descrizione = descrizione;
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMarca() {
        return marca;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModello() {
        return modello;
    }

    public void setModello(String modello) {
        this.modello = modello;
    }

    public int getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(int prezzo) {
        this.prezzo = prezzo;
    }

    public String getMateriale() {
        return materiale;
    }

    public void setMateriale(String materiale) {
        this.materiale = materiale;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdImmagine() {
        return idImmagine;
    }

    public void setIdImmagine(int idImmagine) {
        this.idImmagine = idImmagine;
    }
}

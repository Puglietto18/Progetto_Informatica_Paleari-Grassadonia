package com.example.motoparts.model;

import java.util.Map;

public class Prodotto {
    private String marca;
    private String modello;
    private int prezzo;
    private Map<String, String> attributi;
    private String nome;
    private int idImmagine;
    public Prodotto(String marca, String modello, int prezzo, Map<String, String> attributi, String nome, int idImmagine){
        this.marca = marca;
        this.modello = modello;
        this.prezzo = prezzo;
        this.attributi = attributi;
        this.nome = nome;
        this.idImmagine = idImmagine;
    }

    public String getMarca() {
        return marca;
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

    public Map<String, String> getAttributi() {
        return attributi;
    }

    public void setAttributi(Map<String, String> attributi) {
        this.attributi = attributi;
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

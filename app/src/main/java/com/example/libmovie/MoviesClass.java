package com.example.libmovie;

public class MoviesClass {

    String nome;
    String id;
    String cognome;

    static String nomeFile = "Tre Uomini e una Gamba";

    public MoviesClass( String nome, String cognome, String id ){
        this.nome = nome;
        this.cognome = cognome;
        this.id = id;

    }

    public String getNome() {
        return nome;
    }

    public String getCognome(){
        return cognome;
    }
    public String getId() {
        return id;
    }

    public static void main(String[] args) {
        System.out.println( nomeFile );
    }
}

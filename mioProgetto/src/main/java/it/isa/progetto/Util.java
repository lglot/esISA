package it.isa.progetto;

/**
 *
 * Classe di utility
 *
 */
public class Util {

    private int a;

    public static void stampa() {
        System.out.println("stampa di prova");
    }

    public Util() {
        this.a = 0;
    }

    public void update(int a) {
        this.a = a;
    }

    public void printValore() {
        System.out.println(this.a);
    }
}

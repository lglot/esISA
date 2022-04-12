package it.isa.progetto;

import java.util.*;

public class Collezioni {

    public static void testMap() {
        Map<String, String> hm = new HashMap<>();

        hm.put("A", "Italia");
        hm.put("B", "Francia");
        hm.put("C", "Germania");

        // Stampe
        System.out.println(hm);
        // oppure stampo valori
        for (String s : hm.values()) {
            System.out.println(s);
        }
        // stampo keys
        for (String s : hm.keySet()) {
            System.out.println(s);
        }
    }

    public static void liste() {
        List<String> lista = new ArrayList<>();
        lista.add("a");
        lista.add("b");
        lista.add("casa");

        System.out.println("Dimensione lista: " + lista.size());

        for (String s : lista)
            System.out.println(s);

        // rimuove elementi dalla lista (viene modificata quella iniziale)
        lista.removeIf(x -> x.length() > 1);
        System.out.println(lista);

        // conversione da array di stringhe a lista di stringe
        String[] parole = { "a", "b", "c" };
        List<String> paroleList1 = new ArrayList<>();

        for (String s : parole) {
            paroleList1.add(s);
        }

        System.out.println(paroleList1);

        // modo più veloce
        List<String> paroleList2 = new ArrayList<>(Arrays.asList(parole));
        System.out.println(paroleList2);

    }

    public static void main(String[] args) {
        System.out.println("In main");
        // Collezioni.liste();
        Collezioni.testMap();
    }

}

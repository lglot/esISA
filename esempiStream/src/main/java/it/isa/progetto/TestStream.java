package it.isa.progetto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.*;
import java.util.List;
import java.util.Optional;
import java.util.function.DoubleBinaryOperator;

public class TestStream {

    public static void provaForEach() {
        // Stream<String> parole = Stream.of("a", "b", "c");
        // parole.forEach(x -> System.out.println(x));

        String[] arrayParole = { "casa", "albero", "oca" };
        List<String> lParole = new ArrayList<>(Arrays.asList(arrayParole));
        List<String> lParole2 = new ArrayList<>(Arrays.asList(arrayParole));
        lParole.stream().forEach(x -> System.out.println(x));
        lParole2.stream().forEach((String x) -> {
            String y = x + "_a";
            System.out.println(y);
        });

    }

    public static void provaFilter() {
        String[] arrayParole = { "casa", "albero", "oca" };
        List<String> lParole = new ArrayList<>(Arrays.asList(arrayParole));

        Stream<String> paroleLunghe = lParole.stream().filter(x -> x.length() > 3);
        paroleLunghe.forEach(x -> System.out.println(x));
    }

    public static void provaMap() {
        String[] arrayParole = { "casa", "albero", "oca" };
        List<String> lParole = new ArrayList<>(Arrays.asList(arrayParole));

        Stream<String> paroleMaiuscole = lParole.stream()
                .filter(x -> x.length() > 3)
                .map(String::toUpperCase)
                .map(s -> s.replace("A", "O"));

        paroleMaiuscole.forEach(System.out::println);

    }

    public static void provaInfiniti() {

        // generate usa interfaccia funzionale supplier, limito a 10 la lunghezza dello
        // stream
        Stream<Double> numeriRandom = Stream.generate(Math::random).limit(10);

        // collect è un operazione terminale, dopo questa non posso più utilizzare lo
        // stream
        List<Double> listaNumero = numeriRandom.collect(Collectors.toList());
        System.out.println(listaNumero);

        Stream<Double> numeriRandom2 = Stream.generate(Math::random).limit(10);
        System.out.println(numeriRandom2.count());

    }

    public static void provaOptional() {
        Stream<Double> numeriRandom = Stream.generate(Math::random).limit(10);
        // gli optional possono esserre vuoti / null
        Optional<Double> risultato = numeriRandom.filter(x -> x > 1).findFirst();

        try {
            // se non c'è nulla mi parte l'eccezione
            System.out.println(risultato.get());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // come gestire l'optional vuoto, mi stampa -1 se non c'è nulla
        System.out.println(risultato.orElse((double) -1));

        // se non c'è nulla si può eseuire una funzione con le lambda exp (ci si aspetta
        // un double quindi deve tornare un double)
        System.out.println(risultato.orElseGet(() -> {
            double a = 2;
            double b = 3;
            return a + b;
        }));

    }

    public static void esercizio() {
        String[] arrayParole = { "casa", "albero", "oca" };
        List<String> lParole = new ArrayList<>(Arrays.asList(arrayParole));

        Stream<String> ris = lParole.stream().filter(x -> x.length() > 3 && x.startsWith("a"));

        // converto in lista
        List<String> risList = ris.collect(Collectors.toList());
        risList.forEach(System.out::println);

    }

    public static void main(String[] args) {
        System.out.println("STREAM");
        // TestStream.provaForEach();
        // TestStream.provaFilter();
        // TestStream.provaMap();
        // TestStream.provaInfiniti();
        // TestStream.provaOptional();
        TestStream.esercizio();
    }

}

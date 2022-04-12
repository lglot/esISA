# Comandi

Creiamo un repository git, aggiungiamo il `.gitignore` per java e maven poi creiamo un progetto maven.

```
mvn archetype:generate -DgroupId=it.isa.stream -DartifactId=esempiStream -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false
```

Nel file `pom.xml` devo sostituire 1.7 con almeno 8 (se compare un errore durante la compilazione)
```
<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>8</maven.compiler.source>
    <maven.compiler.target>8</maven.compiler.target>
</properties>
```

Creo un file `Collezioni.java` con la classe `main`.
Ricordarsi di inserire nella prima riga `package it.isa.stream;`.

Ricordo: compilo con `mvn clean compile` ed eseguo con `mvn exec:java` dopo aver inserito nel `pom.xml`
```
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>exec-maven-plugin</artifactId>
    <version>3.0.0</version>
    <executions>
        <execution>
            <id>execution1</id>
            <phase>compile</phase>
            <configuration>
                <mainClass>it.isa.stream.Collezioni</mainClass>
            </configuration>
            <goals>
                <goal>java</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```
In questo modo, quando faccio `mvn clean compile` viene anche eseguito il codice.
Importante: rimuovere il tag `<pluginManagement>`.

## Collezioni
List
```
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public static void liste() {
    List<String> lista = new ArrayList<String>();
    lista.add("a");
    lista.add("b");
    lista.add("casa");
    System.out.println(lista.size());

    for(int i = 0; i < lista.size(); i++) {
        System.out.println(lista.get(i));
    }

    lista.removeIf(x -> x.length() > 1);

    // oppure
    System.out.println(lista);
}
```
Abbiamo usato una lambda expression, che ha la sintassi: una lista di parametri formali separati da virgole racchiusi tra parentesi (se più di 1), il token `->`, il corpo che può essere una singola espressione o più espressioni racchiuse da `{}`.

Conversione array di stringhe in una List:
```
String[] arrayParole = {"k","d","e"};
List<String> listParole = new ArrayList<String>();
for(String p : arrayParole) {
    listParole.add(p);
}
System.out.println(listParole);
```
Più velocemente:
```
List<String> lp = new ArrayList<String>(Arrays.asList(arrayParole));
System.out.println(lp);
```

`Map`:
```
import java.util.HashMap;
import java.util.Map;
public static void testMap() {
    Map<String,String> hm = new HashMap<String, String>();

    hm.put("Inghilterra", "Londra");
    hm.put("Francia", "Parigi");
    hm.put("Italia", "Roma");

    for(String s : hm.values()) {
        System.out.println(s);
    }

    for (String s : hm.keySet()) {
        System.out.println(s);
    }
}
```

## Stream
Importo
```
import java.util.stream.Stream;
```

Inizio creando uno stream di stringhe che chiamo `parole` poi stampo tutte le parole utilizzando `forEach` (attenzione che è terminale):
```
Stream<String> parole = Stream.of("a","b");
parole.forEach(x -> System.out.println(x));
```

Posso partire anche da una `List` per uno stream:
```
String[] arrayParole = {"casa","albero","oca"};
List<String> lp = new ArrayList<String>(Arrays.asList(arrayParole));
lp.stream().forEach(x -> System.out.println(x));
```
con `stream()` ottengo uno stream sul quale posso lavorare.

Con lambda expression più interessante:
```
lp.stream().forEach((String x) -> {String y = x + "_a"; System.out.println(y);});
```

Vediamo `filter`, che filtra gli elementi che rispettano la condizione:
```
public static void provaFilter() {
    String[] arrayParole = { "casa", "albero", "oca" };
    List<String> lp = new ArrayList<String>(Arrays.asList(arrayParole));
    Stream<String> paroleLunghe = lp.stream().filter(w -> w.length() > 3);
    paroleLunghe.forEach(x -> System.out.println(x));
}
```

Vediamo `map`, che applica la stessa operazione o serie di operazioni a tutti gli elementi dello stream:
```
public static void provaMap() {
    String[] arrayParole = { "casa", "albero", "oca" };
    List<String> lp = new ArrayList<String>(Arrays.asList(arrayParole));
    Stream<String> paroleLunghe = lp.stream().filter(w -> w.length() > 3);
    Stream<String> paroleLungheUpper = paroleLunghe.map(String::toUpperCase);
    paroleLungheUpper.forEach(x -> System.out.println(x));
}
```
Attenzione che se abbiamo già utilizzato `forEach` su `paroleLunghe` non possiamo applicare nuovi stream (errore runtime).
Possiamo utilizzare anche lambda expression:
```
Stream <String> paroleLungheReplace = paroleLunghe.map(s -> s.replace('a', 'k'));
```

Vediamo come utilizzare stream infiniti.
`Collectors.toList()` ci permette di collezionare gli elementi di uno stream sottoforma di lista.
`collect()` applica una certa riduzione agli elementi dello stream.
```
import java.util.stream.Collectors;
public static void provaInfiniti() {
    Stream<Double> randomNumbers = Stream.generate(Math::random).limit(10);
    List<Double> coll = randomNumbers.collect(Collectors.toList());
    System.out.println(coll);
}
```

Vediamo ora le riduzioni.
Esempio con metodo `count`.
```
public static void provaRiduzioni() {
    Stream<Double> randomNumbers = Stream.generate(Math::random).limit(10);
    System.out.println(randomNumbers.count());
}
```

Vediamo `Optional`:
```
import java.util.Optional;

public static void provaOptional() {
    Stream<Double> randomNumbers = Stream.generate(Math::random).limit(10);
    Optional<Double> result = randomNumbers.filter(x -> x > 1).findFirst();
    
    System.out.println(result.orElse((double) -1));
    // questo lancia eccezione
    // System.out.println(result.get());

    // Optional<String> result = paroleLungheUpper.filter(s -> s.length() > 21).findFirst();
    // System.out.println(result.orElseGet(() -> System.getProperty("user.name")));

    // oppure posso chiamare una funzione
    // qui ottengo un errore in compilazione
    // System.out.println(result.orElseGet(() -> System.getProperty("user.name")));
    System.out.println(result.orElseGet(() -> {double a = 3; double b = 6; return a+b;}));

}
```

## Esercizo
Data una lista di stringhe, stampare quelle con più di tre lettere e che iniziano con 'a', utilizzando gli stream.
```
public static void esercizio() {
    String[] arrayParole = { "casa", "albero", "oca", "ab"};
    List<String> lp = new ArrayList<String>(Arrays.asList(arrayParole));

    // soluzione
    System.out.println(lp.stream().filter(x -> (x.startsWith("a") && x.length() > 3)).collect(Collectors.toList()));
    // oppure
    System.out.println(lp.stream().filter(x -> x.startsWith("a")).filter(x -> x.length() > 3).collect(Collectors.toList()));
}
```
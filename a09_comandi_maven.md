# Comandi Maven
Per la prima esecuzione, mi assicuro che non esista (o che sia vuota) la cartella `~/.m2` (cartella che contiene le dipendenze maven).

Mi posiziono in una cartella e creo un nuovo progetto maven con
```
mvn archetype:generate -DgroupId=it.isa.progetto -DartifactId=mioProgetto -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false
```
Archetype è un plugin che permette la generazione di template per un progetto.

Appare anche la cartella `~/.m2`.

Viene creata tutta la gerarchia delle cartelle e il file `pom.xml` già popolato.
Notare la presenza di una classe `App.java` con metodo `main` e una classe di test.

Compiliamo con `mvn clean compile` (crea cartella `target`), test con `mvn clean test`, package con `mvn clean package`.
Con test viene eseguito anche clean, con package vengono eseguiti sia compile che test.

Aggiungo un file nella cartella `mioProgetto/src/main/java/it/isa/progetto/` dove è già presente `App.java`.
Creo `Util.java` con dentro:

```
package it.isa.progetto;
public class Util {
    public static void printMessage(String message) {
        System.out.println(message);
    }
    public int a;
    public Util() {
        a = 0;
    }
    public void update() {
        a = 3;
    }
    public void printA() {
        System.out.println(a);
    }
}
``` 

Poi uso questi metodi nella classe App (file `App.java`).
```
System.out.println( "Hello World!" );
Util.printMessage("printed in class");
Util u = new Util();
u.update();
u.printA();
``` 

Compilo con `mvn clean compile`.
Come faccio ad eseguire il codice? 
Due opzioni utilizzando il maven exec plugin:
- `mvn exec:java -Dexec.mainClass="it.isa.progetto.App"` (specificando quindi dove si trova la classe main)
- modificando il file `pom.xml` inserendo 
```
<plugin>
  <groupId>org.codehaus.mojo</groupId>
  <artifactId>exec-maven-plugin</artifactId>
  <version>3.0.0</version>
  <configuration>
    <mainClass>it.isa.progetto.App</mainClass>
    <!-- <arguments>
      <argument>argument1</argument>
    </arguments> -->
  </configuration>
</plugin>
```
e poi usando il comando `mvn exec:java`.

Proviamo a creare il sito del nostro progetto: `mvn site`.
Può generare un errore perché il plugin `maven-site-plugin` non viene trovato nella versione specifica.
Se succede, cambiamo con versione `3.7.1` nel file `pom.xml`.
Ora `mvn site` funziona e crea la cartella `target/site`.

Proviamo a scrivere una pagina di documentazione per il nostro sito: creo una cartella `site/markdown` in `src`.
Creo un file `index.md` in `mardown`.
Breve recap su come scrivere md file.
Posso usare diversi formati per scrivere la documentazione.
Vedere sito [https://maven.apache.org/guides/mini/guide-site.html](https://maven.apache.org/guides/mini/guide-site.html).
Popolo il file.
Quando faccio `mvn clean site` verrà convertito in `index.html`.

Proviamo a creare le API doc: `mvn javadoc:javadoc`.
Ha creato `target/site/apidocs`. 
Apro `index.html`.

Se voglio generare tutto, sito e apidocs, uso sia `mvn site` che `mvn javadoc:javadoc`.
Notare che in `index.html` del sito complessivo c'è solo ``Project information'' nella barra laterale sinistra.
Posso fare sia sito che javadoc con `mnv site`: aggiungo in nel tag `reporting` un plugin che si chiama javadoc
```
  <reporting>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-javadoc-plugin</artifactId>
        <version>3.3.1</version>
      </plugin>
    </plugins>
  </reporting>
```
Dopo aver eseguito `mvn clean site`, nel file `index.html` complessivo del sito, ho anche ``Project reports'' nella barra laterale sinistra, che prima non c'era.

Installiamo una dipendenza, `apache common math` (https://commons.apache.org/proper/commons-math/) che mette a disposizione dei metodi per effettuare calcoli.

Aggiungo al file pom.xml
```
<dependencies>
  <dependency>
      <groupId>org.apache.commons</groupId>
      <artifactId>commons-math</artifactId>
      <version>2.2</version>
    </dependency>
</dependencies>
```
Queste configurazioni posso trovarle sul maven central repository: https://search.maven.org/artifact/org.apache.commons/commons-math/2.2/jar.

Il tutto sarà scaricato ed installato in: `~/.m2/repository/org/apache/commons/commons-math/2.2`.

Posso utilizzare le classi messe a disposizione dal package.
Modifichiamo il file `App.java`.
```
...
import org.apache.commons.math.stat.descriptive.*;
...
DescriptiveStatistics stats = new DescriptiveStatistics();
int[] a = {1,3,54};
for (int i = 0; i < a.length; i++) {
    stats.addValue(a[i]);
}

// Compute some statistics
System.out.println(stats.getMean());
...
```

Compilo con `mvn clean compile` poi eseguo con `mvn exec:java`.

Se genero il sito ora vedo le dipendenze aggiunte.

Con `mvn javadoc:jar` genero le api doc: ora nella cartella ho il jar sia dell'app (se ho utilizzato `mvn clean` o ho aggiornato il progetto devo eseguire nuovamente `mvn package`) che delle javadoc: abbiamo generato le api per la documentazione.

Con `mvn install` infine vedo che è stata creato il jar del mio programma (`mioProgetto-1.0-SNAPSHOT.jar`) che si trova in `~/.m2/repository/it/isa/progetto/mioProgetto/1.0-SNAPSHOT/`.
Posso quindi ora utilizzare le funzionalità (classi) che abbiamo appena sviluppato in un altro programma.
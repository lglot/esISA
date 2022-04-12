# Guida Esercitazione Modelio

Nuovo progetto con Modelio: file, create a project e spuntare new java project.

Aprire gerarchia cartelle finché non si incontra quella con il nome del progetto tutto minuscolo.
Tasto destro, create diagram, class diagram.

Creiamo il diagramma di classe, partendo dall'immagine sulle slide 05_generazione_automatica_codice.

- Creo interfaccia Personaggio. Poi, doppio click su di essa e posso modificarla. Andiamo nel tab Java e poi selezioniamo Java element: in questo modo riusciremo a convertirla automaticamente in Java
- Inserisco una operation a personaggio: getForza, poi aggiungo valore di ritorno
- Aggiungo le altre due intefacce Umano e Mostro
- Ora specifichiamo che Umano e Mostro estendono Personaggio: clicco su generalization (inheritance, freccia con punta in alto). Notare che se clicco su realizzazione (implementazione, freccia accanto), non posso cliccare su Mostro perché è essa stessa un'interfaccia e non può implementare
- Creo classi Eroe, Licantropo e Vampiro
- Per creare un attributo utilizzo il tasto a:. Per forza (e tutti gli altri metodi), ricordarsi di selezionare acces mode none, altrimenti vengono creati i metodi getter e setter. Aggiungo costruttore con icona operazione. Non inserisco i metodi delle interfacce, per nessuna delle classi
- Specifichiamo le implementazioni: freccia tratteggiata, poi clicco su yes all: notare l'aggiunta dei metodi
- Notare che getForza non viene ereditato da Personaggio perché Modelio non è così intelligente
- Aggiungo classe Giochiamo con metodo main (statico, di classe)
- Per specificare relazione tra Giochiamo ed altre classi (relazione d'uso) clicco su icona con cerchio verde: clicco su Giochiamo poi Eroe e seleziono usage

Generiamo il codice Java: barra laterale, clicco su cartella, Java desing, generate.

Per vedere il codice: clicco sulla classe, in basso, tab Java, poi edit (ultima icona).

A cosa serve fare tutto questo?
Per ottenere in maniera automatica lo scheletro del sistema software partendo da un diagramma UML (che potrebbe esserci richiesto).

Ora vediamo come fare il contrario: da codice ad UML.
Creo un programma java semplice, come quello delle slide.

Sempre con modelio, nuovo progetto, Java project.
Vado nella cartella sulla barra laterale, destro, Java designer, reverse, reverse from source.
Selezionare la cartella intera. Next, next, reverse: sono apparse le classi.

Ora creo il diagramma: tasto destro sulla classe, create diagram, class diagram, ok.
Il diagramma inizialmente è vuoto.
Posso trascinare gli elementi sul foglio ed aggiungere la classi, metodi o istanze.

Il procedimento è macchinoso, soprattutto se voglio vedere tutto in sistema, ma è molto comodo per decidere quali elementi mostrare (per esempio, durante la progettazione software).
# Esercitazione Git

Creo un nuovo repository con `git init`.

Bisogna impostare un username ed una mail che identificheranno il nostro account.

Controllo che non siano già impostati:
- `git config --get user.name`
- `git config --get user.email`

Se non sono impostati, devo fare:
- `git config --global user.name "[name]"`
- `git config --global user.email "[email address]"`

Si può configurare l'editor di default con:
- `git config --global core.editor "vim"`

Dopo `git init`, viene creata una cartella `.git`.
Trascuriamone il contenuto.

Creo un file `Main.java` e scrivo un programma.
```
public class Main {
    public static void main(String[] args) {
        System.out.println("Main");
    }
}
```
Compilo e ottengo il file `Main.class`.

Con `git status` vedo lo stato dei miei file.
```
Untracked files:
  (use "git add <file>..." to include in what will be committed)

        Main.class
        Main.java
```
Vedo che ci sono due file non tracciati da git (cioè file che non esistono
nel precedente snapshot).

Si vuole tracciare solo l'avanzamento dei file sorgente, non dei file compilati
perché questi sono specifici del compilatore utilizzato e non sono portabili.
Inserisco un file `.gitignore` nel repository per specificare quali file ignorare.
Il nome del file deve essere esattamente `.gitignore`.

Quali file devo tracciare e quali no? Ci sono siti che generano questi file,
come https://www.toptal.com/developers/gitignore.
Entro e cerco `java`.
Copio il contenuto nel mio file `.gitignore` e poi salvo.

Eseguo ancora `git status`.
```
Untracked files:
  (use "git add <file>..." to include in what will be committed)

        .gitignore
        Main.java
```
Notare che il file con estensione `.class` non è più presente.

Ora, con il comando `git add` aggiungo i file nella *staging area*, dove sono
contenuti i file che saranno inclusi nel prossimo commit.

`git add Main.java .gitignore`

Nuovamente `git status`.
```
Changes to be committed:
  (use "git rm --cached <file>..." to unstage)

        new file:   .gitignore
        new file:   Main.java
```

<!-- Solitamente, il primo commit contiene il file `.gitignore` ma ora ci sono 2 file
pronti per il commit. Rimuoviamo il tracciamento di `Main.java`.

`git rm --cached Main.java`

Nuovamente `git status`.
```
Changes to be committed:
  (use "git rm --cached <file>..." to unstage)

        new file:   .gitignore

Untracked files:
  (use "git add <file>..." to include in what will be committed)

        Main.java
``` -->

Tracciamo ora il file con `git commit -m "Commit iniziale"`.
```
[master (root-commit) 4854e57] commit iniziale
 2 files changed, 36 insertions(+)
 create mode 100644 .gitignore
 create mode 100644 Main.java
```

Creo un file `README.md`, lo aggiungo con `git add`. Poi `git status`.

<!-- Per qualche motivo mi sono sbagliato e voglio toglierlo dalla staging area.
Posso usare `git reset HEAD README.md`.
```
Untracked files:
  (use "git add <file>..." to include in what will be committed)

        README.md
``` -->

Lo aggiungo e creo un commit.
Ora modifico il file `Main.java` aggiungendo nuove istruzioni.

Con `git status` vedo lo stato del file: modificato.

Posso fare due cose: o cancellare le modifiche fatte o spostarle in area di
staging.

Proviamo prima ad eliminarle con `git checkout -- Main.java`: notare che le
modifiche sono state eliminare (quindi bisogna essere sicuri di volerle
cancellare).

Inserisco una modifica e commit. Ripeto una seconda volta ma questa volta
senza commit.

Come faccio a vedere le differenze con l'ultimo commit?
`git diff`. Con `+` e `-` sono identificate le due differenti versioni.
Aggiungo questa versione con `git add` e `git commit`.

Come faccio a vedere tutti i commit eseguiti finora? `git log`.
```
commit d2f29585deabe5fd50b50291fea365585d6eb2aa
Author: Damiano Azzolini <damiano.azzolini@unife.it>
Date:   Fri Mar 18 11:50:36 2022 +0100

    seconda modifica

commit 038653c2861b4ce2cba1b66ed910a8e25db59f9d
Author: Damiano Azzolini <damiano.azzolini@unife.it>
Date:   Fri Mar 18 11:45:38 2022 +0100

    Prima modifica

commit 37c810c42f4df0f1b52e8eb4a75b56e49e1896fe
Author: Damiano Azzolini <damiano.azzolini@unife.it>
Date:   Fri Mar 18 11:42:16 2022 +0100

    readme

commit 4854e57f8de54aa71e815b92afbba883d01851b9
Author: Damiano Azzolini <damiano.azzolini@unife.it>
Date:   Fri Mar 18 11:39:19 2022 +0100

    commit iniziale
```

Posso fare il diff tra due commit: `git diff d2f29585 37c810`.
```
diff --git a/Main.java b/Main.java
index 650b8ef..bed4e8c 100644
--- a/Main.java
+++ b/Main.java
@@ -1,8 +1,5 @@
 public class Main {
     public static void main(String[] args) {
         System.out.println("Main");
-        System.out.println("Seconda print");
-        System.out.println("Terza print");
-
     }
 }
```

Supponiamo di voler tornare al commit readme, perché gli ultimi due hanno
inserito dei bug.
Usiamo `git revert`.
<!-- Posso farlo in due modi: `git revert` e `git reset`. -->

`git revert` crea un nuovo commit che annulla un commit specifico.
`git revert 37c810c42f4`.

Nel mio caso, il file `README.md` non è più tracciato.
Posso utilizzarlo più volte e cancellarne uno alla volta.

<!-- Con `git reset` cancello proprio i commit dalla storia. -->
<!-- Provare `git reset 038653c`. -->

<!-- Ora inserisco un file `prove.txt` e commit.
Mi accorgo che è un file che non dovrebbe essere tracciato, come faccio ad
eliminarlo dal tracciamento?
Se lo inserisco in `.gitignore`, le modifiche sul file continuano ad essere
tracciate ugualmente.

Uso `git rm`, che può essere utilizzato solamente su file già in commit.

Con `git rm prove.txt` viene proprio modificato il file, con
`git rm --cached prove.txt` no.
Provare le due versioni. -->

Posso salvare le modifiche temporanee ai file con `git stash`.
Posso recuperarle con `git stash pop`.
Se modifico la stessa linea, devo prima fare il commit poi mi trovo un conflitto che posso risolvere come per il merge.

Posso sostituire il messaggio dell'ultimo commit con `git commit --amend -o -m "rinomino"`.

Se voglio sostituire più messaggi posso usare `git rebase -i HEAD~n` dove `n` è il numero di commit da considerare.
Si apre una schermata con le possibili operazioni.
Individuo il commit che mi serve poi sostituisco `pick` con `reword`.
Cioè, da
```
pick fe16118 secondo commit
```
modifico in
```
reword fe16118 secondo commit
```
Si apre una nuova finestra dove posso rinominare il commit.

Posso tornare ad un commit specifico *CANCELLANDO* tutta la storia dal quel punto in avanti con `git reset --hard <hash>`, per esempio `git reset --hard fe1611893d36`.
Attenzione che viene cancellato tutto.

In realtà posso eventualmente recuperare i commit eliminati con due comandi: prima `git reflog` ottengo la lista dei commit effettuati in ogni momento (`git log` invece mi dice i commit che sono accessibili dalla posizione corrente).

Poi posso fare nuovamente `git reset --hard <hash>`, per esempio `git reset --hard 302d437`, per tornare nella posizione identificata dal commit.

## Branch
Ora consideriamo i branch che finora abbiamo trascurato.

Non ci sono cambiamenti sui quali fare commit.
Con `git branch` ottengo la lista di tutti i branch.
Ora abbiamo solamente il master.

Creo un nuovo branch chiamato test con `git branch test` e mi sposto con il
comando `git checkout test`.
Avrei potuto fare tutto in un comando solo con `git checkout -b test`.

Modifico `Main.java` poi add e commit.
Se torno nel master, i cambiamenti spariscono.
Come faccio ad includere nel branch master i cambiamenti?
Uso `git merge test`.

Posso eliminare eliminare il branch test con `git branch -d test`.

## Repository Remoti
Utilizzo github, ma lo stesso vale per bitbucket.

Vado su github e creo un nuovo repository (privato).

Per clonare un repository remoto: `git clone`.
Mi sposto nel repository, aggiungo un file (`test.txt`), commit e poi lo carico con `git push origin master`.
`Origin` indica il repository remoto dal quale ho fatto il clone.
Ora su github appare il file.
Aggiungo un file (README.md) dall'interfaccia web (per mostrare come gestire
il merge).

Ora aggiorno il mio repository locale: posso usare `git fetch` e poi
`git merge origin master` o `git pull`.

Ora faccio una modifica al file `README.md` da interfaccia web e in locale.
Commit di entrambi.
Da locale provo a fare push (`git push origin master`): viene rifiutato perché nel repository remoto ci sono delle modifiche che non sono presenti nel repository locale.
Prima devo fare il `pull`: ottengo un conflitto perché sono state modificate le stesse linee nel file.
Apro il file da gestire e vedo le differenze (con un editor abbastanza
avanzato è più facile).
Sistemo poi ora posso fare il push (dopo il commit).

## Gestione repository open source
Capire come usare issues, pull request ecc.

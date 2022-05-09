# Comandi Docker
Guida per installare
https://docs.docker.com/engine/install/ubuntu/

Usare `sudo /etc/init.d/docker start`
se si ha l'errore
`Cannot connect to the Docker daemon at unix:/var/run/docker.sock. Is the docker daemon running?`

Usare `sudo chmod 666 /var/run/docker.sock` (read e write per owner e group)
se si ha l'errore `Got permission denied while trying to connect to the Docker daemon socket`

Vedere: https://chmod-calculator.com/ per i permission bit

## Creo account dockerhub
Registrazione, dockerid rappresenta il nome utente.

Creo un repository remoto: tag Repositories poi click su Create Repository.
Nome: `isa`, poi `Create`.

## Pratica
Controllo se ho delle immagini (`docker image ls`).
Controllo se ho dei container (`docker container ls`) o ho dei container interrotti (`docker container ls -a`).

Proviamo ad utilizzare `docker container run hello-world`: lancio il container passando come argomento l'immagine che voglio eseguire (`hello-world`).
Questa immagine sarà cercata prima in locale e poi su dockerhub.
Una volta che l'immagine è stata scaricata, docker la trasforma in un container e la esegue.
```
Unable to find image 'hello-world:latest' locally
latest: Pulling from library/hello-world
2db29710123e: Pull complete 
Digest: sha256:10d7d58d5ebd2a652f4d93fdd86da8f265f5318c6a73cc5b6a9798ff6d2b2e67
Status: Downloaded newer image for hello-world:latest

Hello from Docker!
...
To try something more ambitious, you can run an Ubuntu container with:
$ docker run -it ubuntu bash
```
Appare `:latest` perché, non avendo specificato il tagname, di default è `latest` (ultima versione).
Leggere l'output.

Ora, `docker image ls` mostra la nuova immagine. `docker container ls` mostra nulla mentre `docker container ls -a` mostra il container che abbiamo appena eseguito (il nome che troviamo è stato associato di default).

Proviamo ora, come suggerisce l'output del comando `docker container run hello-world`, ad eseguire `docker run -it ubuntu bash`.
```
Unable to find image 'ubuntu:latest' locally
latest: Pulling from library/ubuntu
e0b25ef51634: Pull complete 
Digest: sha256:9101220a875cee98b016668342c489ff0674f247f6ca20dfc91b91c0f28581ae
Status: Downloaded newer image for ubuntu:latest
root@382d9f22e121:/# 
```
Sono nella bash del mio container. Posso utilizzare i comandi della bash come `ls`, ecc.

Per uscire dal container, `ctrl p + ctrl q` (questo devo farlo in un terminale oppure modificare le impostazioni di vscode oppure cambiare le detach keys).
Con `docker container ls` vedo che il container è ancora in esecuzione.
Mi ricollego al container con `docker container attach <nome>`.
Per vedere che rimane in esecuzione in background, eseguire il comando, per esempio, `top`, poi `ctrl p` `ctrl q` e `docker container attach <nome>`.
Si riapre la schermata di prima.

Per interrompere un container, `docker container stop <nome>` oppure `ctrl d` dal container.
Con `docker inspect <nome>` ispeziono un container e ottengo alcune informazioni.

Posso far ripartire un container con `docker container start <nome>`.
Con `docker container ls` vedo che il container è stato riavviato.
Posso collegarmi con `docker container attach <nome>`.
Notare che se eseguo nuovamente `docker run -it ubuntu bash` viene creato un nuovo container e non viene riaperto quello precedente.

Cancello un container con `docker container rm <nome>` (o `<id>`).

Assegno un nome ad un container con l'opzione `--name`: `docker run --name ciao -it ubuntu bash`.

Notare che le operazioni sono fatte istantaneamente (a differenza delle VM).

Con `docker container ls -a`, vedo che tutti i container derivano dalla stessa immagine.

`docker container prune` cancella tutti i container inattivi

## Creazione e distribuzione di un'immagine
Andiamo nel repository dell'applicazione Java sugli stream creata utilizzando Maven e poi facciamo `mvn package`.
Possiamo creare un container contenente l'applicazione creata da noi (oppure mettendo solo il jar generato).

Vediamo come inserire tutta la cartella.

Creo il file `Dockerfile` nella cartella del progetto poi inserisco
```
# parto da un'immagine esistente che ha un ambiente di 
# lavoro con già installato Maven e Java 11: https://hub.docker.com/_/maven
FROM maven:3.8.5-jdk-11

WORKDIR home/isa

# copio il file pom.xml nella WORKDIR
# COPY <src> <dest>
COPY pom.xml .

# copio la cartella src nella WORKDIR
# importante specificare src/ così crea la
# cartella e non copia solamente il contenuto
COPY src src/
```
Creo l'immagine: `docker image build --tag isa:2022 .`.
Ci mette un po' poi crea l'immagine.

Testiamolo: `docker container run -it isa:2022 bash` poi `mvn compile`.

Come faccio a pubblicarla in remoto?
Facciamo il push su dockerhub.
Prima `docker login`. 
Poi associo `isa:2022` con il mio username e repository su dockerhub: `docker image tag isa:2022 damianodamianodamiano/isa:2022`.
Lo username sarà diverso per ogni studente.
Infine `docker image push damianodamianodamiano/isa:2022` (o con lo username corretto).
Su dockerhub ora vedo il container: https://hub.docker.com/r/damianodamianodamiano/isa/tags

Provare ora ad eseguire: `docker container run -it damianodamianodamiano/isa:2022 bash` poi `mvn compile`.
In questo modo, scaricate la mia immagine e la eseguite.

Posso fare delle modifiche al repository, poi nuovemente `docker image build --tag isa:2022 .` e `docker image tag isa:2022 damianodamianodamiano/isa:2022`.
Con `docker pull damianodamianodamiano/isa:2022` aggiorno l'immagine.
Eseguendo nuovamente `docker container run -it damianodamianodamiano/isa:2022 bash` poi `mvn compile` vedo le nuove modifiche.
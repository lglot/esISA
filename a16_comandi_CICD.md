# Comandi

Creo un progetto Maven con una semplice classe ed un test con Junit.
```
mvn archetype:generate -DgroupId=it.isa.pipe -DartifactId=esempiPipelines -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false
```

Aggiungo JUnit
```
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.8.2</version>
    <scope>test</scope>
</dependency>
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.0.0-M6</version>
</plugin>
```
Versione 1.8 compilatore.

Aggiungo un metodo add in `App.java` che somma due interi ed un test.
```
public int add(int a, int b) {
    return a + b;
}
```

```
@Test
public void testSum() {
    App op = new App();
    assertEquals(5,op.add(2,3));
}
```

Uso TDD, quindi prima test poi codice: modifico `appTest` per aggiornare i package e poi inserisco un test per un metodo che esegue la somma.

Voglio utilizzare le bitbucket pipeline per eseguire i vari comandi nel cloud, per far si che tutti gli sviluppatori possano vedere eventualmente quali test falliscono.
Inoltre, posso testare la mia applicazione su diverse macchine rappresentate come container.

Creo un nuovo repository pubblico *vuoto* (no README.md e no .gitignore) 'esempiPipeline' su bitbucket.

Inizializzo repository in locale, aggiungo .gitignore, poi collego con quello su bitbucket:
```
git remote add origin https://damiano_azzolini@bitbucket.org/damiano_azzolini/esempio.git
git push -u origin master
```

Sulla sinistra dell'interfaccia web è presente la tab pipeline.
Scegliamo il template java Maven build a maven project.
Vediamo che nella parte default script c'è già un comando maven.
Lascio quello (verify, che esegue anche build e test) poi commit file.
La pipeline ha avuto successo.

Vediamo cosa succede se c'è un errore:
```
public int sub(int a, int b) {
    return b - a; % b ed a sono invertiti, corretto è a - b
}
``` 
```
@Test
public void testSub() {
    App op = new App();
    assertEquals(1,op.sub(3,2));
}
``` 
Non eseguo il codice in locale (questo è un errore, si dovrebbe sempre prima testare in locale e poi caricare sul repo remoto).
Aggiorno il repository locale, aggiungo e poi push, così parte la pipeline.
La pipeline fallisce.
Analizzo.
Sistemo ed aggiorno.

Validator per il file `bitbucket-pipelines.yml`: https://bitbucket-pipelines.atlassian.io/validator

## Distribuzione Applicazione
Ora abbiamo fatto CI, ora vogliamo fare CD e consegnare l'applicazione come immagine docker.

Prima creo il Dockerfile:
```
FROM maven:3.8.5-jdk-11

WORKDIR home/esempiPipelines

COPY target/esempiPipelines-1.0-SNAPSHOT.jar .
```

Poi creo un repository remoto su dockerhub: `damianodamianodamiano/pipeline`

Durante CD vorrei poter aggiornare direttamente una potenziale immagine Docker. Come fare? Dovrei aggiungere nel file `bitbucket-pipelines.yml` il comando docker login e passare in chiaro username e password.
Non va bene dal punto di vista della sicurezza.
Bitbucket offre la possibilità di definire delle variabili di ambiente che sono accessibili solamente a me e non agli alri.
Definisco `DOCKER_HUB_USERNAME` e `DOCKER_HUB_PASSWORD`.

Infine, modifico il file yml di configurazione.
Aggiungo a script
```
# creo immagine docker pronta per essere pubblicata su dockerhub
- docker image build --tag damianodamianodamiano/pipeline:0.1 .
# login su dockerhub
- docker login --username $DOCKER_HUB_USERNAME --password $DOCKER_HUB_PASSWORD
# push dell'immagine su dockerhub
- docker push damianodamianodamiano/pipeline
```

Devo anche aggiungere la possibilità di utilizzare docker, nel campo services:
```
- step:
    name: Build and Test
    caches:
        - maven
    script:
        # build, test e package automatico
        - mvn -B verify --file pom.xml
        # creo immagine docker pronta per essere pubblicata su dockerhub
        - docker image build --tag damianodamianodamiano/pipeline:0.1 .
        # login su dockerhub
        - docker login --username $DOCKER_HUB_USERNAME --password $DOCKER_HUB_PASSWORD
        # push dell'immagine su dockerhub
        - docker push damianodamianodamiano/pipeline
    after-script:
        # Collect checkstyle results, if any, and convert to Bitbucket Code Insights.
        - pipe: atlassian/checkstyle-report:0.3.0
    services:
        - docker
```

Link: https://support.atlassian.com/bitbucket-cloud/docs/run-docker-commands-in-bitbucket-pipelines/

Dopo che la pipeline è terminata, su dockerhub vedo l'immagine.

## Miglioramento
Vediamo come migliorare e suddividere le due parti: creo due step che separano CI da CD.
```
image: maven:3.6.3

pipelines:
  default:
      - step:
          name: Build and Test
          caches:
            - maven
          script:
            # build, test e package automatico
            - mvn -B verify --file pom.xml
          after-script:
              # Collect checkstyle results, if any, and convert to Bitbucket Code Insights.
            - pipe: atlassian/checkstyle-report:0.3.0
          services:
            - docker
      - step:
          name: Security Scan
          script:
            # Run a security scan for sensitive data.
            # See more security tools at https://bitbucket.org/product/features/pipelines/integrations?&category=security
            - pipe: atlassian/git-secrets-scan:0.5.1
      - step:
         name: Deploy su Docker Hub
         services:
            - docker
         script:
             # creo immagine docker pronta per essere pubblicata su dockerhub
             - docker image build --tag damianodamianodamiano/pipeline:0.1 .
             # login su dockerhub
             - docker login --username $DOCKER_HUB_USERNAME --password $DOCKER_HUB_PASSWORD
             # push dell'immagine su dockerhub
             - docker push damianodamianodamiano/pipeline
```

Errore che ottengo:
```
Step 3/3 : COPY target/esempiPipelines-1.0-SNAPSHOT.jar .
COPY failed: file not found in build context or excluded by .dockerignore: stat target/esempiPipelines-1.0-SNAPSHOT.jar: file does not exist
```

Ad ogin step viene creato un nuovo container quindi se creo un jar in un container, questo non è visto nel container che rappresenta lo step successivo.
Devo utilizzare la keyword artifacts per poter rendere visibili alcuni artefatti agli step successivi.
```
              artifacts:
                - target/**
```

## Miglioramento
Come faccio a fare solo integrazione sul branch develop e sia integrazione che distribuzione sul branch master?

Utilizzo la keyword branches
```
#  Template maven-build

#  This template allows you to test and build your Java project with Maven.
#  The workflow allows running tests, code checkstyle and security scans on the default branch.

# Prerequisites: pom.xml and appropriate project structure should exist in the repository.

image: maven:3.6.3

pipelines:
  default:
      # per tutti i branch che non hanno una pipeline specifica
      - step:
          name: Build and Test
          caches:
            - maven
          script:
            # build, test e package automatico
            - mvn verify --file pom.xml
          after-script:
              # Collect checkstyle results, if any, and convert to Bitbucket Code Insights.
            - pipe: atlassian/checkstyle-report:0.3.0

  branches:
      master:
          - step:
              name: Build and Test
              caches:
                - maven
              script:
                # build, test e package automatico
                - mvn -B verify --file pom.xml
              after-script:
                  # Collect checkstyle results, if any, and convert to Bitbucket Code Insights.
                - pipe: atlassian/checkstyle-report:0.3.0
              services:
                - docker
              artifacts:
                - target/**
          - step:
              name: Security Scan
              script:
                # Run a security scan for sensitive data.
                # See more security tools at https://bitbucket.org/product/features/pipelines/integrations?&category=security
                - pipe: atlassian/git-secrets-scan:0.5.1
          - step:
             name: Deploy su Docker Hub
             services:
                - docker
             script:
                 # creo immagine docker pronta per essere pubblicata su dockerhub
                 - docker image build --tag damianodamianodamiano/pipeline:0.1 .
                 # login su dockerhub
                 - docker login --username $DOCKER_HUB_USERNAME --password $DOCKER_HUB_PASSWORD
                 # push dell'immagine su dockerhub
                 - docker push damianodamianodamiano/pipeline

```

Ora, sul master faccio sia CI che CD, su tutti gli altri branch solo CI (provare con, per esempio, develop e develop2).
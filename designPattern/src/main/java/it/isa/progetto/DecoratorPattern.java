package it.isa.progetto;

//il pattern non ci dice di usare run, ma la struttura generale dell'ereditarietà delle classi

interface NewEngine {
    public void run();
}

class NewCar implements NewEngine {
    public void run() {
        System.out.println("The car is running");
    }
}

class NewRocket implements NewEngine {
    public void run() {
        System.out.println("The rocket is running");
    }
}

abstract class EngineDecorator implements NewEngine {
    protected NewEngine decoratorEngine;

    public EngineDecorator(NewEngine decoratorEngine) {
        this.decoratorEngine = decoratorEngine;
    }

    // la classe è astratta perchè non abbiamo implementato il metodo run
    // dell'interfaccia
    public void run() {
        decoratorEngine.run();
    }
}

class BoostedEngineDecorator extends EngineDecorator {

    public BoostedEngineDecorator(NewEngine car) {
        super(car);
    }

    private void boostIt() {
        System.out.println("Boosted");
    }

    public void run() {
        // variabile della classe padre
        decoratorEngine.run();
        boostIt();
    }
}

public class DecoratorPattern {
    public static void esegui() {
        System.out.println("Decorator pattern");
        NewEngine e = new NewCar();
        e.run();

        // estendiamo il funzionamento della classe
        NewEngine boostedCar = new BoostedEngineDecorator(new NewCar());

        boostedCar.run();

    }
}
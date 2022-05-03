package it.isa.progetto;

// file Enjine.java
abstract class Engine {
    abstract void init();

    abstract void start();

    abstract void stop();

    // final non può essere sovrascritto
    public final void turnOn() {
        init();
        start();
        stop();
    }
}

// file Rocket.java -> implementazione di Engine
class Rocket extends Engine {
    public void init() {
        System.out.println("Init rocket");
    }

    public void start() {
        System.out.println("Stop rocket");
    }

    public void stop() {
        System.out.println("Stop rocket");
    }
}

// file Car.java
class Car extends Engine {

    @Override
    void init() {
        System.out.println("Init Car");

    }

    @Override
    void start() {
        System.out.println("Start Car");

    }

    @Override
    void stop() {
        System.out.println("Stop Car");
    }

}

// client
public class TemplatePattern {
    public static void esegui() {
        System.out.println("Template Pattern");
        Engine e = new Rocket();
        e.turnOn();

        e = new Car();
        e.turnOn();
    }
}

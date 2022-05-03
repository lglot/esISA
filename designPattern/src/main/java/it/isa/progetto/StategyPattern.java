package it.isa.progetto;

interface Strategy {
    public String doOp(String s);
}

class ConverLowercase implements Strategy {
    public String doOp(String s) {
        return s.toLowerCase();
    }
}

class ConvertUppercase implements Strategy {
    public String doOp(String s) {
        return s.toUpperCase();
    }

}

class Capitalize implements Strategy {
    public String doOp(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}

class Context {
    private Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public String doOp(String s) {
        return strategy.doOp(s);
    }

}

public class StategyPattern {
    public static void esegui() {
        System.out.println("Strategy pattern");
        String s = "pRoVa";

        // il funzionamento di doOp cambia a runtime e dipende dal contesto che gli
        // passiamo
        Context c = new Context(new ConverLowercase());
        System.out.println(c.doOp(s));

        c = new Context(new ConvertUppercase());
        System.out.println(c.doOp(s));

        c = new Context(new Capitalize());
        System.out.println(c.doOp(s));

    }
}

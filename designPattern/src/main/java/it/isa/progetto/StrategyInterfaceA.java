package it.isa.progetto;

//vediamo lo strategy pattern con le classi anonime

interface StrategyInterface {
    public String doOp(String s);

}

public class StrategyInterfaceA {
    public static void esegui() {
        System.out.println("Strategy pattern classi anonime");

        String s = "pRoVa";

        // classe anonima
        // l'utilizzo delle classi anonime è utile quando devo usare questa classe una
        // sola volta
        StrategyInterface siLower = new StrategyInterface() {
            public String doOp(String s) {
                return s.toLowerCase();
            }
        };

        System.out.println(siLower.doOp(s));

    }
}

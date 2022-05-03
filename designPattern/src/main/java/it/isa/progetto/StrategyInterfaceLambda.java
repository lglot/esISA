package it.isa.progetto;

interface StrategyIntLambda {
    public String doOp(String s);
}

public class StrategyInterfaceLambda {

    // metodo che restituisce una lambda exp
    public static StrategyIntLambda Converter() {
        return s -> s.toUpperCase();
    }

    public static void esegui() {
        System.out.println("Strategy pattern con lambda");
        String s = "pRoVa";

        // implemento il metodo usando una lambda exp, visto che l'interfaccia ha solo
        // un metodo
        StrategyIntLambda siLowerLambda = stringa -> stringa.toLowerCase();

        System.out.println(siLowerLambda.doOp(s));

        // equivalente a prima solo che uso un metodo che mi ritorna una lambda exp
        StrategyIntLambda siUpperLambda = Converter();

        System.out.println(siUpperLambda.doOp(s));
    }
}

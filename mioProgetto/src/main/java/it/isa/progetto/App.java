package it.isa.progetto;

import org.apache.commons.math.stat.descriptive.*;
//import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

public class App {
    public static void main(String[] args) {
        Util.stampa();
        Util u = new Util();
        u.update(5);
        u.printValore();

        DescriptiveStatistics stats = new DescriptiveStatistics();
        int[] a = { 1, 2, 3, 54 };

        for (int i = 0; i < a.length; i++) {
            stats.addValue(a[i]);
        }
        System.out.println(stats.getMean());
    }
}

import java.util.ArrayList;
import java.util.List;

public class Lambda {
    public static void main(String argv[]) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        printList(list);

        List<Integer> list2 = map(list, Lambda::doppio);
        printList(list2);

        List<Integer> list3 = map(list, Lambda::triplo);
        printList(list3);

        List<Integer> list4 = map(list, Lambda::quadrato);
        printList(list4);

        // doppio with lambda
        List<Integer> list5 = map(list, (Integer x) -> x * 2);
        // triplo with lambda
        List<Integer> list6 = map(list, (Integer x) -> x * 3);
        // quadrato with lambda
        List<Integer> list7 = map(list, (Integer x) -> x * x);

        printList(list5);
        printList(list6);
        printList(list7);

    }

    static Integer doppio(Integer x) {
        return x * 2;
    }

    static Integer triplo(Integer x) {
        return x * 3;
    }

    static Integer quadrato(Integer x) {
        return x * x;
    }

    // private static List<Integer> map(List<Integer> list, Funzione g) {
    // List<Integer> list2;
    // list2 = new ArrayList<Integer>();
    // for (Integer i : list) {
    // list2.add(g.apply(i));
    // }
    // return list2;
    // }

    private static <T1, T2> List<T2> map(List<T1> list, Funzione<T1, T2> g) {
        List<T2> list2 = new ArrayList<T2>();
        for (T1 i : list) {
            list2.add(g.apply(i));
        }
        return list2;
    }

    private static void printList(List<Integer> list) {

        list.forEach(e -> {
            System.out.print(e + " ");
        });
        System.out.println();
    }
}
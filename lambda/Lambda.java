import java.util.ArrayList;
import java.util.List;

public class Lambda {
    public static void main(String argv[]) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        printList(list);

        List<Integer> list2 = new ArrayList<Integer>();
        for (Integer i : list) {
            list2.add(i * 2);
        }
        printList(list2);

    }

    private static void printList(List<Integer> list) {
        for (Integer i : list) {
            System.out.print(i);
            System.out.print(" ");
        }
        System.out.println();
    }
}
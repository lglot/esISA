package it.isa.progetto;

// da inserire in un file MyIterator.java
interface MyIterator {
    void rewind();

    int nextElement();

    boolean hasNextElement();
}

// da inserire in un file MyCollection.java
interface MyCollection {
    public MyIterator getIter();

}

// da inserire in un file MyListIterator.java
class MyListIterator implements MyIterator {

    private int index = -1;
    private int[] lista;

    public MyListIterator(MyListArray la) {
        lista = la.lista;
    }

    @Override
    public void rewind() {
        index = -1;
    }

    @Override
    public int nextElement() {
        index++;
        return lista[index];
    }

    @Override
    public boolean hasNextElement() {
        return index < lista.length - 1;
    }

}

// da inserire in un file MyListArray.java
class MyListArray implements MyCollection {

    public int[] lista;

    public MyListArray(int n) {
        lista = new int[n];

        for (int i = 0; i < n; i++) {
            lista[i] = i * 10;
        }
    }

    @Override
    public MyIterator getIter() {
        return new MyListIterator(this);
    }

}

public class IteratorPattern {
    public static void esegui() {
        System.out.println("Iterator pattern");
        MyCollection la = new MyListArray(10);
        MyIterator li = la.getIter();

        while (li.hasNextElement()) {
            System.out.println("Element: " + li.nextElement());
        }
    }
}

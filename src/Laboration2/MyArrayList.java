package Laboration2;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

@SuppressWarnings("serial")
public class MyArrayList<E> implements Serializable, Cloneable, Iterable<E>,
        Collection<E>, List<E>, RandomAccess {

    private int size;
    private E[] array;

    // ---------------------------------------------------------------

    public static void main(String[] args) {
        MyArrayList<String> testing = new MyArrayList<String>();
    }

    // ---------------------------------------------------------------

    /*
    Här skapar vi vår generiska array och initierar den med en standardstorlek.
    Vi väljer att göra den av typen E[], där E är en generisk typ. Java tillåter
    ej direkt skapande av generiska arrays. (t.ex. new E[10]), därför behöver
    vi använda en workaround där en Object-array skapas och sedan typecastas
    till E[].

    Vi gör alltså en generisk array med startstorlek på 10 element. Den
    Kommer att användas för att lagra objekt av typen E. När man tilldelat
    den arrayen en annan typ (t.ex. string), så kommer den ej vara generisk
    längre utan nu av typen string. I och med detta kan den bara ta nya
    strängar som argument utan att skapa ett problem.
     */
    public MyArrayList() {
        array = (E[]) new Object[10];
    }

    /*
    Skapar här en metod för att kolla så man är inom index.
    Bara för att man ska slippa ha med hela den koden flera
    gånger om i andra metoder, det är lättare att bara kalla
    på denna.
     */
    private void indexCtrl(int index, int value) {
        if (index < 0 || index >= value) {
            throw new IndexOutOfBoundsException();
        }
    }

    /*
    Ska skapa en tom lista med en specificerad initialkapacitet.
    Detta ska vara en int som heter initialCapacity.
    Ska kasta IllegalArgumentException om värdet på
    initicalCapacity är negativt.
     */
    public MyArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        } else {
            array = (E[]) new Object[initialCapacity];
        }
    }

    // -- 1 --

    /*
    Ska returnera ett tal som visar antalet element som
    finns i listan.
     */
    @Override
    public int size() {
        return size;
    }

    /*
    Ska returnera true ifall listan ej innehåller något element.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /*
    Ska rensa alla element i listan. Kan man kanske bara
    skapa en ny, tom lista och sätta size = 0?
     */
    @Override
    public void clear() {
        size = 0;
    }

    // -- 2 --

    /*
    Ska öka kapaciteten hos ArrayList instansen vid behov.
    Den ska se till så att listan kan hålla åtminstone så många
    element som är specificerat hos minCapacity argumentet.
     */
    public void ensureCapacity(int minCapacity) {
        if (minCapacity > array.length) {
            Object[] temp = new Object[array.length * 2];

            for (int i = 0; i < array.length; i++) {
                temp[i] = array[i];
            }
            array = (E[]) temp;
        }
    }

    /*
    Ska skala ner kapaciteten hos arrayen så att den blir
    samma storlek som antalet element i listan. Tanken
    är att en applikation ska kunna använda detta för att
    minimenra largingenskravet för en ArrayList instans.
     */
    public void trimToSize() {
        if (size < array.length) {
            Object[] temp = new Object[this.size];
            for (int i = 0; i < size; i++) {
                temp[i] = array[i];
            }
            array = (E[]) temp;
        }
    }

    // -- 3 --

    @Override
	/*
	Addera ett specifikt element på en specifik plats i listan,
	Skifta alla eventuella element som kommer efter i listan till höger.
	Måste hålla koll så att index ej är utanför range.
	 */
    public void add(int index, E element) {
        indexCtrl(index, size + 1);
        ensureCapacity(this.size + 1);

        //Loopar igenom bakifrån för att ej överskriva befintlig data som behöver sparas.
        for (int i = size; i > index; i--) {
            array[i] = array[i - 1];
        }
        array[index] = element;
        size++;
    }

    /*
    Här ska vi lägga till ett element i slutet av listan och
    sedan returnera true för att bekräfta att det faktiskt adderats.
     */
    @Override
    public boolean add(E e) {
        this.add(size, e);
        return true;
    }

    // -- 4 --

    /*
    Ska returnera ett element på en specifik plats i
    listan. Behöver även kolla så att index ej är
    utanför range.
     */
    @Override
    public E get(int index) {
        indexCtrl(index, size);
        return array[index];
    }

    /*
    Ska ersätta ett specifikt element i listan med något annat och
    returnera det gamla elementet. Måste även kolla så att index ej
    är utanför range.
    */
    @Override
    public E set(int index, E element) {
        indexCtrl(index, size);
        E oldObj = get(index);
        array[index] = element;
        return oldObj;
    }

    // -- 5 --
	/*
	Ta bort ett element på en specifik position i listan.
	Flyttar eventuella element till vänster i listan.
	Ska även returnera elementet som blev borttaget från listan.
	 */
    @Override
    public E remove(int index) {
        indexCtrl(index, size);
        E oldEle = get(index);

        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        //Plocka bort det sista elementet (en dubblett rest)
        array[size - 1] = null;
        size--;
        return oldEle;
    }

    /*
    Ska plocka bort alla element f.o.m fromIndex t.o.m toIndex.
    Ska flytta föregående element till vänster (reducera dess index)
    Kortar alltså ner listan med (toIndex - fromIndex) element.
    Skulle det vara så att toIndex == fromIndex så händer inget.

    Ska även kasta IndexOutOfBoundsException ifall fromIndex eller toIndex
    är utanför range.
     */
    protected void removeRange(int fromIndex, int toIndex) {
        if ((fromIndex < 0 || fromIndex >= size() || toIndex > size() || toIndex < fromIndex)) {
            throw new IndexOutOfBoundsException();
        }

        //Loopar igenom och tar bort element från fromIndex -> intIndex.
        for (int i = fromIndex; i < toIndex; i++) {
            remove(fromIndex);
        }

    }

    // -- 6 --

    /*
    Ska returnera index av första träffen av ett specifikt element.
    Innehåller listan ej elementet ska -1 returneras.
    */
    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < this.size; i++) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < this.size; i++) {
                if (array[i].equals(o)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /*
    Tar bort första händelsen av ett objekt ifall det finns med i listan.
    Innehåller listan ej elementet så förblir listan oförändrad.
    Returnerar true ifall listan innehöll det specifika elementet.
     */
    @Override
    public boolean remove(Object o) {
        int index = this.indexOf(o);
        if (index != -1){
            remove(index);
            return true;
        }

//        for (int i = 0; i < size; i++) {
//            if (o.equals(array[i])) {
//                remove(i);
//                return true;
//            }
//        }
        return false;
    }

    //Ska returnera true ifall listan innehåller det specifika elementet.
    @Override
    public boolean contains(Object o) {
        return this.indexOf(o) != -1;
    }

            // -- 7 --

    /*
    Ska returnera en "Shallow" kopia av listan. Som jag förstått det innebär detta att
    man ska skapa en lista som refererar till originallistan.
     */
            @Override
            public Object clone () {
                MyArrayList<E> kopia = new MyArrayList<>(size);
                for (int i = 0; i < this.size; i++) {
                    kopia.array[i] = this.array[i];
                }
                kopia.size = this.size;
                return kopia;
            }

    /*
    Ska returnera en array som innehåller alla element i denna lista.
    Dom behöver även vara i följd (från första till sista)
    Metoden ska skapa en ny array som användaren fritt kan förändra utan att
    ändra originalet.
     */
            @Override
            public Object[] toArray () {
                return ((MyArrayList<E>)clone()).array;
            }

            // --- Rör ej nedanstående kod -----------------------------------

    public MyArrayList(Collection < ? extends E > c){
                throw new UnsupportedOperationException();
            }

            private class InternalIterator implements Iterator {
                int current = 0;

                @Override
                public boolean hasNext() {
                    return current < size();
                }

                @Override
                public Object next() {
                    return get(current++);

                }

            }

            @Override
            public Iterator<E> iterator () {
                return new InternalIterator();
            }

            @Override
            public boolean addAll ( int index, Collection<? extends E > c){
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean addAll (Collection < ? extends E > c){
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean removeAll (Collection < ? > c) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean retainAll (Collection < ? > c) {
                throw new UnsupportedOperationException();
            }

            @Override
            public int lastIndexOf (Object o){
                throw new UnsupportedOperationException();
            }

            @Override
            public <T > T[]toArray(T[]a){
                throw new UnsupportedOperationException();
            }

            @Override
            public ListIterator<E> listIterator ( int index){
                throw new UnsupportedOperationException();
            }

            @Override
            public ListIterator<E> listIterator () {
                throw new UnsupportedOperationException();
            }

            @Override
            public List<E> subList ( int fromIndex, int toIndex){
                throw new UnsupportedOperationException();
            }

            @Override
            public void forEach (Consumer < ? super E > action){
                throw new UnsupportedOperationException();
            }

            @Override
            public Spliterator<E> spliterator () {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean removeIf (Predicate < ? super E > filter){
                throw new UnsupportedOperationException();
            }

            @Override
            public void replaceAll (UnaryOperator < E > operator) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void sort (Comparator < ? super E > c){
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean containsAll (Collection < ? > c) {
                throw new UnsupportedOperationException();
            }

        }

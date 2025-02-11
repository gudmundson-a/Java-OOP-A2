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


	public MyArrayList() {
		array = (E[]) new Object[10];
	}

	//En metod för att kolla så man är inom index.
	private void indexCtrl(int index, int value){
		if (index < 0 || index >= value){
			throw new IndexOutOfBoundsException();
		}
	}

	//
	public MyArrayList(int initialCapacity) {
		if (initialCapacity < 0) {
			throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
		} else {
			array = (E[]) new Object[initialCapacity];
		}
	}

	// -- 1 --

	//Ska returnera storleken på listan.
	@Override
	public int size() {
		return this.size;
	}

	//Ska returnera true ifall listan ej innehåller något element.
	@Override
	public boolean isEmpty() {
		 return this.size == 0;
	}

	//Ska rensa alla element i listan.
	@Override
	public void clear() {
		for (int i = 0; i < this.size(); i++) {
			this.array[i] = null;
			this.size = 0;
		}
	}

	// -- 2 --

	/*
	Ska öka kapaciteten hos ArrayList instansen vid behov.
	Den ska se till så att listan kan hålla åtminstone så många
	element som är specificerat hos minCapacity argumentet.
	 */
	public void ensureCapacity(int minCapacity) {
		if (minCapacity > array.length){
			Object[] temp = new Object[array.length * 2];

		for(int i = 0; i < this.array.length; i++){
			temp[i] = array[i];
			}
			this.array = (E[]) temp;
		}
	}

	//Ska skala ner storleken hos arrayen så att den blir samma som antalet element i listan.
	public void trimToSize() {
		if (this.size < this.array.length){
			Object[] temp = new Object[this.size];
			for(int i = 0; i < this.size; i++){
				temp[i] = array[i];
			}
			this.array = (E[]) temp;
		}
	}
    
	// -- 3 --
    
	@Override
	/*
	Addera ett specifikt element på en specifik plats i listan,
	Skifta alla nuvarande element som kommer efter i listan till höger.
	 */
	public void add(int index, E element) {
		indexCtrl(index, size+1);
		ensureCapacity(this.size + 1);

		//Loopar igenom bakifrån för att ej överskriva befintlig data som behöver sparas.
		for(int i = this.size; i > index; i--){
			array[i] = array[i-1];
		}
		array[index] = element;
		this.size++;
	}

	//Lättare för här behöver man bara lägga till nytt element i slutet.
	@Override
	public boolean add(E e) {
		this.add(size, e);
			return	true;
	}

        // -- 4 --

	//Plocka fram ett element i ett specifikt index i listan.
	@Override
	public E get(int index) {
		indexCtrl(index, size);
		return this.array[index];
	}

	/*
	Ska ersätta ett specifikt element i listan med något annat och
	returnera det gamla elementet.
	*/
	@Override
	public E set(int index, E element) {
		indexCtrl(index, size);

		E oldObj = get(index);
		array[index]= element;
		return oldObj;

	}

	// -- 5 --
	/*
	Ta bort ett element på en specifik position i listan.
	Flyttar eventuella element till vänster listan.
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
		array[size -1] = null;
		this.size--;
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
		if ((fromIndex < 0 || fromIndex >= size() || toIndex > size() || toIndex < fromIndex)){
			throw new IndexOutOfBoundsException();
		}

		//Loopar igenom och tar bort element från fromIndex -> intIndex.
		for (int i = fromIndex; i < toIndex; i++) {
			this.remove(fromIndex);
		}

	}

	// -- 6 --

	/*
	Ska returnera index av första träffen av ett specifikt element.
	Innehåller listan ej elementet ska -1 returneras.
	*/
	@Override
	public int indexOf(Object o) {
		for (int i = 0; i < this.size; i++) {
			if (array[i].equals(o)){
				return i;
			}
		} return -1;
	}

	/*
	Tar bort första händelsen av ett objekt ifall det finns med i listan.
	Innehåller listan ej elementet så förblir listan oförändrad.
	Returnerar true ifall listan innehöll det specifika elementet.
	 */
	@Override
	public boolean remove(Object o) {
		for(int i = 0; i < this.size; i++){
			if (o.equals(array[i])){
				this.remove(i);
				return true;
			}
		} return false;
	}

	//Ska returnera true ifall listan innehåller det specifika elementet.
	@Override
	public boolean contains(Object o) {
		for (int i = 0; i < this.size; i++) {
			if (o.equals(array[i])){
				return true;
			}
		} return false;
	}

	// -- 7 --

	/*
	Ska returnera en "Shallow" kopia av listan. Som jag förstått det innebär detta att
	man ska skapa en lista som refererar till orginallistan. Man kan säga att det är
	som alias i python.
	 */
	@Override
	public Object clone() {
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
	public Object[] toArray() {
		Object[] newArray = new Object[this.size];
		for (int i = 0; i < this.size; i++) {
			newArray[i] = this.array[i];
		}
		return newArray;
	}

	// --- Rör ej nedanstående kod -----------------------------------

	public MyArrayList(Collection<? extends E> c) {
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
	public Iterator<E> iterator() {
		return new InternalIterator();
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int lastIndexOf(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<E> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void forEach(Consumer<? super E> action) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Spliterator<E> spliterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeIf(Predicate<? super E> filter) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void replaceAll(UnaryOperator<E> operator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void sort(Comparator<? super E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

}

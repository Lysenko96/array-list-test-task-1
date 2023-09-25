package core.basesyntax;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

public class ArrayList<T> implements List<T> {

    private static final int DEFAULT_CAPACITY = 10;
    private int size;
    private Object[] array;


    public ArrayList() {
        array = new Object[0];
    }

    @Override
    public void add(T value) {
        this.add(value, size);
    }

    @Override
    public void add(T value, int index) {
        if (isEmpty()) array = new Object[DEFAULT_CAPACITY];
        checkIndexAdd(index, size);
        growIfArrayFull(size);
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = value;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (isEmpty()) array = new Object[DEFAULT_CAPACITY];
        Object[] newArray = new Object[list.size()];
        for (int i = 0; i < list.size(); i++) newArray[i] = list.get(i);
        growIfArrayFull(list.size());
        System.arraycopy(newArray, 0, array, size, list.size());
        size += list.size();
    }

    @Override
    public T get(int index) {
        checkIndex(index, size);
        return (T) array[index];

    }

    @Override
    public void set(T value, int index) {
        checkIndex(index, size);
        array[index] = value;

    }

    @Override
    public T remove(int index) {
        checkIndex(index, size);
        T element = (T) array[index];
        System.arraycopy(array, index + 1, array, index, size - index - 1);
        size--;
        return element;
    }

    @Override
    public T remove(T element) {
        int index = 0;
        for (int i = 0; i < size; i++) {
            if (contains(element)) {
                index++;
                break;
            }
        }
        if (index == 0) throw new NoSuchElementException();
        this.remove(index);
        return element;
    }

    public boolean contains(T element) {
        for (int i = 0; i < size; i++) {
            if (element == null && array[i] == null) return true;
            if (element != null && element.equals(array[i])) return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void growIfArrayFull(int size) {
        if (size == array.length) {
            Object[] newArray = new Object[array.length * 2];
            System.arraycopy(array, 0, newArray, 0, size);
            array = newArray;
        }
    }

    private void checkIndex(int index, int size) {
        if (!(index >= 0 && index < size)) {
            throw new ArrayListIndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void checkIndexAdd(int index, int size) {
        if (!(index >= 0 && index <= size)) {
            throw new ArrayListIndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        for (Object elem : array) if (elem != null) joiner.add(elem.toString());
        return joiner.toString();
    }
}

package dev.mqzn.lib.utils;

import java.util.Arrays;
import java.util.Iterator;

public class FilteredArray<T> implements Iterable<T> {

    private final Object[] filteredArray;

    @SafeVarargs
    public FilteredArray(T... array) {

        int countNulls = 0;
        for (T t : array) {
            if (t == null) {
                countNulls++;
            }
        }

        if(countNulls == 0) {
            filteredArray = array;
            return;
        }

        filteredArray = new Object[array.length - countNulls];
        for (int i = 0, j = 0; i < array.length; i++) {
            if (array[i] != null) {
                filteredArray[j] = array[i];
                j++;
            }
        }

    }



    public int indexOf(T element) {
        int index = -1;
        for (int i = 0; i < this.size(); i++) {
            if(this.get(i).equals(element)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public boolean contains(T element) {
        return this.indexOf(element) != -1;
    }



    @SuppressWarnings("unchecked")
    public T[] toTrimmedArray() {
        return (T[]) filteredArray;
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        return (T) filteredArray[index];
    }

    public void set(int index, T t) {

        if(index >= this.size() || index < 0) {
            throw new IndexOutOfBoundsException("Index is out of allowed range : " + index);
        }

        filteredArray[index] = t;

    }

    public int size() {
        return filteredArray.length;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private int current = -1;

            @Override
            public boolean hasNext() {
                try {
                    FilteredArray.this.get(current+1);
                    return true;
                }catch (Exception ex) {
                    return false;
                }
            }

            @Override
            public T next() {
                current++;
                return FilteredArray.this.get(current);
            }
        };
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FilteredArray)) return false;
        FilteredArray<?> that = (FilteredArray<?>) o;
        return Arrays.equals(filteredArray, that.filteredArray);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(filteredArray);
    }


}


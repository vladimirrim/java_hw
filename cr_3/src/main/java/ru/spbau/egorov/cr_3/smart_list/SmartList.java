package ru.spbau.egorov.cr_3.smart_list;


import org.jetbrains.annotations.NotNull;

import java.util.*;


/**
 * This class implements list which contains elements differently depends on the count of current elements.
 * @param <E> is type of contained elements.
 */
public class SmartList<E> extends AbstractList<E> implements List<E> {


    private static final int FIRST_STAGE_UPPER_BOUND = 1;
    private static final int SECOND_STAGE_UPPER_BOUND = 5;
    private int size;
    private Object container = null;

    /**
     * Creates empty collection.
     */
    public SmartList() {

    }

    /**
     * Creates collection with container depending on size of collection in param.
     * @param c is collection to copy.
     */
    public SmartList(@NotNull Collection<? extends E> c) {
        size = c.size();

        if (c.size() > SECOND_STAGE_UPPER_BOUND) {
            container = new ArrayList<E>(c);
            return;
        }

        if (c.size() > FIRST_STAGE_UPPER_BOUND) {
            container = new Object[5];
            int i = 0;
            for (E value : c) {
                ((Object[]) container)[i] = value;
                i++;
            }
            return;
        }

        if (c.size() == FIRST_STAGE_UPPER_BOUND) {
            container = c.toArray()[0];
        }
    }



    @Override
    public E get(int index) {

        if (size > SECOND_STAGE_UPPER_BOUND) {
            return ((ArrayList<E>) container).get(index);
        }

        if (size > FIRST_STAGE_UPPER_BOUND) {
            return (E) ((Object[]) container)[index];
        }

        if (size == FIRST_STAGE_UPPER_BOUND) {
            if (index == 0)
                return (E) container;
            else
                throw new IndexOutOfBoundsException();
        }

        throw new IndexOutOfBoundsException();
    }

    @Override
    public int size() {
        return size;
    }


    @Override
    public E remove(int index) {
        if (size == 0)
            throw new IndexOutOfBoundsException();

        size--;
        if (size > SECOND_STAGE_UPPER_BOUND) {
            return ((ArrayList<E>) container).remove(index);
        }

        if (size == SECOND_STAGE_UPPER_BOUND) {
            Object[] tmp = new Object[5];

            E oldValue = ((ArrayList<E>) container).remove(index);
            int i = 0;
            for (E value : ((ArrayList<E>) container)) {
                tmp[i] = value;
                i++;
            }

            container = tmp;
            return oldValue;
        }

        if (size > FIRST_STAGE_UPPER_BOUND) {
            E oldValue = ((E[]) container)[index];
            for (int i = index; i < SECOND_STAGE_UPPER_BOUND - 1; i++)
                ((E[]) container)[i] = ((E[]) container)[i + 1];
            return oldValue;
        }

        if (size == FIRST_STAGE_UPPER_BOUND) {
            E oldValue = ((E[]) container)[index];
            for (int i = index; i < SECOND_STAGE_UPPER_BOUND - 1; i++)
                ((E[]) container)[i] = ((E[]) container)[i + 1];
            container = ((E[]) container)[0];
            return oldValue;
        }

        E oldValue = (E) container;
        container = null;
        return oldValue;
    }

    @Override
    public boolean add(E value) {
        if (size > SECOND_STAGE_UPPER_BOUND) {
            size++;
            ((ArrayList<E>) container).add(value);
            return true;
        }

        if (size == SECOND_STAGE_UPPER_BOUND) {
            size++;
            container = new ArrayList<>(Arrays.asList((Object[]) container));
            ((ArrayList<E>) container).add(value);
            return true;
        }

        if (size > FIRST_STAGE_UPPER_BOUND) {
            ((Object[]) container)[size] = value;
            size++;
            return true;
        }

        if (size == FIRST_STAGE_UPPER_BOUND) {
            size++;
            Object[] tmp = new Object[5];
            tmp[0] = container;
            tmp[1] = value;
            container = tmp;
            return true;
        }
        size++;

        container = value;
        return true;
    }

    @Override
    public E set(int index, E value){

        if (size > SECOND_STAGE_UPPER_BOUND) {
            return ((ArrayList<E>) container).set(index, value);
        }

        if (size > FIRST_STAGE_UPPER_BOUND) {
            E oldValue = ((E[]) container)[index];
            ((E[]) container)[index] = value;
            return oldValue;
        }

        if(index != 0)
            throw new IndexOutOfBoundsException();

        E oldValue = (E) container;
        container = value;
        return oldValue;
    }

}

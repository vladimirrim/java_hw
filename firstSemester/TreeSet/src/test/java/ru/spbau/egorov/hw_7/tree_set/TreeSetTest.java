package ru.spbau.egorov.hw_7.tree_set;


import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NavigableSet;

import static org.junit.jupiter.api.Assertions.*;

class TreeSetTest {


    @Test
    void createTreeSet() {
        TreeSet<Integer> tree = new TreeSet<>();
        assertNotNull(tree);
    }

    @Test
    void descendingIteratorOneElement() {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(1);
        java.util.TreeSet<Integer> defTree = new java.util.TreeSet<>();
        defTree.add(1);
        Iterator<Integer> defIt = defTree.descendingIterator();
        Iterator<Integer> it = tree.descendingIterator();
        assertEquals(defIt.next(), it.next());
        assertEquals(defIt.hasNext(), it.hasNext());
    }

    @Test
    void descendingIteratorThousandElements() {
        TreeSet<Integer> tree = new TreeSet<>();
        for (int i = 1; i <= 1000; i++)
            tree.add(i);
        java.util.TreeSet<Integer> defTree = new java.util.TreeSet<>();
        for (int i = 1; i <= 1000; i++)
            defTree.add(i);

        Iterator<Integer> defIt = defTree.descendingIterator();
        Iterator<Integer> it = tree.descendingIterator();
        for (int i = 1; i <= 1000; i++) {
            assertEquals(defIt.next(), it.next());
            assertEquals(defIt.hasNext(), it.hasNext());
        }
    }

    @Test
    void descendingIteratorThousandElementsReverseComparator() {
        TreeSet<Integer> tree = new TreeSet<>((o1, o2) -> o2 - o1);
        for (int i = 1; i <= 1000; i++)
            tree.add(i);
        java.util.TreeSet<Integer> defTree = new java.util.TreeSet<>((o1, o2) -> o2 - o1);
        for (int i = 1; i <= 1000; i++)
            defTree.add(i);

        Iterator<Integer> defIt = defTree.descendingIterator();
        Iterator<Integer> it = tree.descendingIterator();
        for (int i = 1; i <= 1000; i++) {
            assertEquals(defIt.next(), it.next());
            assertEquals(defIt.hasNext(), it.hasNext());
        }
    }


    @Test
    void descendingSetThousandElementsTraverseDescendingIterator() {
        TreeSet<Integer> tree = new TreeSet<>();
        for (int i = 1; i <= 1000; i++)
            tree.add(i);

        MyTreeSet<Integer> revTree = tree.descendingSet();

        java.util.TreeSet<Integer> defTree = new java.util.TreeSet<>();
        for (int i = 1; i <= 1000; i++)
            defTree.add(i);
        NavigableSet<Integer> revDefTree = defTree.descendingSet();

        Iterator<Integer> defIt = revDefTree.descendingIterator();
        Iterator<Integer> it = revTree.descendingIterator();

        for (int i = 1; i <= 1000; i++) {
            assertEquals(defIt.next(), it.next());
            assertEquals(defIt.hasNext(), it.hasNext());
        }
    }

    @Test
    void descendingSetThousandElementsTraverseIterator() {
        TreeSet<Integer> tree = new TreeSet<>();
        for (int i = 1; i <= 1000; i++)
            tree.add(i);

        MyTreeSet<Integer> revTree = tree.descendingSet();

        java.util.TreeSet<Integer> defTree = new java.util.TreeSet<>();
        for (int i = 1; i <= 1000; i++)
            defTree.add(i);
        NavigableSet<Integer> revDefTree = defTree.descendingSet();

        Iterator<Integer> defIt = revDefTree.iterator();
        Iterator<Integer> it = revTree.iterator();

        for (int i = 1; i <= 1000; i++) {
            assertEquals(defIt.next(), it.next());
            assertEquals(defIt.hasNext(), it.hasNext());
        }
    }


    @Test
    void descendingSetThousandElementsLower() {
        TreeSet<Integer> tree = new TreeSet<>();
        for (int i = 1; i <= 1000; i++)
            tree.add(i);

        MyTreeSet<Integer> revTree = tree.descendingSet();

        java.util.TreeSet<Integer> defTree = new java.util.TreeSet<>();
        for (int i = 1; i <= 1000; i++)
            defTree.add(i);
        NavigableSet<Integer> revDefTree = defTree.descendingSet();
        for (int i = 1; i <= 1000; i++) {
            assertEquals(revDefTree.lower(i), revTree.lower(i));
        }

    }

    @Test
    void descendingSetThousandElementsHigher() {

        TreeSet<Integer> tree = new TreeSet<>();
        for (int i = 1; i <= 1000; i++)
            tree.add(i);

        MyTreeSet<Integer> revTree = tree.descendingSet();

        java.util.TreeSet<Integer> defTree = new java.util.TreeSet<>();
        for (int i = 1; i <= 1000; i++)
            defTree.add(i);
        NavigableSet<Integer> revDefTree = defTree.descendingSet();
        for (int i = 1; i <= 1000; i++) {
            assertEquals(revDefTree.higher(i), revTree.higher(i));
        }
    }

    @Test
    void descendingSetThousandElementsCeiling() {
        TreeSet<Integer> tree = new TreeSet<>();
        for (int i = 1; i <= 1000; i++)
            tree.add(i);

        MyTreeSet<Integer> revTree = tree.descendingSet();

        java.util.TreeSet<Integer> defTree = new java.util.TreeSet<>();
        for (int i = 1; i <= 1000; i++)
            defTree.add(i);
        NavigableSet<Integer> revDefTree = defTree.descendingSet();
        for (int i = 1; i <= 1000; i++) {
            assertEquals(revDefTree.ceiling(i), revTree.ceiling(i));
        }
    }

    @Test
    void descendingSetThousandElementsFloor() {
        TreeSet<Integer> tree = new TreeSet<>();
        for (int i = 1; i <= 1000; i++)
            tree.add(i);

        MyTreeSet<Integer> revTree = tree.descendingSet();

        java.util.TreeSet<Integer> defTree = new java.util.TreeSet<>();
        for (int i = 1; i <= 1000; i++)
            defTree.add(i);
        NavigableSet<Integer> revDefTree = defTree.descendingSet();
        for (int i = 1; i <= 1000; i++) {
            assertEquals(revDefTree.floor(i), revTree.floor(i));
        }
    }


    @Test
    void firstOneElement() {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(1);
        assertEquals((Integer) 1, tree.first());
    }

    @Test
    void firstThousandElements() {
        TreeSet<Integer> tree = new TreeSet<>();
        for (int i = 1; i <= 1000; i++)
            tree.add(i);
        assertEquals((Integer) 1, tree.first());
    }

    @Test
    void firstThousandElementsReverseComparator() {
        TreeSet<Integer> tree = new TreeSet<>((o1, o2) -> o2 - o1);
        for (int i = 1; i <= 1000; i++)
            tree.add(i);
        assertEquals((Integer) 1000, tree.first());
    }


    @Test
    void lasttOneElement() {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(1);
        assertEquals((Integer) 1, tree.last());
    }


    @Test
    void lastThousandElements() {
        TreeSet<Integer> tree = new TreeSet<>();
        for (int i = 1; i <= 1000; i++)
            tree.add(i);
        assertEquals((Integer) 1000, tree.last());
    }


    @Test
    void lastThousandElementsReverseComparator() {
        TreeSet<Integer> tree = new TreeSet<>((o1, o2) -> o2 - o1);
        for (int i = 1; i <= 1000; i++)
            tree.add(i);
        assertEquals((Integer) 1, tree.last());
    }


    @Test
    void lowerOneElement() {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(1);
        assertEquals((Integer) 1, tree.lower(2));
    }

    @Test
    void lowerNoSuchElement() {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(1);
        assertNull(tree.lower(1));
    }


    @Test
    void lowerThousandElements() {
        TreeSet<Integer> tree = new TreeSet<>();
        for (int i = 1; i <= 1000; i++)
            tree.add(i);
        assertEquals((Integer) 499, tree.lower(500));
    }


    @Test
    void lowerThousandElementsReverseComparator() {
        TreeSet<Integer> tree = new TreeSet<>((o1, o2) -> o2 - o1);
        for (int i = 1; i <= 1000; i++)
            tree.add(i);
        assertEquals((Integer) 501, tree.lower(500));
    }

    @Test
    void floorOneElement() {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(1);
        assertEquals((Integer) 1, tree.floor(1));
    }

    @Test
    void floorNoSuchElement() {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(1);
        assertNull(tree.floor(0));
    }


    @Test
    void floorThousandElements() {
        TreeSet<Integer> tree = new TreeSet<>();
        for (int i = 1; i <= 1000; i++)
            tree.add(i);
        assertEquals((Integer) 500, tree.floor(500));
    }


    @Test
    void floorThousandElementsReverseComparator() {
        TreeSet<Integer> tree = new TreeSet<>((o1, o2) -> o2 - o1);
        for (int i = 1; i <= 1000; i++)
            if (i != 500)
                tree.add(i);
        assertEquals((Integer) 501, tree.floor(500));
    }


    @Test
    void ceilingrOneElement() {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(1);
        assertEquals((Integer) 1, tree.ceiling(1));
    }

    @Test
    void ceilingNoSuchElement() {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(1);
        assertNull(tree.ceiling(2));
    }


    @Test
    void ceilingThousandElements() {
        TreeSet<Integer> tree = new TreeSet<>();
        for (int i = 1; i <= 1000; i++)
            tree.add(i);
        assertEquals((Integer) 500, tree.ceiling(500));
    }


    @Test
    void ceilingThousandElementsReverseComparator() {
        TreeSet<Integer> tree = new TreeSet<>((o1, o2) -> o2 - o1);
        for (int i = 1; i <= 1000; i++)
            if (i != 500)
                tree.add(i);
        assertEquals((Integer) 499, tree.ceiling(500));
    }

    @Test
    void higherOneElement() {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(1);
        assertEquals((Integer) 1, tree.higher(0));
    }

    @Test
    void higherNoSuchElement() {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(1);
        assertNull(tree.higher(1));
    }


    @Test
    void higherThousandElements() {
        TreeSet<Integer> tree = new TreeSet<>();
        for (int i = 1; i <= 1000; i++)
            tree.add(i);
        assertEquals((Integer) 501, tree.higher(500));
    }


    @Test
    void higherThousandElementsReverseComparator() {
        TreeSet<Integer> tree = new TreeSet<>((o1, o2) -> o2 - o1);
        for (int i = 1; i <= 1000; i++)
            tree.add(i);
        assertEquals((Integer) 499, tree.higher(500));
    }


    @Test
    void iteratorOneElement() {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(1);
        java.util.TreeSet<Integer> defTree = new java.util.TreeSet<>();
        defTree.add(1);
        Iterator<Integer> defIt = defTree.iterator();
        Iterator<Integer> it = tree.iterator();
        assertEquals(defIt.next(), it.next());
        assertEquals(defIt.hasNext(), it.hasNext());
    }

    @Test
    void iteratorThousandElements() {
        TreeSet<Integer> tree = new TreeSet<>();
        for (int i = 1; i <= 1000; i++)
            tree.add(i);
        java.util.TreeSet<Integer> defTree = new java.util.TreeSet<>();
        for (int i = 1; i <= 1000; i++)
            defTree.add(i);

        Iterator<Integer> defIt = defTree.iterator();
        Iterator<Integer> it = tree.iterator();
        for (int i = 1; i <= 1000; i++) {
            assertEquals(defIt.next(), it.next());
            assertEquals(defIt.hasNext(), it.hasNext());
        }

    }

    @Test
    void iteratorThousandElementsReverseComparator() {
        TreeSet<Integer> tree = new TreeSet<>((o1, o2) -> o2 - o1);
        for (int i = 1; i <= 1000; i++)
            tree.add(i);
        java.util.TreeSet<Integer> defTree = new java.util.TreeSet<>((o1, o2) -> o2 - o1);
        for (int i = 1; i <= 1000; i++)
            defTree.add(i);

        Iterator<Integer> defIt = defTree.iterator();
        Iterator<Integer> it = tree.iterator();
        for (int i = 1; i <= 1000; i++) {
            assertEquals(defIt.next(), it.next());
            assertEquals(defIt.hasNext(), it.hasNext());
        }
    }

    @Test
    void sizeOneElement() {
        TreeSet<Integer> tree = new TreeSet<>();
        tree.add(1);
        assertEquals(1, tree.size());
    }

    @Test
    void sizeThousandElements() {
        TreeSet<Integer> tree = new TreeSet<>();
        for (int i = 1; i <= 1000; i++)
            tree.add(i);
        assertEquals(1000, tree.size());
    }

}
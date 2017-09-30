package ru.spbau.egorov.hw_3.trie;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * This class implements trie with alphabet size 65536.
 */
public class Trie implements Serializable {
    private final static int ALPHABET_SIZE = 65536;
    private ArrayList<Vertex> symbols;
    private int size;

    private class Vertex {
        int next[];
        boolean leaf;
        int prefixCount;

        Vertex() {
            next = new int[ALPHABET_SIZE];
            Arrays.fill(next, -1);
        }
    }

    public Trie() {
        symbols = new ArrayList<>();
        symbols.add(new Vertex());
    }

    /**
     * Add element to the trie.O(|element|) time.
     * @return false if element was already in trie.
     */
    public boolean add(String element) {

        if (contains(element))
            return false;

        size++;
        int v = 0;
        for (char c : element.toCharArray()) {
            if (symbols.get(v).next[c] == -1) {
                symbols.get(v).next[c] = symbols.size();
                symbols.add(new Vertex());
            }
            symbols.get(v).prefixCount++;
            v = symbols.get(v).next[c];
        }

        symbols.get(v).prefixCount++;
        return symbols.get(v).leaf = true;
    }


    /**
     * O(|element|) time.
     */
    public boolean contains(String element) {
        int v = 0;
        for (char c : element.toCharArray()) {
            if (symbols.get(v).next[c] == -1) {
                return false;
            }
            v = symbols.get(v).next[c];
        }
        return symbols.get(v).leaf;
    }

    /**
     * Remove element from the trie.O(|element|) time.
     * @return false if element was not in the trie.
     */
    public boolean remove(String element) {

        if (!contains(element))
            return false;

        size--;
        int v = 0;
        for (char c : element.toCharArray()) {
            symbols.get(v).prefixCount--;
            v = symbols.get(v).next[c];
        }
        symbols.get(v).prefixCount--;
        return !(symbols.get(v).leaf = false);
    }

    /**
     * O(1) time.
     * @return strings count.
     */
    public int size() {
        return size;
    }

    /**
     * O(|prefix|) time.
     */
    public int howManyStartsWithPrefix(String prefix) {

        int v = 0;
        for (char c : prefix.toCharArray()) {
            if (symbols.get(v).next[c] == -1) {
                return 0;
            }
            v = symbols.get(v).next[c];
        }
        return symbols.get(v).prefixCount;

    }

    /**
     * Write size, vertices count and all data in vertices.
     */
    public void serialize(OutputStream out) throws IOException {
        DataOutputStream dout = new DataOutputStream(out);
        dout.writeInt(size);
        dout.writeInt(symbols.size());
        for (int i = 0; i < symbols.size(); i++) {
            for (int ind : symbols.get(i).next)
                dout.writeInt(ind);

            dout.writeInt(symbols.get(i).prefixCount);
            dout.writeBoolean(symbols.get(i).leaf);
        }
        dout.close();
    }

    /**
     * Overwrite all data in trie with data from input stream.
     */
    public void deserialize(InputStream in) throws IOException {
        DataInputStream din = new DataInputStream(in);
        size = din.readInt();
        int symSize;
        symSize = din.readInt();
        symbols = new ArrayList<>(symSize);
        for (int i = 0; i < symSize; i++) {
            symbols.add(new Vertex());
        }
        for (int i = 0; i < symSize; i++) {
            for (int ind = 0; ind < ALPHABET_SIZE; ind++)
                symbols.get(i).next[ind] = din.readInt();

            symbols.get(i).prefixCount = din.readInt();
            symbols.get(i).leaf = din.readBoolean();
        }
        din.close();
    }

}

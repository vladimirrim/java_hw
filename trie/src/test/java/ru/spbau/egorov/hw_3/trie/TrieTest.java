package ru.spbau.egorov.hw_3.trie;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TrieTest {

    @Test
    void createTrie() {
        Trie trie = new Trie();
        assertNotNull(trie);
    }

    @Test
    void addTwoElements() {
        Trie trie = new Trie();
        trie.add("JO");
        trie.add("jo");
        assertEquals(true, trie.contains("JO"));
        assertEquals(true, trie.contains("jo"));
    }

    @Test
    void addLongString() {
        Trie trie = new Trie();
        trie.add("ajsdgfkashgdkfhgsakdhfgkasdhgfkjahdsg");
        assertEquals(true, trie.contains("ajsdgfkashgdkfhgsakdhfgkasdhgfkjahdsg"));
    }

    @Test
    void addSameString() {
        Trie trie = new Trie();
        trie.add("jo");
        assertEquals(false, trie.add("jo"));
    }

    @Test
    void addThousandDifferentElements() {
        Trie trie = new Trie();
        for (int i = 0; i < 1000; i++)
            assertEquals(true, trie.add("" + i));
    }

    @Test
    void containsOneElement() {
        Trie trie = new Trie();
        trie.add("jo");
        assertEquals(true, trie.contains("jo"));
    }

    @Test
    void containsThousandElements() {
        Trie trie = new Trie();
        for (int i = 0; i < 1000; i++)
            trie.add("" + i);
        for (int i = 0; i < 1000; i++)
            assertEquals(true, trie.contains("" + i));
    }

    @Test
    void containsNoElements() {
        Trie trie = new Trie();
        assertEquals(false, trie.contains("jo"));
    }

    @Test
    void removeOneElement() {
        Trie trie = new Trie();
        trie.add("jo");
        assertEquals(true, trie.remove("jo"));
    }

    @Test
    void removeNoElements() {
        Trie trie = new Trie();
        assertEquals(false, trie.remove("jo"));
    }

    @Test
    void removeLongString() {
        Trie trie = new Trie();
        trie.add("askdfasldbflasldfblasjfblasdflasdfs");
        assertEquals(true, trie.remove("askdfasldbflasldfblasjfblasdflasdfs"));
    }

    @Test
    void sizeZero() {
        Trie trie = new Trie();
        trie.add("jo");
        trie.remove("jo");
        trie.remove("jo");
        assertEquals(0, trie.size());
    }

    @Test
    void sizeThousand() {
        Trie trie = new Trie();
        for (int i = 0; i < 1000; i++)
            trie.add("" + i);
        assertEquals(1000, trie.size());
    }

    @Test
    void howManyStartsWithPrefixZero() {
        Trie trie = new Trie();
        trie.add("jO");
        assertEquals(0, trie.howManyStartsWithPrefix("jo"));
    }

    @Test
    void howManyStartsWithPrefixThousand() {
        Trie trie = new Trie();
        trie.add("jo");
        for (int i = 0; i < 999; i++)
            trie.add("jo" + i);
        assertEquals(1000, trie.howManyStartsWithPrefix("jo"));
    }

    @Test
    void howManyStartsWithPrefixLongPrefix() {
        Trie trie = new Trie();
        trie.add("ajsdf,asdfasgdf,jas,dgcffasdbfbbvad,fasdbvfcamdsf");
        assertEquals(1, trie.howManyStartsWithPrefix("ajsdf,asdfasgdf,jas,dgcffasdbfbbvad,fasdbvfcamdsf"));
    }

    @Test
    void serializeAndDeserializeSizeOne() throws IOException {
        Trie trie = new Trie();
        trie.add("jo");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        trie.serialize(out);

        Trie trieCopy = new Trie();

        trieCopy.deserialize(new ByteArrayInputStream(out.toByteArray()));
        assertEquals(trie.size(), trieCopy.size());
        assertEquals(true, trieCopy.contains("jo"));
        out.close();
    }

    @Test
    void serializeAndDeserializeSizeThousand() throws IOException {
        Trie trie = new Trie();
        trie.add("jo");
        for (int i = 0; i < 999; i++)
            trie.add("jo" + i);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        trie.serialize(out);
        Trie trieCopy = new Trie();
        trieCopy.deserialize(new ByteArrayInputStream(out.toByteArray()));
        assertEquals(trie.size(), trieCopy.size());
        assertEquals(true, trieCopy.contains("jo"));
        for (int i = 0; i < 999; i++)
            assertEquals(true, trieCopy.contains("jo" + i));
        out.close();
    }

}
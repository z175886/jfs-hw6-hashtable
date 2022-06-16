import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class LinearProbingHashTableTest {
    LinearProbingHashTable table1 = new LinearProbingHashTable<>(8);

    @Test
    void containsKey() {
        table1.put(1,3);
        table1.put(2,4);
        assertTrue(table1.containsKey(1));
        assertTrue(table1.containsKey(2));
        assertFalse(table1.containsKey(3));
        table1.remove(2);
        assertFalse(table1.containsKey(2));
    }

    @Test
    void clear() {
        table1.clear();
        assertTrue(table1.isEmpty());
    }


    @Test
    void containsValue() {
        table1.put(1,3);
        table1.put(2,4);

        assertTrue(table1.containsValue(3));
        assertTrue(table1.containsValue(4));
        assertFalse(table1.containsValue(5));
    }

    @Test
    void entrySet() {
        table1.put(1,3);
        table1.put(2,4);
        table1.put(3,5);
        Set<Map.Entry<Integer, Integer>> set =table1.entrySet();
        int j =0;
        for (int i = 0; i< set.size() ; i++){
            if(table1.getArray()[i] != null){
            assertEquals(set.toArray()[j].hashCode(),table1.getArray()[i].hashCode());
            j++;
            }
        }
    }

    @Test
    void get() {
        table1.put(1,3);
        table1.put(2,4);
        assertEquals(table1.get(1),3);
        assertEquals(table1.get(2),4);
        assertNotEquals(table1.get(3),3);
        assertNull(table1.get(3));
    }

    @Test
    void isEmpty() {
        LinearProbingHashTable table1 = new LinearProbingHashTable<>(0);
        assertTrue(table1.isEmpty());
    }

    @Test
    void keySet() {
        table1.put(1,3);
        table1.put(2,4);
        Set<Map.Entry<Integer, Integer>> set = table1.keySet();
        for(Object key : set){
            assertTrue(table1.containsKey(key));
        }
    }

    @Test
    void put() {
        table1.put(1,3);
        table1.put(2,4);
        assertEquals(3,table1.get(1));
        assertEquals(4,table1.get(2));

    }

    @Test
    void putAll() {
        Map<Integer,Integer> otherMap = new HashMap<>();
        otherMap.put(1,3);
        otherMap.put(2,4);
        table1.putAll(otherMap);
        assertEquals(3,table1.get(1));
        assertEquals(4,table1.get(2));
    }

    @Test
    void remove() {
        table1.put(1,3);
        table1.put(2,4);
        assertTrue(table1.containsKey(1));
        assertTrue(table1.containsKey(2));
        table1.remove(1);
        table1.remove(2);
        assertFalse(table1.containsKey(1));
        assertFalse(table1.containsKey(2));
    }

    @Test
    void size() {
        assertEquals(table1.size(),table1.getArray().length);
        table1.clear();
        assertEquals(table1.size(),table1.getArray().length);
    }

    @Test
    void values() {
        table1.put(1,3);
        table1.put(2,4);
        Collection<Integer> collect = new ArrayList<>();
        collect = table1.values();
        for(Object value : collect){
            assertTrue(table1.containsValue(value));
        }
    }

    @Test
    void getArray() {

    assertEquals(table1.getArray().length,table1.size());
    Set<Map.Entry<Integer, Integer>> set =table1.entrySet();
    for(int i = 0; i< table1.size(); i++){
        if(table1.getArray()[i]!=null)
       assertEquals(table1.getArray()[i],table1.get(set.toArray()[i]));

    }


    }

    @Test
    void setArray() {
        HashTableEntry<Integer,Integer>[] myArray = new HashTableEntry[3];
        myArray[0] = new HashTableEntry<>(1,2);
        myArray[1] = new HashTableEntry<>(2,3);
        myArray[2] = new HashTableEntry<>(3,4);
        table1.setArray(myArray);
        assertEquals(table1.getArray(),myArray);
    }

    @Test
    void setSize() {
        table1.setSize(2);
        assertEquals(table1.getArray().length,2);
    }
    @Test
    void expand(){
        //		size = 8;
        //		loadfactor = .75;
        // auto double the size of array when numofentry > size * loadfactor, so it will be increased when numofentry > 6
        //
        table1.put(1,2);
        table1.put(2,2);
        table1.put(3,2);
        table1.put(4,2);
        table1.put(5,2);
        table1.put(6,2);
        assertEquals(table1.size(),8);
        table1.put(7,2);
        assertEquals(table1.size(),16);
        table1.remove(1);
        assertEquals(table1.size(),16);
    }
}
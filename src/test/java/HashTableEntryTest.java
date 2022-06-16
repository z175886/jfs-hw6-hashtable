import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.testng.Assert.assertTrue;

class HashTableEntryTest {
    HashTableEntry entry1 = new HashTableEntry<>(1,2);
    HashTableEntry entry2 = new HashTableEntry<>(2,2);
    @Test
    void testHashCode() {
        assertEquals(entry1.hashCode(),entry1.getKey().hashCode()^entry1.getValue().hashCode());
    }

    @Test
    void testEquals() {
        assertTrue(entry2.equals(entry2));
        assertFalse(entry1.equals(entry2));
        assertFalse(entry1.equals(1));
    }
}
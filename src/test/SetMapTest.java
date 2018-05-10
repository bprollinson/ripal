import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.util.SetMap;

import java.util.HashSet;

public class SetMapTest
{
    @Test
    public void testGetReturnsEmptySetForNonExistentKey()
    {
        SetMap<String, Integer> setMap = new SetMap<String, Integer>();

        HashSet<Integer> expectedSet = new HashSet<Integer>();
        assertEquals(expectedSet, setMap.get("key"));
    }

    @Test
    public void testSetMapMapsSingleKeyToSingleEntry()
    {
        SetMap<String, Integer> setMap = new SetMap<String, Integer>();
        setMap.put("key", 1);

        HashSet<Integer> expectedSet = new HashSet<Integer>();
        expectedSet.add(1);

        assertEquals(expectedSet, setMap.get("key"));
    }

    @Test
    public void testSetMapMapsSingleKeyToMultipleEntries()
    {
        SetMap<String, Integer> setMap = new SetMap<String, Integer>();
        setMap.put("key", 1);
        setMap.put("key", 2);

        HashSet<Integer> expectedSet = new HashSet<Integer>();
        expectedSet.add(1);
        expectedSet.add(2);

        assertEquals(expectedSet, setMap.get("key"));
    }

    @Test
    public void testSetMapMapsMultileKeysToSingleEntryEach()
    {
        SetMap<String, Integer> setMap = new SetMap<String, Integer>();
        setMap.put("key1", 1);
        setMap.put("key2", 2);

        HashSet<Integer> expectedSet1 = new HashSet<Integer>();
        expectedSet1.add(1);
        assertEquals(expectedSet1, setMap.get("key1"));

        HashSet<Integer> expectedSet2 = new HashSet<Integer>();
        expectedSet2.add(2);
        assertEquals(expectedSet2, setMap.get("key2"));
    }

    @Test
    public void testSetMapMapsMultipleKeysToMultipleEntriesEach()
    {
        SetMap<String, Integer> setMap = new SetMap<String, Integer>();
        setMap.put("key1", 1);
        setMap.put("key1", 2);
        setMap.put("key2", 3);
        setMap.put("key2", 4);

        HashSet<Integer> expectedSet1 = new HashSet<Integer>();
        expectedSet1.add(1);
        expectedSet1.add(2);
        assertEquals(expectedSet1, setMap.get("key1"));

        HashSet<Integer> expectedSet2 = new HashSet<Integer>();
        expectedSet2.add(3);
        expectedSet2.add(4);
        assertEquals(expectedSet2, setMap.get("key2"));
    }

    @Test
    public void testSetMapMapsNullKey()
    {
        SetMap<String, Integer> setMap = new SetMap<String, Integer>();
        setMap.put(null, 1);

        HashSet<Integer> expectedSet = new HashSet<Integer>();
        expectedSet.add(1);
        assertEquals(expectedSet, setMap.get(null));
    }

    @Test
    public void testEntrySetReturnsEmptySetForEmptySetMap()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testEntrySetReturnsEntrySetForNonEmptySetMap()
    {
        assertEquals(0, 1);
    }
}
